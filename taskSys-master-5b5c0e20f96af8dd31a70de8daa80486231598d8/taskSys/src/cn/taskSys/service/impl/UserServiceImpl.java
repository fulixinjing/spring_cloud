package cn.taskSys.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.taskSys.base.Constants;
import cn.taskSys.base.PageView;
import cn.taskSys.base.QueryResult;
import cn.taskSys.dao.AdminDao;
import cn.taskSys.dao.RoleDao;
import cn.taskSys.dao.UserDao;
import cn.taskSys.entity.Menu;
import cn.taskSys.entity.Role;
import cn.taskSys.entity.TaskInfo;
import cn.taskSys.entity.User;
import cn.taskSys.log.LogAnnotation;
import cn.taskSys.pager.PagerModel;
import cn.taskSys.service.IUserService;
import cn.taskSys.utils.EncodeUtil;
import cn.taskSys.utils.SendEmailUtil;

@Service("userService")
public class UserServiceImpl implements IUserService {
	HttpSession session;
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private AdminDao adminDao;
	
	
	/**
	 * 登录时查询用户角色
	 * @param String
	 * @return
	 * @throws Exception
	 */
	@LogAnnotation(eventCode="20320020",eventProcess="")
	public List<Role> getUserRole(String login) throws Exception {
		
		return userDao.getUserRole(login);
	}
	
	/**
	 * 获取登录用户信息
	 * @return
	 */
	@LogAnnotation(eventCode="20320017",eventProcess="")
	public User getSessionUser(Map map) throws Exception {
		return userDao.getSessionUser(map);
	}
	
	/**
	 * 获取ID集合
	 * @return
	 */ 
	@LogAnnotation(eventCode="20320018",eventProcess="")
	public List<User> getTDUser(Map map) throws Exception {
		return userDao.getTDUser(map);
	}
	
	@LogAnnotation(eventCode="2032003",eventProcess="")
	public User getIsLogin(User user) throws Exception {
			return userDao.getIsLogin(user);	
	}
	
	/**
	 * 登录时查询用户权限
	 * @param String
	 * @return
	 * @throws Exception  
	 */
	@LogAnnotation(eventCode="20320021",eventProcess="")
	public List<Menu> getUserPermission(String login) {
		return userDao.getUserPermission(login);
	}
	@LogAnnotation(eventCode="2032008",eventProcess="")
	public User getUserById(String userId) throws Exception {
		User user = new User(userId);
		return userDao.getUser(user);
	}
	@LogAnnotation(eventCode="20320014",eventProcess="")
	@Override
	public int updateUserPassword(User user) throws Exception {

		String password = user.getPassword();
		password = EncodeUtil.encode(password.getBytes());
		user.setPassword(password);

		return userDao.updateUserPassword(user);
	}
	@LogAnnotation(eventCode="20320016",eventProcess="")
	@Override
	public PagerModel getPageUser(HashMap<String, Object> qry) throws Exception {
		PagerModel pager = new PagerModel();
		pager.setDatas(getPageUserData(qry));
		pager.setTotal(getPageUserSize(qry));
		return pager;

	}	
	/**
	 * 通过查询条件进行分页检索
	 * @param qry
	 * @return
	 */
	private List<User> getPageUserData(Map<String, Object> qry) throws Exception {
		return userDao.getPageUserData(qry);
	}

	/**
	 * 通过查询条件进行分页计算
	 * @param qry
	 * @return
	 */
	private Integer getPageUserSize(Map<String, Object> qry) throws Exception {
		return userDao.getPageUserSize(qry);
	}
	@LogAnnotation(eventCode="2032004",eventProcess="")
	public User getUserWithRoles(String userId) throws Exception {
		User user = new User(userId);
		User result = userDao.getUser(user);
		if (result == null) {
			return result;
		}
		result.setRoles(roleDao.getRoleByUserId(result.getUserId().toString()));
		return result;
	}	
	
	@LogAnnotation(eventCode="20320011",eventProcess="")
	@Override
	public int disableUser(String id) throws Exception {
		return userDao.disableUser(id);
	}	
	@LogAnnotation(eventCode="20320013",eventProcess="")
	@Override
	public int resetUserPassword(String id) throws Exception {
		User user = new User(id);

		String password = Constants.USER_PASSWORD;
		password = EncodeUtil.encode(password.getBytes());
		user.setPassword(password);

		return userDao.updateUserPassword(user);
	}	
	
	@LogAnnotation(eventCode="20320010",eventProcess="")
	@Override
	public int deleteUser(String id) throws Exception {
		return userDao.deleteUser(id);
	}
	@LogAnnotation(eventCode="2032005",eventProcess="")
	public User saveUser(User user) throws Exception {
		if (user != null && user.getUserId() != null) {
			userDao.updateUser(user);
			
			/**
			 * 更新用户信息成功后，如果用户姓名、部门、团队发生变更，则更新任务表相关信息：
			 * 如果任务创建人姓名变更，只需修改任务表创建人姓名，updatecreateName；
			 * 如果任务执行人姓名变更，只需修改任务表执行人姓名，updateExecName；
			 * 如果用户部门、团队信息发生变更，则需要根据任务执行人变更任务表部门、团队信息，因为任务表
			 * 部门、团队信息是根据任务执行人信息获取，updateExecName；
			 */
			try {
				List<TaskInfo> taskInfos = adminDao.getTaskIdListByUserId(user.getUserId());//根据用户id查询任务创建人创建任务集合
				List<TaskInfo> taskInfos2 = adminDao.getTaskIdListByUserId2(user.getUserId());//根据用户id查询任务执行人的任务集合
				if(taskInfos!=null && taskInfos.size()>0){
					String createName = taskInfos.get(0).getCreate_name();
					if(!createName.equals(user.getUserName())){
						TaskInfo taskInfo = new TaskInfo();
						taskInfo.setAllocationUser(user.getUserId());
						taskInfo.setCreate_name(user.getUserName());
						adminDao.updatecreateName(taskInfo);
					}
				}
				if(taskInfos2!=null && taskInfos2.size()>0){
					String execName = taskInfos2.get(0).getExec_name();
					String department = taskInfos2.get(0).getDepartment();
					String team = taskInfos2.get(0).getTeam();
					if(!execName.equals(user.getUserName()) || !department.equals(user.getDepartment_id()) || !team.equals(user.getTeam_id())){
						TaskInfo taskInfo = new TaskInfo();
						taskInfo.setExecutedevtasksys(user.getUserId());
						if(!execName.equals(user.getUserName())){
							taskInfo.setExec_name(user.getUserName());
						}
						if(!department.equals(user.getDepartment_id())){
							taskInfo.setDepartment(user.getDepartment_id());
						}
						if(!team.equals(user.getTeam_id())){
							taskInfo.setTeam(user.getTeam_id());
						}
						adminDao.updateExecName(taskInfo);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			String id = userDao.getSeqNextval();
			user.setUserId(id);

			String password = Constants.USER_PASSWORD;
			password = EncodeUtil.encode(password.getBytes());
			user.setPassword(password);
			user.setUserSource("0");
			user.setStatus(Constants.EMP_STU_ONJOB);
			user.setCreateBy(user.getLastModifyBy());
			userDao.insertUser(user);
			//新增用户后发送邮件
			Map<String, String> map = new HashMap<String, String>();
			map.put("to_email", user.getEmail());
			map.put("loginName", user.getLoginName());
			SendEmailUtil.newPeopleEmail(map);
		}

		saveUserRoleRelations(user);
		return user;
	}	
	private int saveUserRoleRelations(User user) throws Exception {
		List<Role> roles = user.getRoles();
		if (roles == null || roles.size() == 0) {
			return 0;
		}

		userDao.deleteUserRoles(user.getUserId());
		for (Role role : roles) {
			Map<String, Object> obj = new HashMap<String, Object>();
			obj.put("userId", user.getUserId());
			obj.put("roleId", role.getId());
			userDao.saveUserRole(obj);
		}

		return roles.size();
	}	
	@LogAnnotation(eventCode="20320015",eventProcess="")
	@Override
	public User getUserByLoginName(String loginName) throws Exception {
		User user = new User();
		user.setLoginName(loginName);
		return userDao.getUser(user);
	}	
	@LogAnnotation(eventCode="20320012",eventProcess="")
	@Override
	public int enableUser(String id) throws Exception {
		return userDao.enableUser(id);
	}	


	/**
	 * 查询端角色与绩效端角色对应
	 * @param id
	 * @return
	 */
	@LogAnnotation(eventCode="20320016",eventProcess="")
	public List<Role> getRolecode(String roleid)throws Exception{
		return	userDao.getRolecode(roleid);
	};
	
	
	/**
	 * 登录时查询用户--角色权限
	 * @param String
	 * @return
	 * @throws Exception
	 */
	public List<Menu> getUserRolePermission(String roleCode) throws Exception{
		return	userDao.getUserRolePermission(roleCode);
	}
//////////////////汇金////////////////////////////////////////////////////////////////////////////
	
	
	@LogAnnotation(eventCode="2032023",eventProcess="")
	public User getIsFinaceLogin(Map<String,String> map) throws Exception {
			return userDao.getIsFinaceLogin(map);	
	}
	
	/**
	 * 获取登录用户信息
	 * @return
	 */
	@LogAnnotation(eventCode="20320024",eventProcess="")
	public User getFinaceSessionUser(Map map) throws Exception {
		return userDao.getFinaceLoginSessionUser(map);
	}
	
	/**
	 * 汇金端-获取ID集合
	 * @return
	 */
	@LogAnnotation(eventCode="20320025",eventProcess="")
	public List<User> getFinaceTDUser(Map map) throws Exception{
		return userDao.getFinaceTDUser(map);
	}
	
	/**
	 * 汇金端-登录时查询用户角色
	 * @param String
	 * @return
	 * @throws Exception
	 */
	@LogAnnotation(eventCode="20320026",eventProcess="")
	public List<Role> getFinaceUserRole(String login) throws Exception {
		
		return userDao.getFinaceUserRole(login);
	}
	
	/**
	 * 查询部门、团队、职位、岗位集合，用于新增下拉框选择
	 */
	@LogAnnotation(eventCode="20320027",eventProcess="")
	public List<Map<String, String>> getOrgInfo() {
		// TODO Auto-generated method stub
		return userDao.getOrgInfo();
	}
	
	/**
	 * 根据部门联动选择团队
	 */
	@LogAnnotation(eventCode="20320028",eventProcess="")
	public List getTeamByCode(String code) {
		// TODO Auto-generated method stub
		return userDao.getTeamByCode(code);
	}
	
	/**
	 * 批量删除用户以及用户角色关系
	 */
	@Override
	public void batchDeleteUser(List list) {
		// TODO Auto-generated method stub
		userDao.batchDeleteUser(list);
		userDao.batchDeleteUserRole(list);
	}

	@Override
	public List<User> getUserList(User user) {
		return userDao.getUserListByPro(user);
	}
	
	/**
	 * 新增用户时校验用户名是否重复
	 */
	@Override
	public int checkLoginName(String loginName) {
		// TODO Auto-generated method stub
		return userDao.checkLoginName(loginName);
	}
	
	/**
	 * 导入用户信息时  查出数据库已存在的用户名的List，用于校验用户名是否重复
	 */
	@Override
	public List<Map<String, String>> getLoginNameList() {
		// TODO Auto-generated method stub
		return userDao.getLoginNameList();
	}

	@Override
	public List<Map<String, String>> getBuMenList() {
		// TODO Auto-generated method stub
		return userDao.getBuMenList();
	}

	@Override
	public List<Map<String, String>> getRoleList() {
		// TODO Auto-generated method stub
		return userDao.getRoleList();
	}

	@Override
	public void batchStopUser(List list) {
		// TODO Auto-generated method stub
		userDao.batchStopUser(list);
	}

	@Override
	public List<User> getDepartmentManagerById(String zjid) {
		// TODO Auto-generated method stub
		return userDao.getDepartmentManagerById(zjid);
	}

	@Override
	public List<User> getAllUserList() throws Exception {
		// TODO Auto-generated method stub
		return userDao.getAllUserList();
	}

	@Override
	public List<User> getAllGeneralUsers() {
		return userDao.getAllGeneralUsers();
	}

	@Override
	public List<Map<String, Object>> getUserCountByDeparment() {
		return userDao.getUserCountByDeparment();
	}

    @Override
    public PageView<Map<String, Object>> getUserListpageView(Map<String,Object> map,User user)throws Exception{
        PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(user.getMaxResult(),user.getPage());// 需要设置当前页
    try {   
        // 获取列表条数
        map.put("maxResult", user.getMaxResult());
        map.put("page", user.getPage());
        int count = userDao.getUserSize1(map);
        // 获取列表数据
        List<Map<String, Object>> lcmList = userDao.getUserList1(map);

        QueryResult<Map<String, Object>> qr = new QueryResult<Map<String, Object>>();
        qr.setResultlist(lcmList);
        qr.setTotalrecord(count);
        pageView.setQueryResult(qr);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return pageView;
    }

    @Override
    public void updateUserHeadPortrait(User user){
        userDao.updateUser(user);
    }
}

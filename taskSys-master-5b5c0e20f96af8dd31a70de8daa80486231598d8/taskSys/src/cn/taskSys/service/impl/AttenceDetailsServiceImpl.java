package cn.taskSys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.taskSys.base.PageView;
import cn.taskSys.base.QueryResult;
import cn.taskSys.dao.AttenceDetailsDao;
import cn.taskSys.entity.AttenceDetails;
import cn.taskSys.service.IAttenceDetailsService;


@Service("attenceDetailsService")
public class AttenceDetailsServiceImpl implements  IAttenceDetailsService{
	@Autowired
	private AttenceDetailsDao attenceDetailsDao;

	@Override
	public List<AttenceDetails> getAllAttence(String nowDate)
			throws Exception {
		return attenceDetailsDao.getAllAttence(nowDate);
	}

	@Override
	public List<AttenceDetails> getAttenceByEmpCode(Map<String, Object> map)
			throws Exception {
		return attenceDetailsDao.getAttenceByEmpCode(map);
	}

	/**
	 * 处理后补打卡信息方法---修改
	 */
	@Override
	public void modifyRemarkFalg(String id) throws Exception {
		
		attenceDetailsDao.modifyRemarkFalg(id);
	}

	@Override
	public AttenceDetails getAttdetailsById(String attdetailsId)
			throws Exception {
		return attenceDetailsDao.getAttdetailsById(attdetailsId);
	}

	@Override
	public List<String> findLeaveEmpcodeList() throws Exception {
		return attenceDetailsDao.findLeaveEmpcodeList();
	}

	/**
	 * 处理后补打卡信息方法---查询
	 */
	@Override
	public List<AttenceDetails> getLastList() throws Exception {
		return attenceDetailsDao.getLastList();
	}
	/**
	 * 应出勤天数
	 */
	@Override
	public int getAttenceDay(Map<String, String> map) throws Exception {
		return attenceDetailsDao.getAttenceDay(map);
	}

	@Override
	public List<AttenceDetails> getAttenceDetailsList(
			AttenceDetails attenceDetails) throws Exception {
		return attenceDetailsDao.getAttenceDetailsList(attenceDetails);
	}

	@Override
	public PageView<AttenceDetails> getAttenceDetailsListpageView(AttenceDetails attenceDetails) throws Exception {
		PageView<AttenceDetails> pageView = new PageView<AttenceDetails>(
				attenceDetails.getMaxResult(),
				attenceDetails.getPage());// 需要设置当前页
		try {	
			
			int count = 0;// 获取列表条数
			List<AttenceDetails> attenceDetailsList = null;// 获取列表数据
			
			count = attenceDetailsDao.findAttenceDetailsListCount(attenceDetails);
			attenceDetailsList = attenceDetailsDao.findAttenceDetailsList(attenceDetails);
			
			
			QueryResult<AttenceDetails> qr = new QueryResult<AttenceDetails>();
			qr.setResultlist(attenceDetailsList);
			qr.setTotalrecord(count);
			pageView.setQueryResult(qr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageView;
	}
}

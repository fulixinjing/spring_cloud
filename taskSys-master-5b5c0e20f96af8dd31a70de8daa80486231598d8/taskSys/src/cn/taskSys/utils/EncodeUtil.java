package cn.taskSys.utils;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.taskSys.entity.Dictionary;

/**
 * java MD5加密算法
 */
public class EncodeUtil {
		 
	public static String D_SEX="100";//性别
	public static String D_DICTIONARY_STATE="110";//机构类型
	public static String D_TEAM_USERTYPE="111";//团队下 用户类型
	public static String D_MENDIAN_USERTYPE="112";//门店下 用户类型
	public static String D_AREA_USERTYPE="115";//大区下 用户类型
	public static String D_ZONGBU_USERTYPE="116";//总部下用户类型
	public static String D_CFSYB_USERTYPE="117";//财富事业部下用户类型
	public static String D_OCCUPATIONSITUATION="118";//职业情况（债权）
	public static String D_BACKMONEYRETURNREASON="119";//回款退回原因
	public static String D_BACKMONEYTYPE="120";//回款类型
	public static String D_BILLDATE="121";//账单日
	public static String D_ZJLX="500";//证件类型
	public static String D_HYLX="501";//婚姻
	public static String D_ZYLY="502";//职业
	public static String D_DWGMLY="503";//单位规模
	public static String D_KHH="504";//开户行
	public static String D_KHZ="505";//卡或折
	public static String D_SQFS="506";//收取方式
	public static String D_YESORNO="507";//是否
	public static String D_XYZL="508";//协议种类
	public static String D_XYBB="509";//协议版本
	public static String D_ZDR="510";//账单日
	public static String D_ZDSQFS="511";//账单收取方式
	public static String D_XTLY="512";//系统来源
	public static String D_ROLETREE_TYPE="RoleTreeCode";  //角色树类型	
	
	
	public static String SUCCEE="操作成功！";//操作成功
	public static String FAILURE="操作失败！";//操作失败
	public static String BUSY="服务器繁忙，请稍后尝试！";//服务器繁忙，请稍后尝试
	
	public static String BZ="10000000000001";//标准管理
	public static String KPIINDEX="10000000000002";//KPI评分管理
	public static String JXINDEX="10000000000003";//绩效提成管理
	public static String YJTJBB="10000000000004";//业绩统计报表
	public static String SYSINDEX="10000000000005";//系统管理

	
	public static String LOG_INFO="Info";//信息日志
	public static String LOG_DEBUG="Debug";//调试日志
	public static String LOG_ERROR="Error";//错误日志
	
	public static String LOG_MODUL_CJZX="101";//日志所属模块-出借咨询
	public static String LOG_MODUL_CJRJBXX="102";//日志所属模块-出借人基本信息
	public static String LOG_MODUL_KHSP="103";//日志所属模块-开户审批
	public static String LOG_MODUL_CJSQ="104";//日志所属模块-出借申请
	public static String LOG_MODUL_CJSQSP="105";//日志所属模块-出借申请审批
	public static String LOG_MODUL_ZQZR="106";//日志所属模块-债权转让
	public static String LOG_MODUL_CJRBGSP="107";//日志所属模块-出借人变更申请审批
	public static String LOG_MODUL_CJSQBGSP="108";//日志所属模块-出借申请变更申请审批
	public static String LOG_MODUL_ZQZYSP="109";//日志所属模块-债权占用
	public static String LOG_MODUL_RECJSQ="110";//日志所属模块-重新提交申请
	public static String LOG_MODUL_HKGL="111";//日志所属模块-回款管理
	public static String LOG_MODUL_MSZH="112";//日志所属模块-模式转换管理
	
	public static String LOG_MODUL_ZDPP="201";//日志所属模块-自动匹配
	public static String LOG_MODUL_PPTJ="202";//日志所属模块-匹配提交
	public static String LOG_MODUL_PPSPY="203";//日志所属模块-审批通过
	public static String LOG_MODUL_PPSPN="204";//日志所属模块-审批拒绝
	public static String LOG_MODUL_DGY="205";//日志所属模块-提交订购
	public static String LOG_MODUL_DGN="206";//日志所属模块-提交订购撤销
	public static String LOG_MODUL_TJHKY="207";//日志所属模块-提交划扣
	public static String LOG_MODUL_TJHKN="208";//日志所属模块-提交划扣撤销
	public static String LOG_MODUL_HKSPY="209";//日志所属模块-划扣审批
	public static String LOG_MODUL_HKSPN="210";//日志所属模块-审批拒绝
	public static String LOG_MODUL_FSYJ="211";//日志所属模块-发送邮件
	public static String LOG_MODUL_FSSMS="212";//日志所属模块-发送短信
	public static String LOG_MODUL_XXHK="213";//日志所属模块-线下划扣
	public static String LOG_MODUL_XSHK="214";//日志所属模块-线上
	
	public static String LOG_MODUL_YJFS="301";//交互管理-发送邮件
	public static String LOG_MODUL_YJMBXG="302";//交互管理-邮件模板修改
	public static String LOG_MODUL_YJMBSC="303";//交互管理-邮件模板删除
	public static String LOG_MODUL_YJMBXZ="304";//交互管理-邮箱模板新增
	public static String LOG_MODUL_DXDC="305";//交互管理-短信导出
	public static String LOG_MODUL_FSDX="306";//交互管理-短信发送
	public static String LOG_MODUL_DXXG="307";//交互管理-短信修改
	public static String LOG_MODUL_DXMBXZ="308";//交互管理-短信模板新增
	public static String LOG_MODUL_SJMBXG="309";//交互管理-手机模板修改
	public static String LOG_MODUL_SJMBSC="310";//交互管理-手机模板删除
	
	public static String LOG_MODUL_JSXZ="400";//系统管理-角色新增
	public static String LOG_MODUL_JSSC="401";//系统管理-角色删除
	public static String LOG_MODUL_JSXG="402";//系统管理-角色修改
	public static String LOG_MODUL_CDXZ="403";//系统管理-菜单新增
	public static String LOG_MODUL_CDXG="404";//系统管理-菜单修改
	public static String LOG_MODUL_ZDXZ="405";//系统管理-状态新增
	public static String LOG_MODUL_ZDXG="406";//系统管理-状态修改
	public static String LOG_MODUL_ZDSC="407";//系统管理-状态删除
	public static String LOG_MODUL_YHSQ="408";//系统管理-用户授权
	public static String LOG_MODUL_YHXZ="409";//系统管理-用户新增
	public static String LOG_MODUL_YHXG="410";//系统管理-用户修改
	public static String LOG_MODUL_YHSC="411";//系统管理-用户删除
	public static String LOG_MODUL_YHCZ="412";//系统管理-用户重置
	public static String LOG_MODUL_ZZJGXZ="413";//系统管理-组织机构新增
	public static String LOG_MODUL_ZZJGXG="414";//系统管理-组织机构修改
	public static String LOG_MODUL_QXYZ="415";//系统管理-区域新增
	public static String LOG_MODUL_QXXG="416";//系统管理-区域修改
	public static String LOG_MODUL_YGXZ="417";//系统管理-员工新增
	public static String LOG_MODUL_YGSC="418";//系统管理-员工删除
	public static String LOG_MODUL_YGTY="419";//系统管理-员工停用
	public static String LOG_MODUL_YGQY="420";//系统管理-员工启用
	public static String LOG_MODUL_CPMBXZ="421";//系统管理-产品模板新增
	public static String LOG_MODUL_CPMBXG="422";//系统管理-产品模板修改
	public static String LOG_MODUL_CPMBSC="423";//系统管理-产品模板删除
	
	
	public static String LOG_TASK_UPDATE="500";//任务管理-修改任务
	public static String LOG_TASK_DELETE="510";//任务管理-删除任务
	public static String LOG_TASK_SUBMIT="501";//任务管理-提交任务
	public static String LOG_TASK_DOWN="502";//任务管理-向下分配任务

	public static String LOG_MODEL_UPDATE="503";//修改操作
	public static String LOG_MODEL_DELETE="504";//删除操作
	public static String LOG_MODLE_SUBMIT="505";//新增操作
	
	
	public static int BILLDATE_ONE=7;
	public static int BILLDATE_TWO=15;
	public static int BILLDATE_THREE=23;
	public static int BILLDATE_FOUR=30;
	
	public static int RATE_DECIMAL=15;
	
	public static String PRODUCT_YMY="86";//月满盈ID
	public static String PRODUCT_DQ="0";//定期
	public static String PRODUCT_FD="1";//非定期
	public static String MATCH_PERCENTAGE="0.7";//匹配比例分配
	
	public static String XINJIE_TYPE_YB="一般职员";
	public static String XINJIE_TYPE_GX="高薪职员";
	public static String XINJIE_TYPE_YZ="业主";
	public static String XINJIE_TYPE_ZYZ="中小业主";
	public static String XINJIE_TYPE_DYZ="大企业主";
	public static String NHSYL="0.058";  //期限类产品年化收益率均为5.8%
	public static String FWF="0.05";  //非期限类产品服务费均为5.0%
	public static String RLV="0.01";  //非期限类产品服务费均为1.0%
	
	public static String SMS_NAME="{addressee}";  //短信模板替换（名字）
	public static String SMS_MONEY="{money}";  //短信模板替换（金额）
	public static String SMS_LEND_DATE="{lenddate}";  //短信模板替换（出借日期）
	public static String SMS_EXPIRE_DATE="{expiredate}";  //短信模板替换（出借日期）
	public static String SMS_PRODUCT="{product}";  //短信模板替换（出借模式）
	public static String SMS_BANK="{bank}";  //短信模板替换（开户行）
	public static String SMS_CARDNO="{cardno}";  //短信模板替换（银行卡尾号）
	public static String SMS_EXPRESSNO="{expressno}";  //短信模板替换（快递单号）
	public static String encode(byte[] source) {
		String s = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };// 用来将字节转换成16进制表示的字符
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest();// MD5 的计算结果是一个 128 位的长整数，
			// 用字节表示就是 16 个字节
			char str[] = new char[16 * 2];// 每个字节用 16 进制表示的话，使用两个字符，所以表示成 16
			// 进制需要 32 个字符
			int k = 0;// 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) {// 从第一个字节开始，对 MD5 的每一个字节// 转换成 16
				// 进制字符的转换
				byte byte0 = tmp[i];// 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];// 取字节中高 4 位的数字转换,// >>>
				// 为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf];// 取字节中低 4 位的数字转换

			}
			s = new String(str);// 换后的结果转换为字符串

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	
	public static List<Dictionary> getDictionaryInfo(String type,String except,List<Dictionary> dlist){
		boolean ky=true;
		String excepts[]=except.split(",");
		List<Dictionary> dictionarylist=new ArrayList<Dictionary>();
		if(dlist!=null && dlist.size()>0){
			for(int i=0;i<dlist.size();i++){
				Dictionary dictionary=dlist.get(i);
				if(type.equals(dictionary.getType_code())){
					for(int k=0;k<excepts.length;k++){
						if(excepts[k].equals(dictionary.getCode())){
							ky=false;
						}
					}
					if(!ky){
						ky=true;
						continue;
					}
					dictionarylist.add(dictionary);
				}
			}
		}
		
		return dictionarylist;
	}

	public static boolean isValidForString(String str)
	{
		boolean r=false;
		if(StringUtils.isNotEmpty(str) && StringUtils.isNotBlank(str))
		{
			r=true;
		}
		return r;
	}
	
	public static void main(String[] args) {
		System.out.println(EncodeUtil.encode("~!@#$%^&*()_+=-0987654321`\\][{}|".getBytes()));
		System.out.println(EncodeUtil.encode("qwertyuiop[][asdfghjkl;'zxcvbnm,./".getBytes()));
		System.out.println(EncodeUtil.encode("ZXCVBNM<>?ASDFGHJKL:\"QWERTYUIOP{{{}".getBytes()));
		System.out.println(EncodeUtil.encode("test1".getBytes()));
		System.out.println(EncodeUtil.encode("test1".getBytes()));
	}
}

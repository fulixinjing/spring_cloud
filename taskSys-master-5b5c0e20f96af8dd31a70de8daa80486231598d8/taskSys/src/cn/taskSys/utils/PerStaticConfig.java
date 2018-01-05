package cn.taskSys.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 配置文件静态类
 * @author ZJ
 * 2014-9-11
 *
 */
public class PerStaticConfig {
	private static Log logger = LogFactory.getFactory().getInstance(PerStaticConfig.class);
	/**
	 * 动态获取配置文件中的配置参数
	 * @param fileName 文件名称
	 * @param variableStr 变量名称
	 * @return 
	 * @author ZJ 2014-9-12
	 */
	public static String getPropertieValue(String fileName,String variableStr){
		try{
			String value =  PropertiesUtil.getValue(fileName, variableStr);
			if(value != null){
				logger.info("读取配置文件 " + fileName +" : "+variableStr +"-->"+value);
				return value.trim();
			}else{
				logger.error("读取配置文件失败" + fileName +" : "+variableStr);
				return "";
			}
		}catch(Exception e){
			logger.error("读取配置文件失败" + fileName +" : "+variableStr,e);
			return "";
		}
	}
	
	/** 
	 * 项目部署到服务器上的WEB-INF路径
	 */
	public static String  tempPath=getPropertieValue("per_config.properties", "tempPath");

	/**
	 * 客户信息 导出Excel表头
	 */
	public static String exportCustomer = getPropertieValue("per_config_static.properties", "exportCustomer");
	
	/**
	 * 团队经理职位ID
	 */
	public static String teamManger=getPropertieValue("per_config_static.properties", "teamManger");
	/**
	 * 客户经理职位ID
	 */
	public static String finPostId=getPropertieValue("per_config_static.properties", "finPostId");
	
	/**
	 *外访组合类型 1:外访家庭，2:外访单位，3:外访企业 
	 */
	public static String  WF_outVisteType=getPropertieValue("per_config_static.properties", "WF_outVisteType");  

	/**
	 *外访补传的资料类型 1:补充身份证明2:补充婚姻证明3:补充工作证明4:补充征信证明5:补充居住证明6:补充资产证明7:补充收入证明8:补充经营证明9:补充经营场地证明10:补充其他材料
	 */
	public static String  WF_materialtype=getPropertieValue("per_config_static.properties", "WF_materialtype");
	
	 
	//   富友划账相关配置  #####
	/**
	 * 富友 商户号
	 **/
	public static String  MERID=getPropertieValue("per_config_static.properties", "MERID");
	/**
	 * 富友 PWD
	 **/
	public static String  channelPassWord=getPropertieValue("per_config_static.properties", "channelPassWord");
	
	/**
	 * 富友 请求类型
	 */
	public static String  REQTYPE=getPropertieValue("per_config_static.properties", "REQTYPE");
	/**
	 * 富友 版本号
	 */
	public static String  VER=getPropertieValue("per_config_static.properties", "VER");
	/**
	 * 富友 编码格式
	 */
	public static String  CHARSET=getPropertieValue("per_config_static.properties", "CHARSET");
	/**
	 *请求地址
	 */
	public static String  URL=getPropertieValue("per_config_static.properties", "URL");
	/**
	 * 富友 超时时间  60000ms=1分钟
	 */
	public static String  TIMEOUT=getPropertieValue("per_config_static.properties", "TIMEOUT");
	
	/**
	 * 允许上传文件类型  JPG,GIF,PNG,BMP,JPEG
	 */
	public static String allowUpFileType = getPropertieValue("per_config_static.properties", "allowUpFileType");
	
	
	//###  合同模板相关配置  #####
	/**
	 *ftlTempNames1 ：信借合同模板
	 */
	public static String  ftlTempNames0=getPropertieValue("per_config_static.properties", "ftlTempNames0");

	/**
	 *ftlTempNames290 :车借移交类90
	 */
	public static String  ftlTempNames290=getPropertieValue("per_config_static.properties", "ftlTempNames290");
	/**
	 *ftlTempNames350 :车借移交类150
	 */
	public static String  ftlTempNames350=getPropertieValue("per_config_static.properties", "ftlTempNames350");


	//###  罚款利率  1 移交 2 GPS  #####
	public static String  PenaltyRate1=getPropertieValue("per_config_static.properties", "PenaltyRate1");
	public static String  PenaltyRate2=getPropertieValue("per_config_static.properties", "PenaltyRate2");

	//###  违约金利率  1 移交 2 GPS  #####
	public static String  Penaltyinterestrate=getPropertieValue("per_config_static.properties", "Penaltyinterestrate");
	
	public static String  huijin=getPropertieValue("per_config_static.properties", "huijin");
	public static String  huicheng=getPropertieValue("per_config_static.properties", "huicheng");
	public static String  huimin=getPropertieValue("per_config_static.properties", "huimin");
	public static String  weiyuejin=getPropertieValue("per_config_static.properties", "weiyuejin");
	public static String  faxi=getPropertieValue("per_config_static.properties", "faxi");
	public static String  yjfaxi=getPropertieValue("per_config_static.properties", "yjfaxi");
	public static String  yuelixi=getPropertieValue("per_config_static.properties", "yuelixi");
	

	//###   好易联好易联支付接口相关配置  #####
	/**
	 * 好易联 请求URL
	 */
	public static String  Stylw_SERVER_URL=getPropertieValue("per_config_static.properties", "Stylw_SERVER_URL");
	/**
	 * 好易联 密钥文件路径
	 */
	public static String  Stylw_pathCer= getPropertieValue("per_config.properties", "tempPath") + getPropertieValue("per_config_static.properties", "Stylw_pathCer");
	/**
	 * 好易联 密钥文件路径
	 */
	public static String  Stylw_pathPfx= getPropertieValue("per_config.properties", "tempPath") + getPropertieValue("per_config_static.properties", "Stylw_pathPfx");
	
	/**
	 * 好易联 交易代码100004
	 */
	public static String  Stylw_TRX_CODE=getPropertieValue("per_config_static.properties", "Stylw_TRX_CODE");
	
	/**
	 * 好易联 版本
	 */
	public static String  Stylw_VERSION=getPropertieValue("per_config_static.properties", "Stylw_VERSION");
	
	/**
	 * 好易联 数据格式 2：xml格式
	 */
	public static String  Stylw_DATA_TYPE=getPropertieValue("per_config_static.properties", "Stylw_DATA_TYPE");
	
	/**
	 * 好易联 处理级别 0实时处理 默认0
	 */
	public static String  Stylw_LEVEL=getPropertieValue("per_config_static.properties", "Stylw_LEVEL");
	/**
	 * 好易联 用户名
	 */
	public static String  Stylw_USER_NAME=getPropertieValue("per_config_static.properties", "Stylw_USER_NAME");
	/**
	 * 好易联 用户密码
	 */
	public static String  Stylw_USER_PASS=getPropertieValue("per_config_static.properties", "Stylw_USER_PASS");
	/**
	 * 好易联 业务代码
	 */
	public static String  Stylw_BUSINESS_CODE=getPropertieValue("per_config_static.properties", "Stylw_BUSINESS_CODE");
	/**
	 * 好易联 商户代码
	 */
	public static String  Stylw_MERCHANT_ID=getPropertieValue("per_config_static.properties", "Stylw_MERCHANT_ID");
	
	/** 好易联商户代码 */
	public static String Stylw_MERCHANT_ID_XINTUO01 = getPropertieValue("per_config_static.properties", "Stylw_MERCHANT_ID_XINTUO01");
	
	//###  生成在线文档相关  #####
	/** 
	 * html2pdf工具路径
	 */
	public static String  html2pdfPath=getPropertieValue("per_config.properties", "html2pdfPath");
	/** 
	 * pdf2swf工具路径
	 */
	public static String  pdf2swfPath=getPropertieValue("per_config.properties", "pdf2swfPath");
	
	/** 
	 * 生成合同文件目录
	 */
	public static String  swfPath=getPropertieValue("per_config.properties", "swfPath");
	/**
	 * 文件上传路径  #####
	 */
	public static String  uploadPath=getPropertieValue("per_config.properties", "uploadPath");
	
	/**
	 * 挂载虚拟图片目录
	 */
	public static String  CK_BASH_URL=getPropertieValue("per_config.properties", "CK_BASH_URL");
	
	
	/**
	 * 跑批机器Ip
	 */
	public static String  batch_job_Ip = getPropertieValue("per_config_static.properties", "batch_job_Ip");
	 
}
 
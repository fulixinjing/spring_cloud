package cn.taskSys.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ReadXmlUtil {
	/**
	 * 汇金员工信息导出
	 * 
	 * @return
	 */
	public Map readHjEmployeeInfoXML() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(this.getClass()
					.getResource("/").getPath()
					+ "exportReport.xml"));
			Element rootElement = document.getDocumentElement();
			NodeList list = rootElement.getElementsByTagName("hjEmployeeNAME");  //报表名称
			Element element = (Element) list.item(0);
			String hjEmployeeListNAME = element.getChildNodes().item(0)
					.getNodeValue();
			NodeList list1 = rootElement
					.getElementsByTagName("hjEmployeeenEMP_CODE");  //员工编号
			Element element1 = (Element) list1.item(0);
			String enEMPCODE = element1.getChildNodes().item(0).getNodeValue();
			NodeList list2 = rootElement
					.getElementsByTagName("hjEmployeeenEMP_NAME");  //员工姓名
			Element element2 = (Element) list2.item(0);
			String enEMPNAME = element2.getChildNodes().item(0).getNodeValue();
			NodeList list3 = rootElement
					.getElementsByTagName("hjEmployeeenORG");  //机构
			Element element3 = (Element) list3.item(0);
			String enORG = element3.getChildNodes().item(0).getNodeValue();
			NodeList list4 = rootElement
					.getElementsByTagName("hjEmployeeenPOST");  //岗位
			Element element4 = (Element) list4.item(0);
			String enPOST = element4.getChildNodes().item(0)
					.getNodeValue();
			NodeList list5 = rootElement
					.getElementsByTagName("hjEmployeeenOVERDUERATE");  //逾期率
			Element element5 = (Element) list5.item(0);
			String enOVERDUERATE = element5.getChildNodes().item(0)
					.getNodeValue();
			NodeList list6 = rootElement
					.getElementsByTagName("hjEmployeeenONJOBRATE");  //在职率
			Element element6 = (Element) list6.item(0);
			String enONJOBRATE = element6.getChildNodes().item(0)
					.getNodeValue();			
			NodeList list7 = rootElement
					.getElementsByTagName("hjEmployeeenSTORERATE");  //门店业绩达成率
			Element element7 = (Element) list7.item(0);
			String enSTORERATE = element7.getChildNodes().item(0)
					.getNodeValue();			
			NodeList list8 = rootElement
					.getElementsByTagName("hjEmployeeenCITYRATE");  //城市业绩达成率
			Element element8 = (Element) list8.item(0);
			String enCITYRATE = element8.getChildNodes().item(0)
					.getNodeValue();				
			NodeList list10 = rootElement
					.getElementsByTagName("hjEmployeeenAREARATE");  //区域总业绩达成率
			Element element10 = (Element) list10.item(0);
			String enAREARATE = element10.getChildNodes().item(0)
					.getNodeValue();	
			NodeList list11 = rootElement
					.getElementsByTagName("hjEmployeeenROLLBACKRATE");  //回退率
			Element element11 = (Element) list11.item(0);
			String enROLLBACKRATE = element11.getChildNodes().item(0)
					.getNodeValue();	
			NodeList list9 = rootElement
					.getElementsByTagName("hjEmployeeenTASKAMOUNT");  //任务额
			Element element9 = (Element) list9.item(0);
			String enTASKAMOUNT = element9.getChildNodes().item(0)
					.getNodeValue();					
			NodeList list12 = rootElement
					.getElementsByTagName("hjEmployeeenUSEDATE");  //应用日期
			Element element12 = (Element) list12.item(0);
			String enUSEDATE = element12.getChildNodes().item(0)
					.getNodeValue();	//应用日期
			
			NodeList list13 = rootElement
					.getElementsByTagName("hjEmployeecnEMP_CODE");//员工编号
			Element element13 = (Element) list13.item(0);
			String cnEMPCODE = element13.getChildNodes().item(0).getNodeValue();			
			NodeList list14 = rootElement
					.getElementsByTagName("hjEmployeecnEMP_NAME");//员工姓名
			Element element14 = (Element) list14.item(0);
			String cnEMPNAME = element14.getChildNodes().item(0).getNodeValue();	
			NodeList list15 = rootElement
					.getElementsByTagName("hjEmployeecnORG");//机构
			Element element15 = (Element) list15.item(0);
			String cnORG = element15.getChildNodes().item(0).getNodeValue();				
			NodeList list16 = rootElement
					.getElementsByTagName("hjEmployeecnPOST");//岗位
			Element element16 = (Element) list16.item(0);
			String cnPOST = element16.getChildNodes().item(0).getNodeValue();	
			NodeList list17 = rootElement
					.getElementsByTagName("hjEmployeecnOVERDUERATE");//逾期率
			Element element17 = (Element) list17.item(0);
			String cnOVERDUERATE = element17.getChildNodes().item(0).getNodeValue();					
			NodeList list18 = rootElement
					.getElementsByTagName("hjEmployeecnONJOBRATE");//在职率
			Element element18 = (Element) list18.item(0);
			String cnONJOBRATE = element18.getChildNodes().item(0).getNodeValue();		
			NodeList list19 = rootElement
					.getElementsByTagName("hjEmployeecnSTORERATE");//门店业绩达成率
			Element element19 = (Element) list19.item(0);
			String cnSTORERATE = element19.getChildNodes().item(0).getNodeValue();					
			NodeList list20 = rootElement
					.getElementsByTagName("hjEmployeecnCITYRATE");//城市业绩达成率
			Element element20 = (Element) list20.item(0);
			String cnCITYRATE = element20.getChildNodes().item(0).getNodeValue();									
			NodeList list22 = rootElement
					.getElementsByTagName("hjEmployeecnAREARATE");//区域总业绩达成率
			Element element22 = (Element) list22.item(0);
			String cnAREARATE = element22.getChildNodes().item(0).getNodeValue();	
			NodeList list23 = rootElement
					.getElementsByTagName("hjEmployeecnROLLBACKRATE");//回退率
			Element element23 = (Element) list23.item(0);
			String cnROLLBACKRATE = element23.getChildNodes().item(0).getNodeValue();
			NodeList list21 = rootElement
					.getElementsByTagName("hjEmployeecnTASKAMOUNT");//任务额
			Element element21 = (Element) list21.item(0);
			String cnTASKAMOUNT = element21.getChildNodes().item(0).getNodeValue();			
			NodeList list24 = rootElement
					.getElementsByTagName("hjEmployeecnUSEDATE");//应用日期
			Element element24 = (Element) list24.item(0);
			String cnUSEDATE = element24.getChildNodes().item(0).getNodeValue();			
			
			Map map = new HashMap();
			//以下为excel所取对应的值
			map.put("hjEmployeeListNAME", hjEmployeeListNAME);  //sheet页名称
			map.put("enEMPCODE", enEMPCODE);  //员工编号
			map.put("enEMPNAME", enEMPNAME);  //员工姓名
			map.put("enORG", enORG);  //机构
			map.put("enPOST", enPOST);  //岗位
			map.put("enOVERDUERATE", enOVERDUERATE);  //逾期率
			map.put("enONJOBRATE", enONJOBRATE);  //在职率
			map.put("enSTORERATE", enSTORERATE);  //门店业绩达成率
			map.put("enCITYRATE", enCITYRATE);  //城市业绩达成率
			map.put("enAREARATE", enAREARATE);  //区域总业绩达成率
			map.put("enROLLBACKRATE", enROLLBACKRATE);  //回退率
			map.put("enTASKAMOUNT", enTASKAMOUNT);  //任务额
			map.put("enUSEDATE", enUSEDATE);  //应用日期
			//以下为excel的标题名称
			map.put("cnEMPCODE", cnEMPCODE);    //员工编号
			map.put("cnEMPNAME", cnEMPNAME);  //员工姓名
			map.put("cnORG", cnORG);  //机构
			map.put("cnPOST", cnPOST);  //岗位
			map.put("cnOVERDUERATE", cnOVERDUERATE);  //逾期率
			map.put("cnONJOBRATE",cnONJOBRATE);  //在职率
			map.put("cnSTORERATE",cnSTORERATE);  //门店业绩达成率
			map.put("cnCITYRATE", cnCITYRATE);  //城市业绩达成率
			map.put("cnAREARATE", cnAREARATE);  //区域总业绩达成率
			map.put("cnROLLBACKRATE", cnROLLBACKRATE);  //回退率
			map.put("cnTASKAMOUNT", cnTASKAMOUNT);  //任务额
			map.put("cnUSEDATE", cnUSEDATE);  //应用日期

			return map;
		} catch (Exception e) {
			return null;
		}
	}
	/**
	 * 汇金员工绩效模板
	 * @return map
	 */
	public Map readPerforTemplateXML() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(new File(this.getClass()
					.getResource("/").getPath()
					+ "exportReport.xml"));
			Element rootElement = document.getDocumentElement();
			NodeList list = rootElement.getElementsByTagName("hjPerforTemplateNAME");  //报表名称
			Element element = (Element) list.item(0);
			String hjPerforListNAME = element.getChildNodes().item(0)
					.getNodeValue();
			NodeList list1 = rootElement
					.getElementsByTagName("hjPerforTemplateenEMP_CODE");  //员工编号
			Element element1 = (Element) list1.item(0);
			String enEMPCODE = element1.getChildNodes().item(0).getNodeValue();
			NodeList list2 = rootElement
					.getElementsByTagName("hjPerforTemplateenEMP_NAME");  //员工姓名
			Element element2 = (Element) list2.item(0);
			String enEMPNAME = element2.getChildNodes().item(0).getNodeValue();
			NodeList list3 = rootElement
					.getElementsByTagName("hjPerforTemplateenORG");  //机构
			Element element3 = (Element) list3.item(0);
			String enORG = element3.getChildNodes().item(0).getNodeValue();
			NodeList list4 = rootElement
					.getElementsByTagName("hjPerforTemplateenPOST");  //岗位
			Element element4 = (Element) list4.item(0);
			String enPOST = element4.getChildNodes().item(0)
					.getNodeValue();
			NodeList list5 = rootElement
					.getElementsByTagName("hjPerforTemplateenOVERDUEPENALTY");  //逾期处罚
			Element element5 = (Element) list5.item(0);
			String enOVERDUEPENALTY = element5.getChildNodes().item(0)
					.getNodeValue();
			NodeList list6 = rootElement
					.getElementsByTagName("hjPerforTemplateenADDMINUSPOINTS");  //加扣分
			Element element6 = (Element) list6.item(0);
			String enADDMINUSPOINTS = element6.getChildNodes().item(0)
					.getNodeValue();			
			NodeList list7 = rootElement
					.getElementsByTagName("hjPerforTemplateenMANAGEFACTOR");  //管理系数
			Element element7 = (Element) list7.item(0);
			String enMANAGEFACTOR = element7.getChildNodes().item(0)
					.getNodeValue();			
			NodeList list8 = rootElement
					.getElementsByTagName("hjPerforTemplateenCHARGEITEM");  //扣款项
			Element element8 = (Element) list8.item(0);
			String enCHARGEITEM = element8.getChildNodes().item(0)
					.getNodeValue();				
			NodeList list9 = rootElement
					.getElementsByTagName("hjPerforTemplateenPERFORDATE");  //绩效日期
			Element element9 = (Element) list9.item(0);
			String enPERFORDATE = element9.getChildNodes().item(0)
					.getNodeValue();	

			
			NodeList list13 = rootElement
					.getElementsByTagName("hjPerforTemplatecnEMP_CODE");//员工编号
			Element element13 = (Element) list13.item(0);
			String cnEMPCODE = element13.getChildNodes().item(0).getNodeValue();			
			NodeList list14 = rootElement
					.getElementsByTagName("hjPerforTemplatecnEMP_NAME");//员工姓名
			Element element14 = (Element) list14.item(0);
			String cnEMPNAME = element14.getChildNodes().item(0).getNodeValue();	
			NodeList list15 = rootElement
					.getElementsByTagName("hjPerforTemplatecnORG");//机构
			Element element15 = (Element) list15.item(0);
			String cnORG = element15.getChildNodes().item(0).getNodeValue();				
			NodeList list16 = rootElement
					.getElementsByTagName("hjPerforTemplatecnPOST");//岗位
			Element element16 = (Element) list16.item(0);
			String cnPOST = element16.getChildNodes().item(0).getNodeValue();	
			NodeList list17 = rootElement
					.getElementsByTagName("hjPerforTemplatecnOVERDUEPENALTY");//逾期处罚
			Element element17 = (Element) list17.item(0);
			String cnOVERDUEPENALTY = element17.getChildNodes().item(0).getNodeValue();					
			NodeList list18 = rootElement
					.getElementsByTagName("hjPerforTemplatecnADDMINUSPOINTS");//加扣分
			Element element18 = (Element) list18.item(0);
			String cnADDMINUSPOINTS = element18.getChildNodes().item(0).getNodeValue();		
			NodeList list19 = rootElement
					.getElementsByTagName("hjPerforTemplatecnMANAGEFACTOR");//管理系数
			Element element19 = (Element) list19.item(0);
			String cnMANAGEFACTOR = element19.getChildNodes().item(0).getNodeValue();					
			NodeList list20 = rootElement
					.getElementsByTagName("hjPerforTemplatecnCHARGEITEM");//扣款项
			Element element20 = (Element) list20.item(0);
			String cnCHARGEITEM = element20.getChildNodes().item(0).getNodeValue();									
			NodeList list22 = rootElement
					.getElementsByTagName("hjPerforTemplatecnPERFORDATE");//绩效日期
			Element element22 = (Element) list22.item(0);
			String cnPERFORDATE = element22.getChildNodes().item(0).getNodeValue();		
			
			Map map = new HashMap();
			//以下为excel所取对应的值
			map.put("hjPerforListNAME", hjPerforListNAME);  //sheet页名称
			map.put("enEMPCODE", enEMPCODE);  //员工编号
			map.put("enEMPNAME", enEMPNAME);  //员工姓名
			map.put("enORG", enORG);  //机构
			map.put("enPOST", enPOST);  //岗位
			map.put("enOVERDUEPENALTY", enOVERDUEPENALTY);  //逾期处罚
			map.put("enADDMINUSPOINTS", enADDMINUSPOINTS);  //加扣分
			map.put("enMANAGEFACTOR", enMANAGEFACTOR);  //管理系数
			map.put("enCHARGEITEM", enCHARGEITEM);  //扣款项
			map.put("enPERFORDATE", enPERFORDATE);  //绩效日期
			//以下为excel的标题名称
			map.put("cnEMPCODE", cnEMPCODE);    //员工编号
			map.put("cnEMPNAME", cnEMPNAME);  //员工姓名
			map.put("cnORG", cnORG);  //机构
			map.put("cnPOST", cnPOST);  //岗位
			map.put("cnOVERDUEPENALTY", cnOVERDUEPENALTY);  //逾期处罚
			map.put("cnADDMINUSPOINTS",cnADDMINUSPOINTS);  //加扣分
			map.put("cnMANAGEFACTOR",cnMANAGEFACTOR);  //管理系数
			map.put("cnCHARGEITEM", cnCHARGEITEM);  //扣款项
			map.put("cnPERFORDATE", cnPERFORDATE);  //绩效日期

			return map;
		} catch (Exception e) {
			return null;
		}
	}		
}

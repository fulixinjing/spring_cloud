package cn.taskSys.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;


public class ExportExcelUtil extends ExcelTempleteUtil {

	private static final int cloumn_with = 15;

	/**
	 *  可以智能进行表格的合并
	 * 
	 * @param fileName
	 * 
	 * @param response
	 * 
	 *            mybatis返回的数据源的封装 例如:List<Map<String,Object>>
	 * @param dataset
	 * 
	 *            没一个sheet显示的标题
	 * @param headerss
	 * 
	 *            你sql语句select的column 的name,要和header对应
	 * @param keys
	 * 
	 *            页脚显示的文字
	 * @param sheets
	 */
	@SuppressWarnings("rawtypes")
	public static void exportExcelAdvanced(String fileName,
			HttpServletResponse response, Collection[] dataset,
			String[][] headerss, String[][] keys, String[] sheets) {
		try {
			OutputStream ouputStream = response.getOutputStream();
			/**
			 * 建立一个导出excel工程 
			 */
			HSSFWorkbook workbook = new HSSFWorkbook();

			// 设置标题单元格样式 和 标题的字体
			HSSFCellStyle style = configCellStyle(workbook);
			// 设置内容单元格样式 和 内容的字体
			HSSFCellStyle style2 = configContentStyle(workbook);

			for (int sheetIndex = 0; sheetIndex < dataset.length; sheetIndex++) {
//				System.out.println("当前第" + (sheetIndex + 1) + "页");
				// 指定页脚
				HSSFSheet sheet = workbook.createSheet(sheets[sheetIndex]);
				sheet.setDefaultColumnWidth(cloumn_with);
//				System.out.println("");
				/**
				 * 开始生成每一个sheet 先完成标题部分
				 */
//				System.out.println("开始画sheet" + (sheetIndex + 1));
				String[] titles = headerss[sheetIndex];
				// 产生表格标题行
				HSSFRow row = sheet.createRow(0);
				for (int cellIndex = 0; cellIndex < titles.length; cellIndex++) {
					HSSFCell cell = row.createCell(cellIndex);
					cell.setCellStyle(style);
					HSSFRichTextString text = new HSSFRichTextString(
							titles[cellIndex]);
//					System.out.print(titles[cellIndex] + "\t");
					cell.setCellValue(text);
				}
				/**
				 * 标题制造完毕
				 */
//				System.out.println("\r\n");
				List datasetList = (List) dataset[sheetIndex];

				String[] keyss = keys[sheetIndex];

				int rowIndex = 1;
				LinkedHashMap<String, String> cellMap = new LinkedHashMap<String, String>();
				for (int i = 0; i < datasetList.size(); i++, rowIndex++) {
					row = sheet.createRow(rowIndex);
//					System.out.println("当前第" + (rowIndex) + "行");
					Map dataMap = (Map) datasetList.get(i);
					for (int k = 0; k < keyss.length; k++) {
//						System.out.println("当前第" + (k + 1) + "列");
						HSSFCell cellContext = row.createCell(k);
						cellContext.setCellStyle(style2);
						String value = null;
//						System.out.println(dataMap.get(keyss[k]));
						if (dataMap.get(keyss[k]) == null) {
							value = "";
						} else {
							if (dataMap.get(keyss[k]) instanceof Date) {
								SimpleDateFormat fmt = new SimpleDateFormat(
										"yyyy-MM-dd");
								value = fmt.format(
										dataMap.get(keyss[k])).toString();
							} else if (dataMap.get(keyss[k]) instanceof Number) {
								DecimalFormat df2 = (DecimalFormat) DecimalFormat
										.getInstance();
								df2.applyPattern("0.00");
								value = df2.format(
										dataMap.get(keyss[k])).toString();
							} else {
								value = dataMap.get(keyss[k])
										.toString();
							}
						}
						if(cellMap.get(k+"")!=null&&cellMap.get(k+"").equals(value)) {
							sheet = mergedRegion(sheet, i, i+1, k, k);
							style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);//居中
							cellContext.setCellStyle(style2);
						}else {
							cellContext.setCellValue(value);
							cellMap.put(k+"", value);
						}
						
					}
				}
			}

			response.setCharacterEncoding("GBK");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="
					+ new String(fileName.getBytes("GBK"), "ISO8859-1"));

			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	
	/**
	 * 你下载指定的文件名,需带扩展名
	 * 
	 * @param fileName
	 * 
	 * @param response
	 * 
	 *            mybatis返回的数据源的封装 例如:List<Map<String,Object>>
	 * @param dataset
	 * 
	 *            每一个sheet显示的标题
	 * @param headerss
	 * 
	 *            你sql语句select的column 的name,要和header对应
	 * @param keys
	 * 
	 *            页脚显示的文字
	 * @param sheets
	 */
	@SuppressWarnings("rawtypes")
	public static void exportExcel(String fileName,
			HttpServletResponse response, List<List> dataset,
			String[][] headerss, String[][] keys, String[] sheets) {
		try {
			OutputStream ouputStream = response.getOutputStream();
			/**
			 * 建立一个导出excel工程 
			 */
			HSSFWorkbook workbook = new HSSFWorkbook();

			// 设置标题单元格样式 和 标题的字体
			HSSFCellStyle style = configCellStyle(workbook);
			// 设置内容单元格样式 和 内容的字体
			HSSFCellStyle style2 = configContentStyle(workbook);
			//设置修改字样为红色
			HSSFCellStyle styleRed = configContentStyleRed(workbook);

			for (int sheetIndex = 0; sheetIndex < dataset.size(); sheetIndex++) {
				// 指定页脚
				HSSFSheet sheet = workbook.createSheet(sheets[sheetIndex]);
				sheet.setDefaultColumnWidth(cloumn_with);
				/**
				 * 开始生成每一个sheet 先完成标题部分
				 */
				String[] titles = headerss[0];
				// 产生表格标题行
				HSSFRow row = sheet.createRow(0);
				for (int cellIndex = 0; cellIndex < titles.length; cellIndex++) {
					HSSFCell cell = row.createCell(cellIndex);
					cell.setCellStyle(style);
					HSSFRichTextString text = new HSSFRichTextString(
							titles[cellIndex]);
					cell.setCellValue(text);
				}
				/**
				 * 标题制造完毕
				 */
				List datasetList = (List) dataset.get(sheetIndex);

				String[] keyss = keys[0];

				int rowIndex = 1;
				for (int i = 0; i < datasetList.size(); i++, rowIndex++) {
					row = sheet.createRow(rowIndex);
					Map dataMap = (Map) datasetList.get(i);
					for (int k = 0; k < keyss.length; k++) {
						HSSFCell cellContext = row.createCell(k);
						if("禁止修改".equals(StringUtil.nvlString(dataMap.get(keyss[k])))){
							cellContext.setCellStyle(styleRed);
						}else{
							cellContext.setCellStyle(style2);
						}
						
						if (dataMap.get(keyss[k]) == null) {
							cellContext.setCellValue("");
						} else {
							if (dataMap.get(keyss[k]) instanceof Date) {
								SimpleDateFormat fmt = new SimpleDateFormat(
										"yyyy-MM-dd");
								cellContext.setCellValue(fmt.format(
										dataMap.get(keyss[k])).toString());
							} else if (dataMap.get(keyss[k]) instanceof Number) {
								DecimalFormat df2 = (DecimalFormat) DecimalFormat
										.getInstance();
								df2.applyPattern("0.00");
								cellContext.setCellValue(df2.format(
										dataMap.get(keyss[k])).toString());
							} else {
								cellContext.setCellValue(dataMap.get(keyss[k])
										.toString());
							}
						}
					}

				}
			}

			response.setCharacterEncoding("GBK");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="
					+ new String(fileName.getBytes("GBK"), "ISO8859-1"));

			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 你下载指定的文件名,需带扩展名
	 * 
	 * @param fileName
	 * 
	 * @param response
	 * 
	 *            mybatis返回的数据源的封装 例如:List<Map<String,Object>>
	 * @param dataset
	 * 
	 *            每一个sheet显示的标题
	 * @param headerss
	 * 
	 *            你sql语句select的column 的name,要和header对应
	 * @param keys
	 * 
	 *            页脚显示的文字
	 * @param sheets
	 */
	@SuppressWarnings("rawtypes")
	public static void exportExcel2(String fileName,
			HttpServletResponse response, List<List> dataset,
			String[][] headerss, String[][] keys, String[] sheets) {
		try {
			OutputStream ouputStream = response.getOutputStream();
			/**
			 * 建立一个导出excel工程 
			 */
			SXSSFWorkbook workbook = new SXSSFWorkbook(1000);   //内存中保留 1000 条数据，以免内存溢出，其余写入 硬盘 

			// 设置标题单元格样式 和 标题的字体
			CellStyle style = configCellStyle2(workbook);
			// 设置内容单元格样式 和 内容的字体
			CellStyle style2 = configContentStyle2(workbook);
			//设置修改字样为红色
			//HSSFCellStyle styleRed = configContentStyleRed(workbook);

			for (int sheetIndex = 0; sheetIndex < dataset.size(); sheetIndex++) {
				// 指定页脚
				Sheet sheet = workbook.createSheet(sheets[sheetIndex]);
				
				
				/**
				 * 开始生成每一个sheet 先完成标题部分
				 */
				String[] titles = headerss[0];
				// 产生表格标题行
				Row row = sheet.createRow(0);
				for (int cellIndex = 0; cellIndex < titles.length; cellIndex++) {
					sheet.setColumnWidth((short) cellIndex, (short) getCellWidth(cellIndex));// 设置列宽  
					Cell cell = row.createCell(cellIndex);
					cell.setCellStyle(style);
					HSSFRichTextString text = new HSSFRichTextString(
							titles[cellIndex]);
					cell.setCellValue(text);
				}
				/**
				 * 标题制造完毕
				 */
				List datasetList = (List) dataset.get(sheetIndex);

				String[] keyss = keys[0];

				int rowIndex = 1;
				for (int i = 0; i < datasetList.size(); i++, rowIndex++) {
					row = sheet.createRow(rowIndex);
					Map dataMap = (Map) datasetList.get(i);
					for (int k = 0; k < keyss.length; k++) {
						Cell cellContext = row.createCell(k);
						if("禁止修改".equals(StringUtil.nvlString(dataMap.get(keyss[k])))){
							//cellContext.setCellStyle(styleRed);
						}else{
							cellContext.setCellStyle(style2);
						}
						
						if (dataMap.get(keyss[k]) == null) {
							cellContext.setCellValue("");
						} else {
							if (dataMap.get(keyss[k]) instanceof Date) {
								SimpleDateFormat fmt = new SimpleDateFormat(
										"yyyy-MM-dd");
								cellContext.setCellValue(fmt.format(
										dataMap.get(keyss[k])).toString());
							} else if (dataMap.get(keyss[k]) instanceof Number) {
								DecimalFormat df2 = (DecimalFormat) DecimalFormat
										.getInstance();
								df2.applyPattern("0.00");
								cellContext.setCellValue(df2.format(
										dataMap.get(keyss[k])).toString());
							} else {
								cellContext.setCellValue(dataMap.get(keyss[k])
										.toString());
							}
						}
					}

				}
			}

			response.setCharacterEncoding("GBK");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="
					+ new String(fileName.getBytes("GBK"), "ISO8859-1"));

			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * 设置标题单元格的样式-列的宽度
	 * @date 2015-02-03	
	 * @param workbook
	 * @return
	 */
	
	private static int getCellWidth(int count){
		int number=1500;
		if(count==0){
			number=9000;
		}else if(count==5){
			number=6000;
		}else if (count==2 || count==4 || count==8) {
			number=5500;
		}else{
			number=4000;
		}
		
		return number;
	} 

	
	

	/**
	 * 确保数据库的每一列都有值时使用这个比较简单
	 * 
	 * 导出文件名字,需添加扩展名
	 * 
	 * @param fileName
	 * 
	 * @param response
	 * 
	 *            mybatis返回的数据源的封装 例如:List<Map<String,Object>>
	 * @param dataset
	 * 
	 *            没一个sheet显示的标题
	 * @param headerss
	 * 
	 *            页脚显示的文字
	 * @param sheets
	 */
	@SuppressWarnings("rawtypes")
	public static void exportExcel(String fileName,
			HttpServletResponse response,
			Collection<List<Map<String, Object>>>[] dataset,
			String[][] headerss, String[] sheets) {
		try {
			OutputStream ouputStream = response.getOutputStream();
			/**
			 * 建立一个导出excel工程
			 */
			HSSFWorkbook workbook = new HSSFWorkbook();

			// 设置标题单元格样式 和 标题的字体
			HSSFCellStyle style = configCellStyle(workbook);
			// 设置内容单元格样式 和 内容的字体
			HSSFCellStyle style2 = configContentStyle(workbook);

			for (int sheetIndex = 0; sheetIndex < dataset.length; sheetIndex++) {
				// 指定页脚
				HSSFSheet sheet = workbook.createSheet(sheets[sheetIndex]);
				sheet.setDefaultColumnWidth(cloumn_with);
				System.out.println("");
				/**
				 * 开始生成每一个sheet 先完成标题部分
				 */
				System.out.println("开始画sheet" + (sheetIndex + 1));
				String[] titles = headerss[sheetIndex];
				// 产生表格标题行
				HSSFRow row = sheet.createRow(0);
				for (int cellIndex = 0; cellIndex < titles.length; cellIndex++) {
					HSSFCell cell = row.createCell(cellIndex);
					cell.setCellStyle(style);
					HSSFRichTextString text = new HSSFRichTextString(
							titles[cellIndex]);
					System.out.print(titles[cellIndex] + "\t");
					cell.setCellValue(text);
				}
				/**
				 * 标题制造完毕
				 */
				System.out.println("\r\n");
				List datasetList = (List) dataset[sheetIndex];
				Iterator<?> it = datasetList.iterator();
				int rowIndex = 0;

				while (it.hasNext()) {
					rowIndex++;
					row = sheet.createRow(rowIndex);
					Map mapInList = (Map) it.next();
					int cellXIndex = 0;
					for (Object o : mapInList.keySet()) {
						HSSFCell cellContext = row.createCell(cellXIndex);
						cellContext.setCellStyle(style2);
						cellXIndex++;
						System.out.print(mapInList.get(o) + "\t");
						if (mapInList.get(o) != null) {
							cellContext.setCellValue(mapInList.get(o)
									.toString());
						} else {
							cellContext.setCellValue("");
						}
					}
					System.out.println("\r\n");
				}
			}

			response.setCharacterEncoding("GBK");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename="
					+ new String(fileName.getBytes("GBK"), "ISO8859-1"));

			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 设置标题单元格的样式
	 * @user zhaojiyan
	 * @date 2013-8-7	
	 * @param workbook
	 * @return
	 */
	private static HSSFCellStyle configCellStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.WHITE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 设置标题单元格内容字体样式
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		// 把字体应用到当前的样式
		style.setFont(font);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平布局：居中
		style.setWrapText(true);
		return style;
	}
	/**
	 * 设置内容单元格的样式
	 * @user zhaojiyan
	 * @date 2013-8-7	
	 * @param workbook
	 * @return
	 */
	private static HSSFCellStyle configContentStyle(HSSFWorkbook workbook) {
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.WHITE.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		// 生成另一个字体
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font2.setFontName("宋体");
		font2.setFontHeightInPoints((short)11);
		// 把字体应用到当前的样式
		style2.setFont(font2);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平布局：居中
		style2.setWrapText(true);
		return style2;
	}
	
	
	/**
	 * 设置标题单元格的样式
	 * @user zhaojiyan
	 * @date 2013-8-7	
	 * @param workbook
	 * @return
	 */
	private static CellStyle configCellStyle2(SXSSFWorkbook workbook) {
		CellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(HSSFColor.WHITE.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		// 设置标题单元格内容字体样式
		Font font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		// 把字体应用到当前的样式
		style.setFont(font);
		style.setAlignment(CellStyle.ALIGN_CENTER); //水平布局：居中
		style.setWrapText(true);
		return style;
	}
	/**
	 * 设置内容单元格的样式
	 * @user zhaojiyan
	 * @date 2013-8-7	
	 * @param workbook
	 * @return
	 */
	private static CellStyle configContentStyle2(SXSSFWorkbook workbook) {
		CellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.WHITE.index);
		style2.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(CellStyle.BORDER_THIN);
		style2.setBorderLeft(CellStyle.BORDER_THIN);
		style2.setBorderRight(CellStyle.BORDER_THIN);
		style2.setBorderTop(CellStyle.BORDER_THIN);
		style2.setAlignment(CellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		// 生成另一个字体
		Font font2 = workbook.createFont();
		font2.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		font2.setFontName("宋体");
		font2.setFontHeightInPoints((short)11);
		// 把字体应用到当前的样式
		style2.setFont(font2);
		style2.setAlignment(CellStyle.ALIGN_CENTER); //水平布局：居中
		style2.setWrapText(true);
		return style2;
	}
	
	
	/**
	 * 设置禁止修改字体颜色为红色
	 * @user yanjiming
	 * @date 2014-12-25	
	 * @param workbook
	 * @return
	 */
	private static HSSFCellStyle configContentStyleRed(HSSFWorkbook workbook) {
		HSSFCellStyle style3 = workbook.createCellStyle();
		style3.setFillForegroundColor(HSSFColor.WHITE.index);
		style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		// 生成另一个字体
		HSSFFont font3 = workbook.createFont();
		font3.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font3.setFontName("宋体");
		font3.setColor(HSSFColor.RED.index);
		font3.setFontHeightInPoints((short)11);
		// 把字体应用到当前的样式
		style3.setFont(font3);
		style3.setAlignment(HSSFCellStyle.ALIGN_CENTER); //水平布局：居中
		style3.setWrapText(true);
		return style3;
	}	
	/**
	 * 导出富友划扣拆分
	 * @param fileName
	 * @param response
	 * @param dataset
	 * @param headerss
	 * @param keys
	 * @param sheets
	 */
	@SuppressWarnings("rawtypes")
	public static void exportFYHKExcel(String fileName,
			HttpServletResponse response, List<List> dataset,
			String[][] headerss, String[][] keys, String[] sheets) {
		try {
			OutputStream ouputStream = response.getOutputStream();
			/**
			 * 建立一个导出excel工程 
			 */
			HSSFWorkbook workbook = new HSSFWorkbook();

			// 设置标题单元格样式 和 标题的字体
			HSSFCellStyle style = configCellStyle(workbook);
			// 设置内容单元格样式 和 内容的字体
			HSSFCellStyle style2 = configContentStyle(workbook);
			BigDecimal splitMonry=new BigDecimal(500000);
			for (int sheetIndex = 0; sheetIndex < dataset.size(); sheetIndex++) {
				// 指定页脚
				HSSFSheet sheet = workbook.createSheet(sheets[sheetIndex]);
				sheet.setDefaultColumnWidth(cloumn_with);
				/**
				 * 开始生成每一个sheet 先完成标题部分
				 */
				String[] titles = headerss[0];
				// 产生表格标题行
				HSSFRow row = sheet.createRow(0);
				for (int cellIndex = 0; cellIndex < titles.length; cellIndex++) {
					HSSFCell cell = row.createCell(cellIndex);
					cell.setCellStyle(style);
					HSSFRichTextString text = new HSSFRichTextString(
							titles[cellIndex]);
					cell.setCellValue(text);
				}
				/**
				 * 标题制造完毕
				 */
				List datasetList = (List) dataset.get(sheetIndex);
				String[] keyss = keys[0];
				int rowIndex = 1;
				for (int i = 0; i < datasetList.size(); i++, rowIndex++) {
					Map dataMap = (Map) datasetList.get(i);
					BigDecimal money= new BigDecimal(dataMap.get(keyss[4]).toString());
					if(money.compareTo(splitMonry)>0){
						int splitCount=(int) Math.ceil(money.divide(splitMonry).doubleValue());
						for(int j=1;j<=splitCount;j++,rowIndex++){
							row = sheet.createRow(rowIndex);
							for (int k = 0; k < keyss.length; k++) {
								HSSFCell cellContext = row.createCell(k);
								cellContext.setCellStyle(style2);
								if(k==0){
									cellContext.setCellValue(rowIndex);
								}else if(k==4){
									if(splitCount==j){
										cellContext.setCellValue(money.subtract(splitMonry.multiply(new BigDecimal(j-1))).toString());
									}else{
										cellContext.setCellValue(splitMonry.toString());
									}
								}else{
									if (dataMap.get(keyss[k]) == null) {
										cellContext.setCellValue("");
									} else {
										if (dataMap.get(keyss[k]) instanceof Date) {
											SimpleDateFormat fmt = new SimpleDateFormat(
													"yyyy-MM-dd");
											cellContext.setCellValue(fmt.format(
													dataMap.get(keyss[k])).toString());
										}else {
											cellContext.setCellValue(dataMap.get(keyss[k])
													.toString());
										}
									}
								}
							}
						}
						money=money.subtract(splitMonry);
					}else{
						row = sheet.createRow(rowIndex);
						for (int k = 0; k < keyss.length; k++) {
							HSSFCell cellContext = row.createCell(k);
							cellContext.setCellStyle(style2);
							if(k==0){
								cellContext.setCellValue(rowIndex);
							}else{
								if (dataMap.get(keyss[k]) == null) {
									cellContext.setCellValue("");
								} else {
									if (dataMap.get(keyss[k]) instanceof Date) {
										SimpleDateFormat fmt = new SimpleDateFormat(
												"yyyy-MM-dd");
										cellContext.setCellValue(fmt.format(
												dataMap.get(keyss[k])).toString());
									}else {
										cellContext.setCellValue(dataMap.get(keyss[k])
												.toString());
									}
								}
								
							}

	
						}
					}
				}
			}
			response.setCharacterEncoding("GBK");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 导出好易联划扣拆分
	 * @param fileName
	 * @param response
	 * @param dataset
	 * @param headerss
	 * @param keys
	 * @param sheets
	 */
	@SuppressWarnings("rawtypes")
	public static void exportHYLHKExcel(String fileName,
			HttpServletResponse response, List<List> dataset,
			String[][] headerss, String[][] keys, String[] sheets,String[] strHeader,String[] strKeys) {
		try {
			OutputStream ouputStream = response.getOutputStream();
			/**
			 * 建立一个导出excel工程 
			 */
			HSSFWorkbook workbook = new HSSFWorkbook();
			// 设置标题单元格样式 和 标题的字体
			HSSFCellStyle style = configCellStyle(workbook);
			// 设置内容单元格样式 和 内容的字体
			HSSFCellStyle style2 = configContentStyle(workbook);
			BigDecimal splitMonry=new BigDecimal(500000);
			for (int sheetIndex = 0; sheetIndex < dataset.size(); sheetIndex++) {
				// 指定页脚
				HSSFSheet sheet = workbook.createSheet(sheets[sheetIndex]);
				sheet.setDefaultColumnWidth(cloumn_with);
				/**
				 * 开始生成每一个sheet 先完成标题部分
				 */
				String[] titles = headerss[0];
				
				String[] headerValue = strKeys;
				// 产生表格标题行
				HSSFRow rowOne = sheet.createRow(0);
				HSSFRow rowTwo = sheet.createRow(1);
				for (int cellIndex = 0; cellIndex < strHeader.length; cellIndex++) {
					HSSFCell cellone = rowOne.createCell(cellIndex);
					cellone.setCellStyle(style);
					HSSFRichTextString textOne = new HSSFRichTextString(strHeader[cellIndex]);
					cellone.setCellValue(textOne);
					HSSFCell celltwo = rowTwo.createCell(cellIndex);
					celltwo.setCellStyle(style2);
					HSSFRichTextString textTwo = new HSSFRichTextString(headerValue[cellIndex]);
					celltwo.setCellValue(textTwo);
				}
				HSSFRow row = sheet.createRow(2);
				
				for (int cellIndex = 0; cellIndex < titles.length; cellIndex++) {
					HSSFCell cell = row.createCell(cellIndex);
					cell.setCellStyle(style);
					HSSFRichTextString text = new HSSFRichTextString(
							titles[cellIndex]);
					cell.setCellValue(text);
				}
				/**
				 * 标题制造完毕
				 */
				List datasetList = (List) dataset.get(sheetIndex);
				String[] keyss = keys[0];
				int rowIndex = 3;
				int moneyIndex = 10;
				for (int i = 0; i < datasetList.size(); i++, rowIndex++) {
					Map dataMap = (Map) datasetList.get(i);
					BigDecimal money= new BigDecimal(dataMap.get(keyss[moneyIndex]).toString());
					if(money.compareTo(splitMonry)>0){
						int splitCount=(int) Math.ceil(money.divide(splitMonry).doubleValue());
						for(int j=1;j<=splitCount;j++,rowIndex++){
							row = sheet.createRow(rowIndex);
							for (int k = 0; k < keyss.length; k++) {
								HSSFCell cellContext = row.createCell(k);
								cellContext.setCellStyle(style2);
								if(k==0){
									cellContext.setCellValue(rowIndex-2);
								}else if(k==moneyIndex){
									if(splitCount==j){
										cellContext.setCellValue(money.subtract(splitMonry.multiply(new BigDecimal(j-1))).toString());
									}else{
										cellContext.setCellValue(splitMonry.toString());
									}
								}else{
									if (dataMap.get(keyss[k]) == null) {
										cellContext.setCellValue("");
									} else {
										if (dataMap.get(keyss[k]) instanceof Date) {
											SimpleDateFormat fmt = new SimpleDateFormat(
													"yyyy-MM-dd");
											cellContext.setCellValue(fmt.format(
													dataMap.get(keyss[k])).toString());
										}else {
											cellContext.setCellValue(dataMap.get(keyss[k])
													.toString());
										}
									}
								}
							}
						}
						money=money.subtract(splitMonry);
					}else{
						row = sheet.createRow(rowIndex);
						for (int k = 0; k < keyss.length; k++) {
							HSSFCell cellContext = row.createCell(k);
							cellContext.setCellStyle(style2);
							if(k==0){
								cellContext.setCellValue(rowIndex);
							}else{
								if (dataMap.get(keyss[k]) == null) {
									cellContext.setCellValue("");
								} else {
									if (dataMap.get(keyss[k]) instanceof Date) {
										SimpleDateFormat fmt = new SimpleDateFormat(
												"yyyy-MM-dd");
										cellContext.setCellValue(fmt.format(
												dataMap.get(keyss[k])).toString());
									}else {
										cellContext.setCellValue(dataMap.get(keyss[k])
												.toString());
									}
								}
								
							}
							
							
						}
					}
				}
			}
			response.setCharacterEncoding("GBK");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes("GBK"), "ISO8859-1"));
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
		/**
		 * 拆分list数组
		 * @param targe 查询的数据（即要拆的数组）
		 * @param size 每个工作薄的容量
		 * @return
		 */
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public static List<List>  createList(List targe,int size) {  
	        List<List> listArr = new ArrayList<List>();  
	        //获取被拆分的数组个数  
	        int arrSize = targe.size()%size==0?targe.size()/size:targe.size()/size+1;
	        for(int i=0;i<arrSize;i++) {   
	            List  sub = new ArrayList();  
	            //把指定索引数据放入到list中  
	            for(int j=i*size;j<=size*(i+1)-1;j++) {  
	                if(j<=targe.size()-1) {  
	                    sub.add(targe.get(j));  
	                }  
	            }  
	            listArr.add(sub);  
	        }  
	        return listArr;  
	    }
}

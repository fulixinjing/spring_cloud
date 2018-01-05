package cn.taskSys.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
  
/** 
 * Excel组件 
 *  
 * @author kangyu 
 * @version 1.0 
 * @since 1.0 
 */  
public abstract class ImportExcelUtil {  
  
    /** 
     * Excel 2003 
     */  
    private final static String XLS = "xls";  
    /** 
     * Excel 2007 
     */  
    private final static String XLSX = "xlsx";  
    /** 
     * 分隔符 
     */  
    private final static String SEPARATOR = "|";  
  
    /** 
     * 由Excel文件的Sheet导出至List 
     *  
     * @param file 
     * @param sheetNum 
     * @return 
     */  
    public static List<String> exportListFromExcel(File file, int sheetNum)  
            throws IOException {  
        return exportListFromExcel(new FileInputStream(file),  
                FilenameUtils.getExtension(file.getName()), sheetNum);  
    }  
  
    /** 
     * 由Excel流的Sheet导出至List 
     *  
     * @param is 
     * @param extensionName 
     * @param sheetNum 
     * @return 
     * @throws IOException 
     */  
    public static List<String> exportListFromExcel(InputStream is,  
            String extensionName, int sheetNum) throws IOException {  
  
        Workbook workbook = null;  
  
        if (extensionName.toLowerCase().equals(XLS)) {  
            workbook = new HSSFWorkbook(is);  
        } else if (extensionName.toLowerCase().equals(XLSX)) {  
            workbook = new XSSFWorkbook(is);  
        }  
  
        return exportListFromExcel(workbook, sheetNum);  
    }  
  
    /** 
     * 由指定的Sheet导出至List 
     *  
     * @param workbook 
     * @param sheetNum 
     * @return 
     * @throws IOException 
     */  
    private static List<String> exportListFromExcel(Workbook workbook,  
            int sheetNum) {  
  
        Sheet sheet = workbook.getSheetAt(sheetNum);  
  
        // 解析公式结果  
        FormulaEvaluator evaluator = workbook.getCreationHelper()  
                .createFormulaEvaluator();  
  
        List<String> list = new ArrayList<String>();  
  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        
        int minRowIx = sheet.getFirstRowNum();  
        int maxRowIx = sheet.getLastRowNum();  
        for (int rowIx = minRowIx; rowIx <= maxRowIx; rowIx++) {  
            Row row = sheet.getRow(rowIx);  
            StringBuilder sb = new StringBuilder();  
  
            short minColIx = row.getFirstCellNum();  
            short maxColIx = row.getLastCellNum();  
            for (short colIx = minColIx; colIx <= maxColIx; colIx++) {  
                Cell cell = row.getCell(new Integer(colIx));  
                CellValue cellValue = evaluator.evaluate(cell);  
                if (cellValue == null) {  
                	sb.append(SEPARATOR);  
                    continue;  
                }  
                System.out.println(cellValue.getCellType());
                // 经过公式解析，最后只存在Boolean、Numeric和String三种数据类型，此外就是Error了  
                // 其余数据类型，根据官方文档，完全可以忽略http://poi.apache.org/spreadsheet/eval.html  
                switch (cellValue.getCellType()) {  
                case Cell.CELL_TYPE_BOOLEAN:  
                    sb.append(SEPARATOR + cellValue.getBooleanValue());  
                    break;  
                case Cell.CELL_TYPE_NUMERIC:  
                    // 这里的日期类型会被转换为数字类型，需要判别后区分处理  
                    if (DateUtil.isCellDateFormatted(cell)) {  
                        sb.append(SEPARATOR + sdf.format(cell.getDateCellValue()));  
                    } else {  
                        sb.append(SEPARATOR + cellValue.getNumberValue());  
                    }  
                    break;  
                case Cell.CELL_TYPE_STRING:  
                    sb.append(SEPARATOR + cellValue.getStringValue());  
                    break;  
                case Cell.CELL_TYPE_FORMULA:  
                    break;  
                case Cell.CELL_TYPE_BLANK:  
//                	sb.append(SEPARATOR);  
                    break;  
                case Cell.CELL_TYPE_ERROR:  
                    break;  
                default:  
                    break;  
                }  
            }  
            sb.deleteCharAt(sb.length()-1);
            list.add(sb.toString());  
        }  
        return list;  
    }  
    
    
    /** 
     * 由Excel文件的Sheet导出至List (特殊：自定义起始行)
     *  
     * @param file 
     * @param sheetNum 
     * @return 
     */  
    public static List<String> exportListFromExcelCustom(File file, int sheetNum,int startNum)  
            throws IOException {  
        return exportListFromExcelCustom(new FileInputStream(file),  
                FilenameUtils.getExtension(file.getName()), sheetNum,startNum);  
    }  
  
    /** 
     * 由Excel流的Sheet导出至List (特殊：自定义起始行)
     *  
     * @param is 
     * @param extensionName 
     * @param sheetNum 
     * @return 
     * @throws IOException 
     */  
    public static List<String> exportListFromExcelCustom(InputStream is,  
            String extensionName, int sheetNum,int startNum) throws IOException {  
  
        Workbook workbook = null;  
  
        if (extensionName.toLowerCase().equals(XLS)) {  
            workbook = new HSSFWorkbook(is);  
        } else if (extensionName.toLowerCase().equals(XLSX)) {  
            workbook = new XSSFWorkbook(is);  
        }  
  
        return exportListFromExcelCustom(workbook, sheetNum, startNum);  
    }  
  
    /** 
     * 由指定的Sheet导出至List (特殊：自定义起始行)
     *  
     * @param workbook 
     * @param sheetNum 
     * @return 
     * @throws IOException 
     */  
    private static List<String> exportListFromExcelCustom(Workbook workbook,  
            int sheetNum, int startNum) {  
  
        Sheet sheet = workbook.getSheetAt(sheetNum);  
  
        // 解析公式结果  
        FormulaEvaluator evaluator = workbook.getCreationHelper()  
                .createFormulaEvaluator();  
  
        List<String> list = new ArrayList<String>();  
  
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        
//        int minRowIx = sheet.getFirstRowNum();  
        int minRowIx = startNum;
        int maxRowIx = sheet.getLastRowNum();  
        for (int rowIx = minRowIx; rowIx <= maxRowIx; rowIx++) {  
            Row row = sheet.getRow(rowIx);  
            StringBuilder sb = new StringBuilder();  
  
            short minColIx = row.getFirstCellNum();  
            short maxColIx = row.getLastCellNum();  
            for (short colIx = minColIx; colIx <= maxColIx; colIx++) {  
                Cell cell = row.getCell(new Integer(colIx));  
                CellValue cellValue = evaluator.evaluate(cell);  
                if (cellValue == null) {  
                	sb.append(SEPARATOR);  
                    continue;  
                }  
                System.out.println(cellValue.getCellType());
                // 经过公式解析，最后只存在Boolean、Numeric和String三种数据类型，此外就是Error了  
                // 其余数据类型，根据官方文档，完全可以忽略http://poi.apache.org/spreadsheet/eval.html  
                switch (cellValue.getCellType()) {  
                case Cell.CELL_TYPE_BOOLEAN:  
                    sb.append(SEPARATOR + cellValue.getBooleanValue());  
                    break;  
                case Cell.CELL_TYPE_NUMERIC:  
                    // 这里的日期类型会被转换为数字类型，需要判别后区分处理  
                    if (DateUtil.isCellDateFormatted(cell)) {  
                        sb.append(SEPARATOR + sdf.format(cell.getDateCellValue()));  
                    } else {  
                        sb.append(SEPARATOR + cellValue.getNumberValue());  
                    }  
                    break;  
                case Cell.CELL_TYPE_STRING:  
                    sb.append(SEPARATOR + cellValue.getStringValue());  
                    break;  
                case Cell.CELL_TYPE_FORMULA:  
                    break;  
                case Cell.CELL_TYPE_BLANK:  
//                	sb.append(SEPARATOR);  
                    break;  
                case Cell.CELL_TYPE_ERROR:  
                    break;  
                default:  
                    break;  
                }  
            }  
            sb.deleteCharAt(sb.length()-1);
            list.add(sb.toString());  
        }  
        return list;  
    }  
    
    public static void main(String[] args) {
    	String path = "D:/123.xlsx";  
        List<String> list = null;  
        try {  
            list = exportListFromExcel(new File(path), 0); 
            for(int i=0;i<list.size();i++){
            	System.out.println(list.get(i));
            }
        } catch (IOException e) { 
        	e.printStackTrace();
        }  
	}
    
} 

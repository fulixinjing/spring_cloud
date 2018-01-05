package cn.taskSys.utils;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.util.CellRangeAddress;


/**
 * excel 基类
 * @version 1.0
 */
@SuppressWarnings("deprecation")
public class ExcelTempleteUtil {

	 /**
     * 查找所有的合并单元格
     * @param oldrow
     * @return
     */
    @SuppressWarnings("rawtypes")
	public static Vector findRegion(HSSFSheet sheet, Cell cell) {
        Vector<Region> regs = new Vector<Region>();
        int num = sheet.getNumMergedRegions();
        int curRowid = cell.getRow().getRowNum();
        for (int i = 0; i < num; i++) {
            Region reg = sheet.getMergedRegionAt(i);
            if (reg.getRowFrom() == reg.getRowTo() && reg.getRowFrom() == curRowid) {
                regs.add(reg);
            }
        }
        return regs;
    }
    
    //判断该单元格是否是合并的
    public static boolean isMergedRegion(HSSFSheet sheet, HSSFCell cell) {
    	int sheetMergeCount = sheet.getNumMergedRegions();
    	int row = cell.getRow().getRowNum();
    	int column = cell.getColumnIndex();//列
    	for(int i=0; i<sheetMergeCount; i++) {
    		CellRangeAddress ca = sheet.getMergedRegion(i);
    		int firstColumn = ca.getFirstColumn();
    		int lastColumn = ca.getLastColumn();
    		int firstRow = ca.getFirstRow();//开始行
    		int lastRow = ca.getLastRow();//结束
    		if(row>=firstRow && row<= lastRow) {
    			if(column >= firstColumn && column <= lastColumn) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
	
	// 获得合并的值
    public static String getMergedRegionValue(HSSFSheet sheet, Cell cell) {
    	int sheetMergeCount = sheet.getNumMergedRegions();
    	int row = cell.getRowIndex();//行
    	int column = cell.getColumnIndex();//列
    	for(int i=0; i<sheetMergeCount; i++) {
    		CellRangeAddress ca = sheet.getMergedRegion(i);
    		int firstColumn = ca.getFirstColumn();
    		int lastColumn = ca.getLastColumn();
    		int firstRow = ca.getFirstRow();//开始行
    		int lastRow = ca.getLastRow();//结束
//    		System.out.println(firstRow+","+firstColumn+","+lastRow+","+lastColumn);
    		if(row>=firstRow && row<= lastRow) {
    			if(column >= firstColumn && column <= lastColumn) {
    				Cell reCell = sheet.getRow(firstRow).getCell(firstColumn);
    				String value = ConvertCellStr(reCell, reCell.getStringCellValue());
    				return value;
    			}
    		}
    	}
    	return null;
    }
	
	/**
	 * 把单元格内的类型转换至String类型
	 */
    public static String ConvertCellStr(Cell cell, String cellStr) {
		switch (cell.getCellType()) {

		case Cell.CELL_TYPE_STRING:
			// 读取String
			cellStr = cell.getStringCellValue().toString();
			break;

		case Cell.CELL_TYPE_BOOLEAN:
			// 得到Boolean对象的方法
			cellStr = String.valueOf(cell.getBooleanCellValue());
			break;

		case Cell.CELL_TYPE_NUMERIC:
			// 先看是否是日期格式
			if (DateUtil.isCellDateFormatted(cell)) {
				// 读取日期格式
				cellStr = cell.getDateCellValue().toString();
			} else {
				// 读取监督员编号
				cellStr = String.valueOf(((int)cell.getNumericCellValue()));
			}
			break;

		case Cell.CELL_TYPE_FORMULA:
			// 读取公式
			cellStr = cell.getCellFormula().toString();
			break;
		}
		return cellStr;
	}
    
    /**
     * 校验该单元格是否包含垃圾数据与字符
     * @param cellStr
     * @return
     */
    public static boolean isUnnormalCell(String cellStr) {
    	Pattern p = Pattern.compile("^[A-Za-z\\d-\\u4E00-\\u9FA5]+$");
		Matcher m = p.matcher(cellStr);
		boolean b = m.matches();
    	return b;
    }
    
    /**
	 * 校验是否是纯数字 这个写的不好 凑活用吧...
	 * @param j
	 * @param cellStr
	 * @return
	 */
	public static boolean checkCellNumber(int j, int column, String cellStr) {
		boolean re = true;
		if(j==column) {
			re = UtilValidate.isInteger(cellStr);
		}
		return re;
	}
	/**
	 * 合并单元格方法
	 * @param sheet 当前页
	 * @param firstRow 合并开始行
	 * @param lastRow 合并结束行
	 * @param firstCol 开始单元格
	 * @param lastCol 结束单元格
	 */
	public static HSSFSheet mergedRegion(HSSFSheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
		sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
		return sheet;
	}
	
    public static void main(String[] args) {
		String v = "你好";
		Pattern p = Pattern.compile("^[A-Za-z\\d-\\u4E00-\\u9FA5]+$");
		Matcher m = p.matcher(v);
		boolean b = m.matches();
		System.out.println(b);
    }
}

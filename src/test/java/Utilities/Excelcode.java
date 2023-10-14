package Utilities;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class Excelcode {

    public static XSSFWorkbook excel;
    public static File file;
    public static FileInputStream fi;
    public static FileOutputStream fo;
    public static XSSFSheet sheet;
    public static XSSFCell cell;

    public static void setCellData(int rownum, int cellnum, String data) throws IOException {

        String path = System.getProperty("user.dir") + "\\ExcelFiles\\Phone.xlsx";
        fi  = new FileInputStream(path);
        excel = new XSSFWorkbook(fi);
        sheet = excel.getSheet("Samsung");
        sheet.createRow(rownum);
        sheet.getRow(rownum).createCell(cellnum).setCellValue(data);
        fo = new FileOutputStream(path);
        excel.write(fo);
        fo.close();

    }
}

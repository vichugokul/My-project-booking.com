package utils;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.HashMap;

public class ExcelUtils {

    public HashMap<String,String> getData(String file,String sheetName,int flowNo,HashMap<String, String> data) throws IOException {
        String filePath = "src/test/resources/TestData/"+file+".xlsx";
        XSSFWorkbook wb = new XSSFWorkbook(filePath);
        Sheet sh = wb.getSheet(sheetName);
        int rowNo = sh.getRow(0).getLastCellNum();
        String key="";
        String value="";
        for(int i=0; i<rowNo; i++){
            key = sh.getRow(0).getCell(i).getStringCellValue();
            value = sh.getRow(flowNo).getCell(i).getStringCellValue();
            data.put(key,value);
            System.out.println(data.get(key));
        }
        return data;
    }
}

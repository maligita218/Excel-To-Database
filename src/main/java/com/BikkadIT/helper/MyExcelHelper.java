package com.BikkadIT.helper;

import com.BikkadIT.entity.Product;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyExcelHelper {

    //file is of excel type or not
    public static boolean checkExcelFormat(MultipartFile file) {
        String contentType = file.getContentType();

        if (contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            return true;
        } else {
            return false;
        }
    }

    //conver excel to list of product

    public static List<Product> convertExcelToListOfProduct(InputStream inputStream) {

        List<Product> list = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheet("data");

            int rowNumber = 0;
            Iterator<Row> iterator = sheet.iterator();

            while (iterator.hasNext()) {
                Row row = iterator.next();

                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }

                Iterator<Cell> cellIterator = row.iterator();
                int cid = 0;
                Product p = new Product();

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    switch (cid) {
                        case 0:
//                            if (cell == null || cell.getCellType() == CellType.BLANK) {
//                                Cell nextcell = row.getCell(1);
//                                if (nextcell != null && nextcell.getCellType() == CellType.NUMERIC) {
                            p.setProductId((int) cell.getNumericCellValue());
                            break;

                        case 1:
//                            if (cell == null || cell.getCellType() == CellType.BLANK) {
//                                Cell nextcell1 = row.getCell(1);
//                                if (nextcell1 != null && nextcell1.getCellType() == CellType.STRING) {
                            p.setProductName(cell.getStringCellValue());
                            break;

                        case 2:
//                            if (cell.getCellType() == CellType.STRING) {
//                                String cellValue = cell.getStringCellValue();
//                                boolean containslowercase = !cellValue.equals(cellValue.toUpperCase());
//                                if (containslowercase) {
//                                    String upperCaseValue = cellValue.toUpperCase();
                            p.setProductDesc(cell.getStringCellValue().toUpperCase());
                            break;

                        case 3:
//                            if (cell.getCellType() == CellType.NUMERIC) {
//                                double numericCellValue = cell.getNumericCellValue();
//                                if (numericCellValue <= 0 || Double.isNaN(numericCellValue)) {
//                                    double positiveValue = Math.abs(numericCellValue);
                            p.setProductPrice(cell.getNumericCellValue());
                            break;
                        default:
                            break;
                    }
                    cid++;

                }
                list.add(p);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

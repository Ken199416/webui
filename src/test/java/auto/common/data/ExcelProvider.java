package auto.common.data;

import auto.utils.CommonUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by haomingjian ,
 * Date ： 2019/11/6 13:39
 * Desc : 数据驱动
 */
public class ExcelProvider implements Iterator<Object[]> {
    private static Logger logger = LoggerFactory.getLogger(ExcelProvider.class);

    private int curRowNo = 0;
    int RowNum = 0;
    int cellNum = 0;
    //标题
    String [] titles = null;

    //用例路径
    File caseFile = null;
    Workbook workbook = null;
    Sheet sheet = null;

    /**
     *
     * @param envID
     * @param object
     * @param sheetID
     * @return 封装好的map对象
     */
    public ExcelProvider(String envID, Object object,int sheetID) {

        try {
//                caseFile = new File
//                        (new File("./").getCanonicalPath() + "//data//"+ envID + "//"+ object.getClass().getSimpleName() + ".xlsx");
                caseFile = new File(CommonUtils.getRootPath()+"//testdata//"+ envID + "//"+ object.getClass().getSimpleName() + ".xlsx");
                logger.info("数据源路径:{}",caseFile.getAbsolutePath());
                FileInputStream fileInputStream = new FileInputStream(caseFile);
                workbook =new XSSFWorkbook(fileInputStream);
                sheet = workbook.getSheetAt(sheetID);

//              获取总行数
                RowNum = sheet.getPhysicalNumberOfRows();
                //先获取第一行标题
                Row curRow = sheet.getRow(0);
                cellNum = curRow.getLastCellNum();
                titles = new String[cellNum];
                for (int i = 0; i < cellNum; i++) {

                    curRow.getCell(i).setCellType(CellType.STRING);
                    titles [i] = curRow.getCell(i).getStringCellValue();

                }

                curRowNo += 1;

            } catch (IOException e) {
                logger.info("Excel数据初始化失败");
                e.printStackTrace();
            }


    }

    public boolean hasNext() {
        if ((RowNum == 0) || (this.curRowNo >= RowNum)) {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }


    public Object[] next() {
        Row row = this.sheet.getRow(this.curRowNo);
        Map<String,String> mapData = new HashMap<String,String>();

        for (int i = 0; i < cellNum; i++) {
//                    考虑到Excel中的数据类型可能不统一，统一设置为String
            row.getCell(i).setCellType(CellType.STRING);
            mapData.put(titles [i],row.getCell(i).getStringCellValue());

        }
        this.curRowNo += 1;
        return new Object[] {mapData};
    }


}

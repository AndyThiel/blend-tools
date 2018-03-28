package info.blendformat.tools.sdna;

import info.blendformat.tools.sdna.model.SDNAFileContent;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;

public class ReportGenerator {

    public static final String SHEETNAME_OVERVIEW = "Overview";

    public void writeReport(OutputStream outputStream,
                            SDNAFileContent content) throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheetOverview = workbook.createSheet(WorkbookUtil
                .createSafeSheetName(SHEETNAME_OVERVIEW));

        XSSFRow rowHeader = sheetOverview.createRow(0);
        XSSFCell cellTopLeftCorder = rowHeader.createCell(0);
        


        workbook.write(outputStream);
    }
}

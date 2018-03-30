package info.blendformat.tools.sdna.reader;

import info.blendformat.tools.sdna.model.SDNABlockMetaData;
import info.blendformat.tools.sdna.model.SDNACatalog;
import info.blendformat.tools.sdna.model.SDNAFileContent;
import info.blendformat.tools.sdna.model.SDNAHeader;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AssertSDNAFileContent {

    public void assertFileContent(
            SDNAFileContent actualFileContent) {
        assertNotNull("A file content handle should have been read.",
                actualFileContent);
    }

    public void assertIdentifierEquals(
            String expectedIdentifier,
            SDNAFileContent actualFileContent) {

        SDNAHeader fileHeader = actualFileContent.getHeader();
        assertNotNull("An SDNA header should have been read.",
                fileHeader);
        assertEquals(
                expectedIdentifier,
                fileHeader.getIdentifier());
    }

    public void assertCatalogSize(
            String type,
            int expectedSize,
            SDNAFileContent actualFileContent) {

        SDNACatalog fileCatalog = actualFileContent.getCatalog();
        assertNotNull("A catalog should have been read.",
                fileCatalog);
        assertEquals(expectedSize, (int) fileCatalog.getSize(type));
    }

    public void assertDataBlocks(
            int expectedEntryCount,
            SDNAFileContent actualFileContent) {
        ArrayList<SDNABlockMetaData> metaDataList = actualFileContent.getMetaDataList();
        ArrayList<byte[]> dataList = actualFileContent.getDataList();

        assertEquals(expectedEntryCount, metaDataList.size());
        assertEquals(expectedEntryCount, dataList.size());
    }
}

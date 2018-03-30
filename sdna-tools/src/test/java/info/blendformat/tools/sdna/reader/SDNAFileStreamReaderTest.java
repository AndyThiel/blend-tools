package info.blendformat.tools.sdna.reader;

import info.blendformat.tools.sdna.model.SDNABlockMetaData;
import info.blendformat.tools.sdna.model.SDNAFileContent;
import info.blendformat.tools.sdna.reader.events.SDNAFileContentSubscriber;
import info.blendformat.tools.sdna.testdata.TestcaseFileMinimalCatalog;
import info.blendformat.tools.sdna.testdata.TestcaseFileMinimalCatalogBigEndian;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class SDNAFileStreamReaderTest {

    private SDNAFileStreamReader reader
            = new SDNAFileStreamReader();

    TestcaseFileMinimalCatalog testcaseFileMinimalCatalog
            = new TestcaseFileMinimalCatalog();
    TestcaseFileMinimalCatalogBigEndian testcaseFileMinimalCatalogBigEndian
            = new TestcaseFileMinimalCatalogBigEndian();

    @Test
    public void testFileStreamReaderLittleEndian() throws IOException {
        assertCatalogTestCase(new BufferedInputStream(
                testcaseFileMinimalCatalog.toInputStream()));
    }

    @Test
    public void testFileStreamReaderBigEndian() throws IOException {
        assertCatalogTestCase(new BufferedInputStream(
                testcaseFileMinimalCatalogBigEndian.toInputStream()));
    }

    private void assertCatalogTestCase(BufferedInputStream inputStream)
            throws IOException {

        ReaderConfig config = new ReaderConfigDefault();

        SDNAFileContentSubscriber subscriber = new SDNAFileContentSubscriber();
        reader.addSubscriber(subscriber);
        reader.readFile(config, inputStream);
        reader.removeSubscriber(subscriber);

        SDNAFileContent fileContent = subscriber.getFileContent();

        AssertSDNAFileContent assertSDNAFileContent
                = new AssertSDNAFileContent();
        assertSDNAFileContent.assertFileContent(fileContent);
        assertSDNAFileContent.assertIdentifierEquals(
                "CATALOG", fileContent);
        assertSDNAFileContent.assertCatalogSize(
                "char",
                1,
                fileContent);

        SDNABlockMetaData sdnaBlockMetaData = fileContent.getMetaDataList().get(0);
        assertEquals("TEST", sdnaBlockMetaData.getCode());
    }
}

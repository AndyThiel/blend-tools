package info.blendformat.tools.sdna;

import com.google.gson.JsonObject;
import info.blendformat.tools.sdna.model.SDNACatalog;
import info.blendformat.tools.sdna.model.SDNAFileContent;
import info.blendformat.tools.sdna.model.SDNAHeader;
import info.blendformat.tools.sdna.reader.ReaderConfigDefault;
import info.blendformat.tools.sdna.testdata.TestcaseFileMinimalBlend;
import info.blendformat.tools.sdna.testdata.TestcaseFileMinimalBlendBigEndian;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.*;

public class SDNAFileReaderTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SDNAFileReaderTest.class);

    private final TestcaseFileMinimalBlend testcaseMinimalBlend
            = new TestcaseFileMinimalBlend();
    private final TestcaseFileMinimalBlendBigEndian testcaseMinimalBlendBigEndian
            = new TestcaseFileMinimalBlendBigEndian();

    private SDNAFileReader fileReader = new SDNAFileReader();

    @Test
    public void testReadLittleEndian() throws IOException {

        LOGGER.debug("Executing testReadLittleEndian ...");

        ReaderConfigDefault config = new ReaderConfigDefault();
        config.setCatalogCode("N/A");
        SDNAFileContent fileContent = fileReader.read(
                config,
                testcaseMinimalBlend.toInputStream());

        assertTestcaseMinimalBlendContent(fileContent, true);
    }

    @Test
    public void testReadBigEndian() throws IOException {

        LOGGER.debug("Executing testReadBigEndian ...");

        ReaderConfigDefault config = new ReaderConfigDefault();
        config.setCatalogCode("N/A");
        SDNAFileContent fileContent = fileReader.read(
                config,
                testcaseMinimalBlendBigEndian.toInputStream());

        assertTestcaseMinimalBlendContent(fileContent, false);
    }

    private void assertTestcaseMinimalBlendContent(SDNAFileContent fileContent,
                                                   boolean littleEndian) {

        SDNAHeader header = fileContent.getHeader();
        assertNotNull("The file header is null", fileContent);

        assertEquals("BLENDER", header.getIdentifier());
        assertEquals((short) 8, (short) header.getPointerSize());
        if (littleEndian) {
            assertTrue(header.isLittleEndian());
        } else {
            assertFalse(header.isLittleEndian());
        }

        JsonObject extendedHeaderValues = header.getExtendedHeaderValues();
        String version = extendedHeaderValues.get("version").getAsString();
        assertEquals('2', version.charAt(0));
        assertEquals('7', version.charAt(1));
        assertEquals('9', version.charAt(2));

        SDNACatalog catalog = fileContent.getCatalog();
        assertNull("The SDNA catalog should only be read with code DNA1",
                catalog);
    }
}

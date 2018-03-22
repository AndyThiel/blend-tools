package info.blendformat.tools.sdna;

import info.blendformat.tools.sdna.model.SDNACatalog;
import info.blendformat.tools.sdna.model.SDNAFileContent;
import info.blendformat.tools.sdna.model.SDNAHeader;
import info.blendformat.tools.sdna.reader.ReaderConfig;
import info.blendformat.tools.sdna.testdata.TestcaseFileMinimalBlend;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class SDNAFileReaderTest {

    private final TestcaseFileMinimalBlend testcaseMinimalBlend
            = new TestcaseFileMinimalBlend();

    private SDNAFileReader fileReader = new SDNAFileReader();

    @Test
    public void testRead() throws IOException {

        SDNAFileContent fileContent = fileReader.read(
                new ReaderConfig(),
                testcaseMinimalBlend.toInputStream());

        assertNotNull("The file content is null", fileContent);

        SDNAHeader header = fileContent.getHeader();
        assertNotNull("The file header is null", fileContent);

        assertEquals("BLENDER", header.getIdentifier());
        assertEquals(8, header.getPointerSize());
        assertTrue(header.isLittleEndian());
        assertEquals(SDNAHeader.CODE_POINTERSIZE_8, header.getCodePointerSize());
        assertEquals(SDNAHeader.CODE_LITTLE_ENDIAN, header.getCodeEndianness());
        assertEquals('2', header.getCodeVersion()[0]);
        assertEquals('7', header.getCodeVersion()[1]);
        assertEquals('9', header.getCodeVersion()[2]);

        SDNACatalog catalog = fileContent.getCatalog();
        assertNotNull("The SDNA catalog is null", catalog);
    }
}

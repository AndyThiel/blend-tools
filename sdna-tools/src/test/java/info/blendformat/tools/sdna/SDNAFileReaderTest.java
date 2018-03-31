package info.blendformat.tools.sdna;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import info.blendformat.tools.sdna.defaults.DefaultCatalogPrimitives;
import info.blendformat.tools.sdna.model.*;
import info.blendformat.tools.sdna.reader.ReaderConfig;
import info.blendformat.tools.sdna.reader.ReaderConfigDefault;
import info.blendformat.tools.sdna.testdata.TestcaseFileMinimalBlend;
import info.blendformat.tools.sdna.testdata.TestcaseFileMinimalBlendBigEndian;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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

        ByteArrayInputStream inputStream = testcaseMinimalBlend.toInputStream();
        SDNAFileContent fileContent = fileReader.read(
                config,
                inputStream);
        inputStream.close();

        assertTestcaseMinimalBlendContent(fileContent, true);
    }

    @Test
    public void testReadBigEndian() throws IOException {

        LOGGER.debug("Executing testReadBigEndian ...");

        ReaderConfigDefault config = new ReaderConfigDefault();
        config.setCatalogCode("N/A");
        ByteArrayInputStream inputStream = testcaseMinimalBlendBigEndian.toInputStream();
        SDNAFileContent fileContent = fileReader.read(
                config,
                inputStream);
        inputStream.close();

        assertTestcaseMinimalBlendContent(fileContent, false);
    }

    @Test
    public void testActualFileStream() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(
                "info/blendformat/tools/sdna/scene278.blend");
        if (null == inputStream) {
            throw new AssertionError();
        }

        ArrayList<SDNAFieldDescriptor> fieldDescriptors = new ArrayList<>();
        SDNAFieldDescriptor versionDescriptor = new SDNAFieldDescriptor();
        versionDescriptor.setFieldType(FieldType.ARRAY);
        versionDescriptor.setStructType(DefaultCatalogPrimitives.PRIMITIVE_CHAR);
        versionDescriptor.setCode("version[3]");
        fieldDescriptors.add(versionDescriptor);

        SDNAStructDescriptor extendedHeaderDescriptor = new SDNAStructDescriptor();
        extendedHeaderDescriptor.setType("H++");
        extendedHeaderDescriptor.setSize(3);
        extendedHeaderDescriptor.setFieldDescriptors(fieldDescriptors);

        ReaderConfig config = new ReaderConfigDefault();
        config.setExtendedHeaderDescriptor(extendedHeaderDescriptor);

        SDNAFileContent fileContent = fileReader.read(config, inputStream);
        inputStream.close();

        assertTestcaseBlenderVersion278(fileContent, true);
    }

    private void assertTestcaseMinimalBlendContent(SDNAFileContent fileContent,
                                                   boolean littleEndian) {

        SDNAHeader header = fileContent.getHeader();
        assertNotNull("The file header should have been read.", fileContent);

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

    private void assertTestcaseBlenderVersion278(SDNAFileContent fileContent,
                                                 boolean littleEndian) {

        SDNAHeader header = fileContent.getHeader();
        assertNotNull("The file header should have been read", fileContent);

        assertEquals("BLENDER", header.getIdentifier());
        assertEquals((short) 8, (short) header.getPointerSize());
        if (littleEndian) {
            assertTrue(header.isLittleEndian());
        } else {
            assertFalse(header.isLittleEndian());
        }

        JsonObject extendedHeaderValues = header.getExtendedHeaderValues();
        LOGGER.info("Extended Header Values: "+extendedHeaderValues);
        JsonArray version = extendedHeaderValues.get("version").getAsJsonArray();
        assertEquals('2', version.get(0).getAsCharacter());
        assertEquals('7', version.get(1).getAsCharacter());
        assertEquals('8', version.get(2).getAsCharacter());

        SDNACatalog catalog = fileContent.getCatalog();
        assertNotNull("The SDNA catalog should have been read.",
                catalog);
    }
}

package info.blendformat.tools.sdna.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import info.blendformat.tools.sdna.defaults.DefaultCatalogPrimitives;
import info.blendformat.tools.sdna.model.FieldType;
import info.blendformat.tools.sdna.model.SDNACatalog;
import info.blendformat.tools.sdna.model.SDNAFieldDescriptor;
import info.blendformat.tools.sdna.model.SDNAStructDescriptor;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class StructParserTest {

    private static final short POINTERSIZE_8 = 8;

    private StructParser structParser = new StructParser();

    @Test
    public void test() {

        SDNACatalog catalog = new DefaultCatalogPrimitives();

        ArrayList<SDNAFieldDescriptor> fieldDescriptors = new ArrayList<>();
        SDNAFieldDescriptor fieldCode = new SDNAFieldDescriptor();
        fieldCode.setFieldType(FieldType.STRING);
        fieldCode.setStructType(DefaultCatalogPrimitives.PRIMITIVE_CHAR);
        fieldCode.setCode("code[4]");
        fieldDescriptors.add(fieldCode);
        SDNAFieldDescriptor fieldPointerSize = new SDNAFieldDescriptor();
        fieldPointerSize.setFieldType(FieldType.CHAR);
        fieldPointerSize.setStructType(DefaultCatalogPrimitives.PRIMITIVE_CHAR);
        fieldPointerSize.setCode("pointerSize");
        fieldDescriptors.add(fieldPointerSize);
        SDNAFieldDescriptor fieldEndianness = new SDNAFieldDescriptor();
        fieldEndianness.setFieldType(FieldType.CHAR);
        fieldEndianness.setStructType(DefaultCatalogPrimitives.PRIMITIVE_CHAR);
        fieldEndianness.setCode("endianness");
        fieldDescriptors.add(fieldEndianness);
        SDNAFieldDescriptor fieldVersion = new SDNAFieldDescriptor();
        fieldVersion.setFieldType(FieldType.ARRAY);
        fieldVersion.setStructType(DefaultCatalogPrimitives.PRIMITIVE_CHAR);
        fieldVersion.setCode("version[3]");
        fieldDescriptors.add(fieldVersion);

        SDNAStructDescriptor descriptor = new SDNAStructDescriptor();
        descriptor.setType("TEST");
        descriptor.setFieldDescriptors(fieldDescriptors);
        descriptor.calculateSize(catalog, POINTERSIZE_8);
        assertEquals(9, (int) descriptor.getSize());

        String previousStructType = catalog.registerStruct(0,
                "testStruct",
                descriptor);
        assertNull("There should be no previous value in the catalog, but there is: "
                + previousStructType, previousStructType);

        JsonObject result = structParser.parseStruct(
                POINTERSIZE_8,
                catalog,
                descriptor,
                new byte[]{'B', 'L', 'E', 'N', '-', 'v', '0', '1', '0'},
                true);
        assertEquals("BLEN", result.get("code").getAsString());
        assertEquals('-', result.get("pointerSize").getAsCharacter());
        assertEquals('v', result.get("endianness").getAsCharacter());
        JsonArray version = result.get("version").getAsJsonArray();
        assertEquals('0', version.get(0).getAsCharacter());
        assertEquals('1', version.get(1).getAsCharacter());
        assertEquals('0', version.get(2).getAsCharacter());
    }
}

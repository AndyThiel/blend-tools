package info.blendformat.tools.sdna.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import info.blendformat.tools.sdna.defaults.DefaultCatalogPrimitives;
import info.blendformat.tools.sdna.model.FieldType;
import info.blendformat.tools.sdna.model.SDNAFieldDescriptor;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.junit.Assert.assertEquals;

public class FieldParserTest {

    private static final short POINTERSIZE_8 = 8;

    private StructParser structParser = new StructParser();
    private FieldParser fieldParser = new FieldParser(structParser);

    @Test
    public void testPrimitiveNumberFieldFlow() {
        String primitiveInt64T = DefaultCatalogPrimitives.PRIMITIVE_INT64_T;
        String primitiveLong = DefaultCatalogPrimitives.PRIMITIVE_LONG;

        DefaultCatalogPrimitives catalogPrimitives = new DefaultCatalogPrimitives();
        Integer sizeInt64T = catalogPrimitives.getSize(primitiveInt64T);
        Integer sizeLong = catalogPrimitives.getSize(primitiveLong);

        Number resultInt64T = fieldParser.readFieldValueAsNumber(primitiveInt64T,
                sizeInt64T,
                ByteBuffer.allocate(sizeInt64T)
                        .order(ByteOrder.LITTLE_ENDIAN).putLong(2000000000).array(),
                true);
        Number resultLong = fieldParser.readFieldValueAsNumber(primitiveLong,
                sizeLong,
                ByteBuffer.allocate(sizeLong)
                        .order(ByteOrder.LITTLE_ENDIAN).putInt(2000000).array(),
                true);

        assertEquals((long) 2000000000, (long) resultInt64T);
        assertEquals(2000000, (int) resultLong);
    }

    @Test
    public void testReadFieldValueAsString() {
        String result = fieldParser.readFieldValueAsString(
                new byte[]{'B', 'L', 'E', 'N', 'D', 'E', 'R'}
        );
        assertEquals("BLENDER", result);
    }

    @Test
    public void testReadFieldValueAsNumber() {
        ByteBuffer littleEndianBuffer = ByteBuffer.allocate(4)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putInt(15);
        ByteBuffer bigEndianBuffer = ByteBuffer.allocate(4)
                .order(ByteOrder.BIG_ENDIAN)
                .putInt(15);
        int littleEndianResult = (int) fieldParser.readFieldValueAsNumber(
                "int",
                4,
                littleEndianBuffer.array(),
                true);
        int bigEndianResult = (int) fieldParser.readFieldValueAsNumber(
                "int",
                4,
                bigEndianBuffer.array(),
                false);
        assertEquals(15, littleEndianResult);
        assertEquals(15, bigEndianResult);
    }

    @Test
    public void testReadFieldValueAsChar() {
        char result = fieldParser.readFieldValueAsChar(
                new byte[]{'B'});
        assertEquals('B', result);
    }

    @Test
    public void testReadFieldValueAsArray() {
        ByteBuffer byteBufferLittleEndian = ByteBuffer.allocate(12)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putFloat(0.0f)
                .putFloat(0.0f)
                .putFloat(0.0f);
        ByteBuffer byteBufferBigEndian = ByteBuffer.allocate(12)
                .order(ByteOrder.BIG_ENDIAN)
                .putFloat(0.0f)
                .putFloat(0.0f)
                .putFloat(0.0f);

        JsonArray resultLittleEndian = fieldParser.readArray(
                new DefaultCatalogPrimitives(),
                FieldType.NUMBER,
                "float",
                3,
                byteBufferLittleEndian.array(),
                POINTERSIZE_8,
                true);
        JsonArray resultBigEndian = fieldParser.readArray(
                new DefaultCatalogPrimitives(),
                FieldType.NUMBER,
                "float",
                3,
                byteBufferBigEndian.array(),
                POINTERSIZE_8,
                false);

        assertEquals(0.0f, resultLittleEndian.get(0).getAsFloat(), 0.002);
        assertEquals(0.0f, resultLittleEndian.get(1).getAsFloat(), 0.002);
        assertEquals(0.0f, resultLittleEndian.get(2).getAsFloat(), 0.002);

        assertEquals(0.0f, resultBigEndian.get(0).getAsFloat(), 0.002);
        assertEquals(0.0f, resultBigEndian.get(1).getAsFloat(), 0.002);
        assertEquals(0.0f, resultBigEndian.get(2).getAsFloat(), 0.002);

        byte[] byteTest = new byte[]{'B', 'L', 'E'};

        JsonArray resultChar = fieldParser.readArray(
                new DefaultCatalogPrimitives(),
                FieldType.CHAR,
                "char",
                3,
                byteTest,
                POINTERSIZE_8,
                true);

        assertEquals('B', resultChar.get(0).getAsCharacter());
        assertEquals('L', resultChar.get(1).getAsCharacter());
        assertEquals('E', resultChar.get(2).getAsCharacter());
    }

    @Test
    public void testRenderReference() {

        SDNAFieldDescriptor fieldDescriptor = new SDNAFieldDescriptor();
        fieldDescriptor.setCode("*mesh");
        fieldDescriptor.setFieldType(FieldType.POINTER);
        fieldDescriptor.setStructType("quad");

        JsonObject reference = fieldParser.renderReference(fieldDescriptor,
                48,
                2000000000,
                "mesh");

        assertEquals("mesh", reference.get("referenceName").getAsString());
        assertEquals(2000000000, reference.get("address").getAsLong());

        assertEquals("quad", reference.get("structType").getAsString());
        assertEquals(48, reference.get("structTypeSize").getAsInt());
        assertEquals("*mesh", reference.get("code").getAsString());
    }
}

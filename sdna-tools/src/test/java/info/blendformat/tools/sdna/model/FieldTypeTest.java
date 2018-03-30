package info.blendformat.tools.sdna.model;

import junit.framework.AssertionFailedError;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class FieldTypeTest {

    private static final short POINTERSIZE_8 = 8;
    private static final short POINTERSIZE_4 = 4;

    @Test
    public void testGetArraySizeType() {
        String fieldCode = "identifier[7]";
        Integer arraySize = FieldType.getArraySize(fieldCode);
        assertEquals(7, (int) arraySize);

        fieldCode = "name[24]";
        arraySize = FieldType.getArraySize(fieldCode);
        assertEquals(24, (int) arraySize);

        fieldCode = "verts[1024]";
        arraySize = FieldType.getArraySize(fieldCode);
        assertEquals(1024, (int) arraySize);
    }

    @Test
    public void testGetByFieldMetaData() {
        FieldType type = FieldType.getByFieldMetaData(
                "char",
                1,
                "pointerSize");
        assertEquals(FieldType.CHAR, type);

        type = FieldType.getByFieldMetaData(
                "char",
                1,
                "name[64]");
        assertEquals(FieldType.STRING, type);

        type = FieldType.getByFieldMetaData(
                "vertex",
                12,
                "xPos");
        assertEquals(FieldType.STRUCT, type);

        type = FieldType.getByFieldMetaData(
                "vertex",
                12,
                "quad[4]");
        assertEquals(FieldType.ARRAY, type);
    }

    @Test
    public void testFieldTypeAddress() {
        Integer size8 = FieldType.ADDRESS.getSize(null,
                POINTERSIZE_8,
                0,
                "address8");
        Integer size4 = FieldType.ADDRESS.getSize(null,
                POINTERSIZE_4,
                0,
                "address4");
        assertEquals((int) POINTERSIZE_8, (int) size8);
        assertEquals((int) POINTERSIZE_4, (int) size4);

        String fieldName8 = FieldType.ADDRESS.getFieldName(null,
                POINTERSIZE_8,
                0,
                "address8");
        String fieldName4 = FieldType.ADDRESS.getFieldName(null,
                POINTERSIZE_8,
                0,
                "address4");
        assertEquals("address8", fieldName8);
        assertEquals("address4", fieldName4);

        try {
            FieldType.getArraySize("address8");
            throw new AssertionFailedError();
        } catch (IllegalArgumentException e) {
            assertEquals("No array code: address8", e.getMessage());
        }
    }

    @Test
    public void testFieldTypeNumber() {
        FieldType type = FieldType.getByFieldMetaData(
                "int",
                4,
                "number");
        assertEquals(FieldType.NUMBER, type);
        type = FieldType.getByFieldMetaData(
                "long",
                4,
                "number");
        assertEquals(FieldType.NUMBER, type);
        type = FieldType.getByFieldMetaData(
                "uint64_t",
                4,
                "number");
        assertEquals(FieldType.NUMBER, type);
        type = FieldType.getByFieldMetaData(
                "float",
                4,
                "number");
        assertEquals(FieldType.NUMBER, type);
        type = FieldType.getByFieldMetaData(
                "double",
                8,
                "number");
        assertEquals(FieldType.NUMBER, type);

        Integer size8 = FieldType.NUMBER.getSize(null,
                POINTERSIZE_4,
                8,
                "number8");
        Integer size4 = FieldType.NUMBER.getSize(null,
                POINTERSIZE_8,
                4,
                "number4");
        assertEquals(8, (int) size8);
        assertEquals(4, (int) size4);

        String fieldName8 = FieldType.ADDRESS.getFieldName(null,
                POINTERSIZE_4,
                8,
                "number8");
        String fieldName4 = FieldType.ADDRESS.getFieldName(null,
                POINTERSIZE_8,
                4,
                "number4");
        assertEquals("number8", fieldName8);
        assertEquals("number4", fieldName4);

        try {
            FieldType.getArraySize("number8");
            throw new AssertionFailedError();
        } catch (IllegalArgumentException e) {
            assertEquals("No array code: number8", e.getMessage());
        }
    }

    @Test
    public void testFieldTypeChar() {
        FieldType type = FieldType.getByFieldMetaData(
                "char",
                1,
                "endianness");
        assertEquals(FieldType.CHAR, type);

        Integer size8 = FieldType.CHAR.getSize(null,
                POINTERSIZE_8,
                1,
                "endianness8");
        Integer size4 = FieldType.CHAR.getSize(null,
                POINTERSIZE_4,
                1,
                "endianness4");
        assertEquals(1, (int) size8);
        assertEquals(1, (int) size4);

        String fieldName8 = FieldType.ADDRESS.getFieldName(null,
                POINTERSIZE_8,
                1,
                "endianness8");
        String fieldName4 = FieldType.ADDRESS.getFieldName(null,
                POINTERSIZE_4,
                1,
                "endianness4");
        assertEquals("endianness8", fieldName8);
        assertEquals("endianness4", fieldName4);

        try {
            FieldType.getArraySize("endianness8");
            throw new AssertionFailedError();
        } catch (IllegalArgumentException e) {
            assertEquals("No array code: endianness8", e.getMessage());
        }
    }

    @Test
    public void testFieldTypeString() {
        FieldType type = FieldType.getByFieldMetaData(
                "char",
                1,
                "identifier[7]");
        assertEquals(FieldType.STRING, type);

        Integer size8 = FieldType.STRING.getSize(null,
                POINTERSIZE_8,
                1,
                "identifier8[7]");
        Integer size4 = FieldType.STRING.getSize(null,
                POINTERSIZE_4,
                1,
                "identifier4[7]");
        assertEquals(7, (int) size8);
        assertEquals(7, (int) size4);

        String fieldName8 = FieldType.STRING.getFieldName(null,
                POINTERSIZE_8,
                1,
                "identifier8[7]");
        String fieldName4 = FieldType.STRING.getFieldName(null,
                POINTERSIZE_4,
                1,
                "identifier4[7]");
        assertEquals("identifier8", fieldName8);
        assertEquals("identifier4", fieldName4);

        assertEquals(7, (int) FieldType.getArraySize("identifier8[7]"));
    }

    @Test
    public void testFieldTypeArray() {
        FieldType type = FieldType.getByFieldMetaData(
                "float",
                4,
                "vertex[3]");
        assertEquals(FieldType.ARRAY, type);
        type = FieldType.getByFieldMetaData(
                "vertex",
                12,
                "quad[4]");
        assertEquals(FieldType.ARRAY, type);

        Integer size8 = type.getSize(null,
                POINTERSIZE_8,
                4,
                "vertex8[3]");
        Integer size4 = type.getSize(null,
                POINTERSIZE_4,
                4,
                "vertex4[3]");
        assertEquals(12, (int) size8);
        assertEquals(12, (int) size4);

        String fieldName8 = type.getFieldName(null,
                POINTERSIZE_8,
                8,
                "vertex8[3]");
        String fieldName4 = type.getFieldName(null,
                POINTERSIZE_8,
                4,
                "vertex4[3]");
        assertEquals("vertex8", fieldName8);
        assertEquals("vertex4", fieldName4);

        assertEquals(3, (int) FieldType.getArraySize("vertex8[3]"));
    }

    @Test
    public void testFieldTypeFunctionPointer() {
        FieldType type = FieldType.getByFieldMetaData(
                "void",
                0,
                "(*testStruct)()");
        assertEquals(FieldType.FUNCTIONPOINTER, type);

        Integer size8 = type.getSize(null,
                POINTERSIZE_8,
                0,
                "(*testStruct8)()");
        Integer size4 = type.getSize(null,
                POINTERSIZE_4,
                0,
                "(*testStruct4)()");
        assertEquals((int) POINTERSIZE_8, (int) size8);
        assertEquals((int) POINTERSIZE_4, (int) size4);

        String fieldName8 = type.getFieldName(null,
                POINTERSIZE_8,
                0,
                "(*testStruct8)()");
        String fieldName4 = type.getFieldName(null,
                POINTERSIZE_8,
                0,
                "(*testStruct4)()");
        assertEquals("testStruct8", fieldName8);
        assertEquals("testStruct4", fieldName4);

        try {
            FieldType.getArraySize("(*testStruct)()");
            throw new AssertionFailedError();
        } catch (IllegalArgumentException e) {
            assertEquals("No array code: (*testStruct)()", e.getMessage());
        }
    }

    @Test
    public void testFieldTypePointer() {
        FieldType type = FieldType.getByFieldMetaData(
                "char",
                1,
                "*testStruct");
        assertEquals(FieldType.POINTER, type);

        SDNAStructDescriptor test = new SDNAStructDescriptor();
        test.setType("TEST");
        test.setSize(12);
        test.setFieldDescriptors(new ArrayList<>());

        Integer size8 = type.getSize(test,
                POINTERSIZE_8,
                test.getSize(),
                "*testStruct8");
        Integer size4 = type.getSize(test,
                POINTERSIZE_4,
                test.getSize(),
                "*testStruct4");
        assertEquals((int) POINTERSIZE_8, (int) size8);
        assertEquals((int) POINTERSIZE_4, (int) size4);

        String fieldName8 = type.getFieldName(test,
                POINTERSIZE_8,
                test.getSize(),
                "*testStruct8");
        String fieldName4 = type.getFieldName(test,
                POINTERSIZE_4,
                test.getSize(),
                "*testStruct4");
        assertEquals("testStruct8", fieldName8);
        assertEquals("testStruct4", fieldName4);

        try {
            FieldType.getArraySize("*testStruct");
            throw new AssertionFailedError();
        } catch (IllegalArgumentException e) {
            assertEquals("No array code: *testStruct", e.getMessage());
        }
    }
}

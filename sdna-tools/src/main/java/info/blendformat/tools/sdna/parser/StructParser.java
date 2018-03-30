package info.blendformat.tools.sdna.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import info.blendformat.tools.sdna.defaults.DefaultCatalogPrimitives;
import info.blendformat.tools.sdna.model.FieldType;
import info.blendformat.tools.sdna.model.SDNACatalog;
import info.blendformat.tools.sdna.model.SDNAFieldDescriptor;
import info.blendformat.tools.sdna.model.SDNAStructDescriptor;

import java.text.MessageFormat;
import java.util.Arrays;

public class StructParser {

    public static final String PROPERTYKEY_STRUCTTYPE = "structType";
    public static final String PROPERTYKEY_STRUCTSIZE = "structSize";

    private final FieldParser fieldParser = new FieldParser(this);

    public JsonObject parseStruct(short pointerSize,
                                  SDNACatalog catalog,
                                  SDNAStructDescriptor structDescriptor,
                                  byte[] buffer,
                                  boolean littleEndian)
            throws IllegalArgumentException {

        JsonObject result = new JsonObject();
        JsonArray nullReferences = new JsonArray();
        JsonArray references = new JsonArray();

        int bufferlength = buffer.length;
        int bufferLengthExpected = structDescriptor.getSize();
        if (bufferlength != bufferLengthExpected) {
            throw new IllegalArgumentException(MessageFormat.format(
                    "Buffer length: {0}, expected: {1}",
                    bufferlength,
                    bufferLengthExpected));
        }

        result.addProperty(PROPERTYKEY_STRUCTTYPE, structDescriptor.getType());
        result.addProperty(PROPERTYKEY_STRUCTSIZE, structDescriptor.getSize());

        int currentOffset = 0;
        byte[] fieldValueBuffer;
        for (SDNAFieldDescriptor fieldDescriptor : structDescriptor
                .getFieldDescriptors()) {

            FieldType fieldType = fieldDescriptor.getFieldType();
            String structType = fieldDescriptor.getStructType();
            String fieldCode = fieldDescriptor.getCode();

            SDNAStructDescriptor fieldStruct = catalog.getStructDescriptor(structType);
            int fieldStructSize = catalog.getSize(structType);
            String fieldName = fieldType.getFieldName(fieldStruct,
                    pointerSize,
                    fieldStructSize,
                    fieldCode);
            Integer fieldSize = fieldType.getSize(fieldStruct,
                    pointerSize,
                    fieldStructSize,
                    fieldCode);

            fieldValueBuffer = Arrays.copyOfRange(
                    buffer,
                    currentOffset,
                    (currentOffset + fieldSize));

            switch (fieldType) {
                case CHAR:
                    result.addProperty(
                            fieldName,
                            fieldParser.readFieldValueAsChar(
                                    fieldValueBuffer));
                    break;
                case STRING:
                    result.addProperty(
                            fieldName,
                            fieldParser.readFieldValueAsString(
                                    fieldValueBuffer));
                    break;
                case NUMBER:
                case ADDRESS:
                    result.addProperty(
                            fieldName,
                            fieldParser.readFieldValueAsNumber(
                                    structType,
                                    fieldSize,
                                    fieldValueBuffer,
                                    littleEndian));
                    break;
                case STRUCT:
                    result.add(
                            fieldName,
                            parseStruct(pointerSize,
                                    catalog,
                                    fieldStruct,
                                    buffer,
                                    littleEndian));
                    break;
                case ARRAY:
                    result.add(fieldName,
                            fieldParser.readArray(
                                    catalog,
                                    FieldType.getByFieldMetaData(
                                            structType,
                                            fieldStructSize,
                                            fieldName),
                                    structType,
                                    FieldType.getArraySize(fieldCode),
                                    fieldValueBuffer,
                                    pointerSize,
                                    littleEndian));
                    break;
                case POINTER:
                case FUNCTIONPOINTER:

                    Number pointerAddress = fieldParser.readFieldValueAsNumber(
                            DefaultCatalogPrimitives.PRIMITIVE_UINT64_T,
                            8,
                            fieldValueBuffer,
                            littleEndian);
                    JsonObject fieldValue = fieldParser.renderReference(
                            fieldDescriptor,
                            fieldStructSize,
                            pointerAddress,
                            fieldName);

                    if (pointerAddress.equals(0)) {
                        nullReferences.add(fieldValue);
                    } else {
                        references.add(fieldValue);
                    }

                    break;
            }

            currentOffset += fieldSize;
        }

        result.add("nullReferences", nullReferences);
        result.add("references", references);

        return result;
    }
}

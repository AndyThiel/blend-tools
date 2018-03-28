package info.blendformat.tools.sdna.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import info.blendformat.tools.sdna.defaults.DefaultCatalogPrimitives;
import info.blendformat.tools.sdna.model.FieldType;
import info.blendformat.tools.sdna.model.SDNACatalog;
import info.blendformat.tools.sdna.model.SDNAFieldDescriptor;
import info.blendformat.tools.sdna.model.SDNAStructDescriptor;

import java.util.Arrays;

public class FieldParser {

    private NumberValueParser numberValueParser = new NumberValueParser();
    private StringValueParser stringValueParser = new StringValueParser();
    private StructParser structParser;

    public FieldParser(StructParser structParser) {
        this.structParser = structParser;
    }

    public JsonArray readFieldStructsAsArray(short pointerSize,
                                             SDNACatalog catalog,
                                             FieldType fieldType,
                                             String structType,
                                             String code,
                                             int amount,
                                             byte[] fieldValueBuffer,
                                             boolean littleEndian) {

        JsonArray result = new JsonArray();
        Integer structTypeSize = catalog.getSize(structType);

        SDNAStructDescriptor structDescriptor
                = catalog.getStructDescriptor(structType);

        for (int index = 0; index < amount; index++) {

            int currentOffset = (index * structTypeSize);

            byte[] valueBuffer = Arrays.copyOfRange(
                    fieldValueBuffer,
                    currentOffset,
                    (currentOffset + structTypeSize));

            switch (fieldType) {
                case ADDRESS:
                case NUMBER:
                    result.add(readFieldValueAsNumber(
                            structType,
                            structTypeSize,
                            valueBuffer,
                            littleEndian));
                    break;
                case CHAR:
                    result.add(readFieldValueAsChar(
                            valueBuffer));
                    break;
                case STRING:
                    result.add(readFieldValueAsString(
                            valueBuffer));
                    break;
                case STRUCT:
                    result.add(structParser.parseStruct(
                            pointerSize,
                            catalog,
                            structDescriptor,
                            valueBuffer,
                            littleEndian));
                    break;
                case ARRAY:
                    result.add(readFieldStructsAsArray(
                            pointerSize,
                            catalog,
                            FieldType.getByFieldMetaData(
                                    structType,
                                    structTypeSize,
                                    code),
                            structType,
                            code,
                            FieldType.getArraySize(code),
                            valueBuffer,
                            littleEndian));
                    break;
                case POINTER:
                    break;
                case FUNCTIONPOINTER:
                    break;
            }
        }

        return result;
    }

    public char readFieldValueAsChar(byte[] fieldValueBuffer) {
        return stringValueParser.parseChar(fieldValueBuffer[0]);
    }

    public String readFieldValueAsString(byte[] fieldValueBuffer) {
        return stringValueParser.parseString(fieldValueBuffer);
    }

    public Number readFieldValueAsNumber(String structType,
                                         int fieldSize,
                                         byte[] fieldValueBuffer,
                                         boolean littleEndian) {
        switch (structType) {
            case DefaultCatalogPrimitives.PRIMITIVE_FLOAT:
            case DefaultCatalogPrimitives.PRIMITIVE_DOUBLE:
                switch (fieldSize) {
                    case 4:
                        return numberValueParser.readFloat(fieldValueBuffer, littleEndian);
                    case 8:
                        return numberValueParser.readDouble(fieldValueBuffer, littleEndian);
                    default:
                        throw new IllegalArgumentException("Unexpected number size: " + fieldSize);
                }
            case DefaultCatalogPrimitives.PRIMITIVE_SHORT:
            case DefaultCatalogPrimitives.PRIMITIVE_USHORT:
            case DefaultCatalogPrimitives.PRIMITIVE_INT:
            case DefaultCatalogPrimitives.PRIMITIVE_LONG:
            case DefaultCatalogPrimitives.PRIMITIVE_ULONG:
            case DefaultCatalogPrimitives.PRIMITIVE_INT64_T:
            case DefaultCatalogPrimitives.PRIMITIVE_UINT64_T:
            default:
                switch (fieldSize) {
                    case 2:
                        return numberValueParser.readShort(fieldValueBuffer, littleEndian);
                    case 4:
                        return numberValueParser.readInteger(fieldValueBuffer, littleEndian);
                    case 8:
                        return numberValueParser.readLong(fieldValueBuffer, littleEndian);
                    default:
                        throw new IllegalArgumentException("Unexpected number size: " + fieldSize);
                }
        }
    }

    public JsonObject renderReference(SDNAFieldDescriptor fieldDescriptor,
                                      int fieldStructSize,
                                      Number pointerAddress,
                                      String fieldName) {
        JsonObject result = new JsonObject();
        result.addProperty("referenceName", fieldName);
        result.addProperty("structType", fieldDescriptor.getStructType());
        result.addProperty("structTypeSize", fieldStructSize);
        result.addProperty("code", fieldDescriptor.getCode());
        result.addProperty("address", pointerAddress);
        return result;
    }
}

package info.blendformat.tools.sdna.model;

import info.blendformat.tools.sdna.defaults.DefaultCatalogPrimitives;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum FieldType {

    ADDRESS {
        @Override
        public Integer getSize(SDNAStructDescriptor structDescriptor,
                               Short pointerSize,
                               Integer typeSize,
                               String code) {
            return (int) pointerSize;
        }
    },
    NUMBER,

    CHAR,
    STRING {
        @Override
        public Integer getSize(SDNAStructDescriptor structDescriptor,
                               Short pointerSize,
                               Integer typeSize,
                               String code) {
            Matcher matcher = PATTERN_ARRAY.matcher(code);
            if (!matcher.find()) {
                throw new IllegalArgumentException("No array code: " + code);
            }
            return Integer.valueOf(matcher.group(2));
        }

        @Override
        public String getFieldName(SDNAStructDescriptor structDescriptor,
                                   Short pointerSize,
                                   Integer typeSize,
                                   String code) {
            Matcher matcher = PATTERN_ARRAY.matcher(code);
            if (!matcher.find()) {
                throw new IllegalArgumentException("No array code: " + code);
            }
            return matcher.group(1);
        }
    },

    STRUCT,
    ARRAY {
        @Override
        public Integer getSize(SDNAStructDescriptor structDescriptor,
                               Short pointerSize,
                               Integer typeSize,
                               String code) {
            return typeSize * getArraySize(code);
        }

        @Override
        public String getFieldName(SDNAStructDescriptor structDescriptor,
                                   Short pointerSize,
                                   Integer typeSize,
                                   String code) {
            Matcher matcher = PATTERN_ARRAY.matcher(code);
            if (!matcher.find()) {
                throw new IllegalArgumentException("No array code: " + code);
            }
            return matcher.group(1);
        }
    },

    POINTER {
        @Override
        public Integer getSize(SDNAStructDescriptor structDescriptor,
                               Short pointerSize,
                               Integer typeSize,
                               String code) {
            return (int) pointerSize;
        }

        @Override
        public String getFieldName(SDNAStructDescriptor structDescriptor,
                                   Short pointerSize,
                                   Integer typeSize,
                                   String code) {
            return code.substring(1);
        }
    },
    FUNCTIONPOINTER {
        @Override
        public Integer getSize(SDNAStructDescriptor structDescriptor,
                               Short pointerSize,
                               Integer typeSize,
                               String code) {
            return (int) pointerSize;
        }

        // (*test)()
        @Override
        public String getFieldName(SDNAStructDescriptor structDescriptor,
                                   Short pointerSize,
                                   Integer typeSize,
                                   String code) {
            return code.substring(2, code.length() - 3);
        }
    };

    private static Pattern PATTERN_ARRAY = Pattern.compile("^([^*]+)\\[([0-9]*)]$");

    public static FieldType getByFieldMetaData(String structType,
                                               int structSize,
                                               String fieldCode) {
        if (fieldCode.startsWith("*")) {
            return FieldType.POINTER;
        } else if (fieldCode.startsWith("(*")) {
            return FieldType.FUNCTIONPOINTER;
        }

        switch (structType) {
            case DefaultCatalogPrimitives.PRIMITIVE_FLOAT:
            case DefaultCatalogPrimitives.PRIMITIVE_DOUBLE:
            case DefaultCatalogPrimitives.PRIMITIVE_SHORT:
            case DefaultCatalogPrimitives.PRIMITIVE_USHORT:
            case DefaultCatalogPrimitives.PRIMITIVE_INT:
            case DefaultCatalogPrimitives.PRIMITIVE_LONG:
            case DefaultCatalogPrimitives.PRIMITIVE_ULONG:
            case DefaultCatalogPrimitives.PRIMITIVE_INT64_T:
            case DefaultCatalogPrimitives.PRIMITIVE_UINT64_T:
                return FieldType.NUMBER;
            case DefaultCatalogPrimitives.PRIMITIVE_CHAR:
                if (1 < structSize) {
                    return FieldType.STRING;
                } else {
                    return FieldType.CHAR;
                }
            default:
                return FieldType.STRUCT;
        }
    }

    public static Integer getArraySize(String code) {
        Matcher matcher = PATTERN_ARRAY.matcher(code);
        if (!matcher.find()) {
            throw new IllegalArgumentException("No array code: " + code);
        }
        return Integer.valueOf(matcher.group(2));
    }

    public Integer getSize(SDNAStructDescriptor structDescriptor,
                           Short pointerSize,
                           Integer structSize,
                           String code) {
        return structSize;
    }

    public String getFieldName(SDNAStructDescriptor structDescriptor,
                               Short pointerSize,
                               Integer typeSize,
                               String code) {
        return code;
    }
}

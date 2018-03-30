package info.blendformat.tools.sdna.defaults;

import info.blendformat.tools.sdna.model.SDNACatalog;

public class DefaultCatalogPrimitives extends SDNACatalog {

    public static final String PRIMITIVE_VOID = "void";
    public static final String PRIMITIVE_CHAR = "char";
    public static final String PRIMITIVE_UCHAR = "uchar";
    public static final String PRIMITIVE_SHORT = "short";
    public static final String PRIMITIVE_USHORT = "ushort";
    public static final String PRIMITIVE_INT = "int";
    public static final String PRIMITIVE_LONG = "long";
    public static final String PRIMITIVE_ULONG = "ulong";
    public static final String PRIMITIVE_FLOAT = "float";
    public static final String PRIMITIVE_DOUBLE = "double";
    public static final String PRIMITIVE_INT64_T = "int64_t";
    public static final String PRIMITIVE_UINT64_T = "uint64_t";

    public DefaultCatalogPrimitives() {
        super.registerSize(PRIMITIVE_VOID, 0);
        super.registerSize(PRIMITIVE_CHAR, 1);
        super.registerSize(PRIMITIVE_UCHAR, 1);
        super.registerSize(PRIMITIVE_SHORT, 2);
        super.registerSize(PRIMITIVE_USHORT, 2);
        super.registerSize(PRIMITIVE_INT, 4);
        super.registerSize(PRIMITIVE_LONG, 4);
        super.registerSize(PRIMITIVE_ULONG, 4);
        super.registerSize(PRIMITIVE_FLOAT, 4);
        super.registerSize(PRIMITIVE_DOUBLE, 8);
        super.registerSize(PRIMITIVE_INT64_T, 8);
        super.registerSize(PRIMITIVE_UINT64_T, 8);
    }
}

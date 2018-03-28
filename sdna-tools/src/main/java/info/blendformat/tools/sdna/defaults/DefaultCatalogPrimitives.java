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
        super.registerSize("void", 0);
        super.registerSize("char", 1);
        super.registerSize("uchar", 1);
        super.registerSize("short", 2);
        super.registerSize("ushort", 2);
        super.registerSize("int", 4);
        super.registerSize("long", 4);
        super.registerSize("ulong", 4);
        super.registerSize("float", 4);
        super.registerSize("double", 8);
        super.registerSize("int64_t", 8);
        super.registerSize("uint64_t", 8);
    }
}

package info.blendformat.tools.sdna.model;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.text.MessageFormat;

public class SDNAHeader implements Serializable {

    public static final String FIELDID_IDENTIFIER = "identifier";
    public static final String FIELDID_POINTERSIZE = "pointerSize";
    public static final String FIELDID_ENDIANNESS = "endianness";

    public static final char CODE_LITTLE_ENDIAN = 'v';
    public static final char CODE_POINTERSIZE_8 = '-';
    // public static final char CODE_POINTERSIZE_4 = '_';
    // public static final char CODE_BIG_ENDIAN = 'V';

    private String identifier;

    private short pointerSize = 8;

    private boolean isLittleEndian = true;

    private JsonObject extendedHeaderValues;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Short getPointerSize() {
        return pointerSize;
    }

    public void setPointerSize(short pointerSize) {
        this.pointerSize = pointerSize;
    }

    public boolean isLittleEndian() {
        return isLittleEndian;
    }

    public void setLittleEndian(boolean littleEndian) {
        isLittleEndian = littleEndian;
    }

    public JsonObject getExtendedHeaderValues() {
        return extendedHeaderValues;
    }

    public void setExtendedHeaderValues(JsonObject extendedHeaderValues) {
        this.extendedHeaderValues = extendedHeaderValues;
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "SDNAHeader'{'identifier=''{0}'', pointerSize={1}, isLittleEndian={2}, extendedHeaderValues={3}'}'",
                identifier, pointerSize, isLittleEndian,
                extendedHeaderValues);
    }
}

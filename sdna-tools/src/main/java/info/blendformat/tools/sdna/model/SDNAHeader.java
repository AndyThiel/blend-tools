package info.blendformat.tools.sdna.model;

import java.io.Serializable;
import java.util.Arrays;

public class SDNAHeader implements Serializable {

    public static final char CODE_LITTLE_ENDIAN = 'v';
    public static final char CODE_BIG_ENDIAN = 'V';
    public static final char CODE_POINTERSIZE_4 = '_';
    public static final char CODE_POINTERSIZE_8 = '-';

    private String identifier;

    private char codePointerSize;
    private char codeEndianness;
    private char[] codeVersion;

    public int getPointerSize() {
        return (SDNAHeader.CODE_POINTERSIZE_8 == codePointerSize) ? 8 : 4;
    }

    public boolean isLittleEndian() {
        return (CODE_LITTLE_ENDIAN == codeEndianness);
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public char getCodePointerSize() {
        return codePointerSize;
    }

    public void setCodePointerSize(char codePointerSize) {
        this.codePointerSize = codePointerSize;
    }

    public char getCodeEndianness() {
        return codeEndianness;
    }

    public void setCodeEndianness(char codeEndianness) {
        this.codeEndianness = codeEndianness;
    }

    public char[] getCodeVersion() {
        return codeVersion;
    }

    public void setCodeVersion(char[] codeVersion) {
        this.codeVersion = codeVersion;
    }

    @Override
    public String toString() {
        return String.format("SDNAHeader{identifier='%s', codePointerSize=%s, codeEndianness=%s, codeVersion=%s}",
                identifier,
                codePointerSize,
                codeEndianness,
                Arrays.toString(codeVersion));
    }
}

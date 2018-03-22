package info.blendformat.tools.sdna.parser;

import java.util.ArrayList;

public class StringValueParser {

    public ArrayList<String> parseNullTerminatedStrings(
            byte[] buffer,
            int nrOfStrings) {
        return new ArrayList<>();
    }

    public String readNullTerminatedString(byte[] buffer) {
        return null;
    }

    public char[] parseCharArray(byte[] buffer) {
        return null;
    }

    public char parseChar(byte bufferValue) {
        return (char) bufferValue;
    }

    public String parseString(byte[] buffer, int offset, int length) {
        return null;
    }

    public String parseString(byte[] buffer) {
        return null;
    }
}

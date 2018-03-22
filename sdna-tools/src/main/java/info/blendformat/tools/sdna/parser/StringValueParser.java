package info.blendformat.tools.sdna.parser;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class StringValueParser {

    public static final Charset CHARSET = StandardCharsets.UTF_8;

    public ArrayList<String> parseNullTerminatedStrings(
            byte[] buffer,
            int nrOfStrings) {
        return new ArrayList<>();
    }

    public String readNullTerminatedString(byte[] buffer) {
        return null;
    }

    public char[] parseCharArray(byte[] buffer) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
        CharBuffer charBuffer = CHARSET.decode(byteBuffer);
        return charBuffer.array();
    }

    public char parseChar(byte bufferValue) {
        return (char) bufferValue;
    }

    public String parseString(byte[] buffer) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
        CharBuffer charBuffer = CHARSET.decode(byteBuffer);
        return charBuffer.toString().trim();
    }
}

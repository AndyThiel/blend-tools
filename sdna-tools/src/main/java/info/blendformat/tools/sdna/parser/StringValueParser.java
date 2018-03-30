package info.blendformat.tools.sdna.parser;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class StringValueParser {

    public static final Charset CHARSET = StandardCharsets.UTF_8;

    public char parseChar(byte bufferValue) {
        return parseCharArray(new byte[]{bufferValue})[0];
    }

    public char[] parseCharArray(byte[] buffer) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
        CharBuffer charBuffer = CHARSET.decode(byteBuffer);
        return charBuffer.array();
    }

    public String parseString(byte[] buffer) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
        CharBuffer charBuffer = CHARSET.decode(byteBuffer);
        return charBuffer.toString().trim();
    }

    public ArrayList<String> parseNullTerminatedStrings(
            byte[] buffer,
            int nrOfStrings) {
        ArrayList<String> values = new ArrayList<>();

        int indexChar = 0;
        loopValue: for (int indexValue = 0; indexValue < nrOfStrings; indexValue++) {
            StringBuilder builder = new StringBuilder();

            loopChar: while (indexChar < buffer.length) {
                byte currentValue = buffer[indexChar];
                indexChar++;
                if (0 == currentValue) {
                    if (0 < builder.length()) {
                        values.add(builder.toString());
                    }
                    break loopChar;
                }
                builder.append((char) currentValue);
            }
        }

        return values;
    }

    public String readNullTerminatedString(byte[] buffer) {
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < buffer.length; index++) {
            byte currentValue = buffer[index];
            if (0 == currentValue) {
                break;
            }
            builder.append((char) currentValue);
        }
        return builder.toString();
    }
}

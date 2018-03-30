package info.blendformat.tools.sdna.parser;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class StringValueParserTest {

    private StringValueParser parser = new StringValueParser();

    @Test
    public void testParseChar() {
        char result = parser.parseChar((byte) 'B');
        assertEquals('B', result);
    }

    @Test
    public void testParseCharArray() {
        char[] result = parser.parseCharArray(
                new byte[]{'B', 'L', 'E', 'N', 'D', 'E', 'R'});
        assertEquals('B', result[0]);
        assertEquals('L', result[1]);
        assertEquals('E', result[2]);
        assertEquals('N', result[3]);
        assertEquals('D', result[4]);
        assertEquals('E', result[5]);
        assertEquals('R', result[6]);
    }

    @Test
    public void testParseString() {
        String result = parser.parseString(
                new byte[]{'B', 'L', 'E', 'N', 'D', 'E', 'R'});
        assertEquals("BLENDER", result);
    }

    @Test
    public void testParseNullTerminatedString() {
        String result = parser.readNullTerminatedString(new byte[]{'B', 'L', 'E', 'N', 0, 0, 0});
        assertEquals("BLEN", result);
    }

    @Test
    public void testParseNullTerminatedStrings() {
        ArrayList<String> result = parser.parseNullTerminatedStrings(
                new byte[]{'B', 'L', 'E', 'N', 'D', 'E', 'R', 0,
                        'T', 'E', 'S', 'T', 0,
                        'C', 'A', 'S', 'E', 0, 0},
                3);
        assertEquals("BLENDER", result.get(0));
        assertEquals("TEST", result.get(1));
        assertEquals("CASE", result.get(2));
    }
}

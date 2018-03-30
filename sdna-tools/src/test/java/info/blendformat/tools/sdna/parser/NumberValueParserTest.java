package info.blendformat.tools.sdna.parser;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class NumberValueParserTest {

    private NumberValueParser parser = new NumberValueParser();

    @Test
    public void testFormatByteAsHexString() {
        assertEquals("0x00", parser.formatByteAsHexString((byte) 0));
        assertEquals("0x01", parser.formatByteAsHexString((byte) 1));
        assertEquals("0x02", parser.formatByteAsHexString((byte) 2));
        assertEquals("0x04", parser.formatByteAsHexString((byte) 4));
        assertEquals("0x08", parser.formatByteAsHexString((byte) 8));
        assertEquals("0x10", parser.formatByteAsHexString((byte) 16));
        assertEquals("0x20", parser.formatByteAsHexString((byte) 32));
        assertEquals("0x40", parser.formatByteAsHexString((byte) 64));
        assertEquals("0x80", parser.formatByteAsHexString((byte) 128));
        assertEquals("0xFF", parser.formatByteAsHexString((byte) 255));
    }

    @Test
    public void testReadFloat() {
        Float floatLittleEndian = parser.readFloat(ByteBuffer.allocate(4)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putFloat(8.0f).array(), true);
        Float floatBigEndian = parser.readFloat(ByteBuffer.allocate(4)
                .order(ByteOrder.BIG_ENDIAN)
                .putFloat(8.0f).array(), false);
        Float floatWrongEndian = parser.readFloat(ByteBuffer.allocate(4)
                .order(ByteOrder.BIG_ENDIAN)
                .putFloat(8.0f).array(), true);
        assertEquals(8.0f, floatLittleEndian, 0.002);
        assertEquals(8.0f, floatBigEndian, 0.002);
        assertNotEquals(8.0f, floatWrongEndian, 0.002);
    }

    @Test
    public void testReadDouble() {
        Double doubleLittleEndian = parser.readDouble(ByteBuffer.allocate(8)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putDouble(360.0).array(), true);
        Double doubleBigEndian = parser.readDouble(ByteBuffer.allocate(8)
                .order(ByteOrder.BIG_ENDIAN)
                .putDouble(360.0).array(), false);
        Double doubleWrongEndian = parser.readDouble(ByteBuffer.allocate(8)
                .order(ByteOrder.BIG_ENDIAN)
                .putDouble(360.0).array(), true);
        assertEquals(360.0, doubleLittleEndian, 0.002);
        assertEquals(360.0, doubleBigEndian, 0.002);
        assertNotEquals(360.0, doubleWrongEndian, 0.002);
    }

    @Test
    public void testReadShort() {
        Short shortLittleEndian = parser.readShort(ByteBuffer.allocate(2)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putShort((short) 4).array(), true);
        Short shortBigEndian = parser.readShort(ByteBuffer.allocate(2)
                .order(ByteOrder.BIG_ENDIAN)
                .putShort((short) 4).array(), false);
        Short shortWrongEndian = parser.readShort(ByteBuffer.allocate(2)
                .order(ByteOrder.BIG_ENDIAN)
                .putShort((short) 4).array(), true);
        assertEquals((short) 4, (short) shortLittleEndian);
        assertEquals((short) 4, (short) shortBigEndian);
        assertNotEquals((short) 4, (short) shortWrongEndian);
    }

    @Test
    public void testReadInteger() {
        Integer intLittleEndian = parser.readInteger(ByteBuffer.allocate(4)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putInt(17).array(), true);
        Integer intBigEndian = parser.readInteger(ByteBuffer.allocate(4)
                .order(ByteOrder.BIG_ENDIAN)
                .putInt(17).array(), false);
        Integer intWrongEndian = parser.readInteger(ByteBuffer.allocate(4)
                .order(ByteOrder.BIG_ENDIAN)
                .putInt(17).array(), true);
        assertEquals(17, (int) intLittleEndian);
        assertEquals(17, (int) intBigEndian);
        assertNotEquals(17, (int) intWrongEndian);
    }

    @Test
    public void testReadLong() {
        Long intLittleEndian = parser.readLong(ByteBuffer.allocate(8)
                .order(ByteOrder.LITTLE_ENDIAN)
                .putLong(1711).array(), true);
        Long intBigEndian = parser.readLong(ByteBuffer.allocate(8)
                .order(ByteOrder.BIG_ENDIAN)
                .putLong(1711).array(), false);
        Long intWrongEndian = parser.readLong(ByteBuffer.allocate(8)
                .order(ByteOrder.BIG_ENDIAN)
                .putLong(1711).array(), true);
        assertEquals(1711, (long) intLittleEndian);
        assertEquals(1711, (long) intBigEndian);
        assertNotEquals(1711, (long) intWrongEndian);
    }
}

package info.blendformat.tools.sdna.parser;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class NumberValueParser {

    public String formatByteAsHexString(byte currentByte) {
        return String.format("%02X", currentByte);
    }

    public Float readFloat(byte[] buffer, boolean littleEndian) {
        ByteBuffer wrapper = wrapByteRange(buffer, littleEndian);
        return wrapper.getFloat();
    }

    public Double readDouble(byte[] buffer, boolean littleEndian) {
        ByteBuffer wrapper = wrapByteRange(buffer, littleEndian);
        return wrapper.getDouble();
    }

    public Short readShort(byte[] buffer, boolean littleEndian) {
        ByteBuffer wrapper = wrapByteRange(buffer, littleEndian);
        return wrapper.getShort();
    }

    public Integer readInteger(byte[] buffer, boolean littleEndian) {
        ByteBuffer wrapper = wrapByteRange(buffer, littleEndian);
        return wrapper.getInt();
    }

    public Long readLong(byte[] buffer, boolean littleEndian) {
        ByteBuffer wrapper = wrapByteRange(buffer, littleEndian);
        return wrapper.getLong();
    }

    private ByteBuffer wrapByteRange(byte[] numberArray,
                                     boolean littleEndian) {

        ByteBuffer wrapper = ByteBuffer.wrap(numberArray);

        boolean shouldSwap = littleEndian != ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN);
        boolean isSwapped = !ByteOrder.nativeOrder().equals(wrapper.order());

        if (shouldSwap != isSwapped) {
            wrapper.order(wrapper.order().equals(ByteOrder.LITTLE_ENDIAN)
                    ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        }
        return wrapper;
    }
}

package info.blendformat.tools.sdna.testdata;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class TestcaseFileMinimalCatalogBigEndian {

    private static final int SIZE_METADATA = 24;
    private static final int SIZE_BLOCK_TEST = 24;
    private static final int SIZE_BLOCK_CATALOG = 40;
    private static final int SIZE_BLOCK_END = 0;

    private final byte[] header = new byte[]{
            'C', 'A', 'T', 'A', 'L', 'O', 'G',
            '-', 'V',
            '0', '1', '2'};

    private final ByteBuffer metaDataTest = ByteBuffer
            .allocate(SIZE_METADATA)
            .order(ByteOrder.LITTLE_ENDIAN)
            .put(new byte[]{'T', 'E', 'S', 'T'})
            .putInt(swapEndianness(SIZE_BLOCK_TEST))
            .putLong(swapEndianness((long) 1))
            .putInt(swapEndianness(1))
            .putInt(swapEndianness(1));

    private final ByteBuffer metaDataCatalog = ByteBuffer
            .allocate(SIZE_METADATA)
            .order(ByteOrder.LITTLE_ENDIAN)
            .put(new byte[]{'D', 'N', 'A', '1'})
            .putInt(swapEndianness(SIZE_BLOCK_CATALOG))
            .putLong(swapEndianness(2))
            .putInt(swapEndianness(0))
            .putInt(swapEndianness(1));

    private final ByteBuffer dataCatalog = ByteBuffer
            .allocate(SIZE_BLOCK_CATALOG)
            .order(ByteOrder.LITTLE_ENDIAN)
            .put(new byte[]{'S', 'D', 'N', 'A', 'N', 'A', 'M', 'E'})
            .putInt(swapEndianness(0))
            .put(new byte[]{'T', 'Y', 'P', 'E'})
            .putInt(swapEndianness(1))
            .put(new byte[]{'c', 'h', 'a', 'r', 0,
                    'T', 'L', 'E', 'N'})
            .putShort(swapEndianness((short) 1))
            .put((byte) 0)
            .put(new byte[]{'S', 'T', 'R', 'C'})
            .putInt(swapEndianness(0));

    private final ByteBuffer metaDataEnd = ByteBuffer
            .allocate(SIZE_METADATA)
            .order(ByteOrder.LITTLE_ENDIAN)
            .put(new byte[]{'E', 'N', 'D', 'B'})
            .putInt(swapEndianness(SIZE_BLOCK_END))
            .putLong(swapEndianness(3))
            .putInt(swapEndianness(0))
            .putInt(swapEndianness(1));

    public ByteArrayInputStream toInputStream() {

        byte[] contentTest = new byte[SIZE_BLOCK_TEST];
        Arrays.fill(contentTest, (byte) 0);

        ByteBuffer fullFileBuffer = ByteBuffer.allocate(header.length +
                (4 * SIZE_METADATA) + SIZE_BLOCK_TEST + SIZE_BLOCK_CATALOG)
                .put(header)
                .put(metaDataTest.array())
                .put(contentTest)
                .put(metaDataCatalog.array())
                .put(dataCatalog.array())
                .put(metaDataEnd.array());

        return new ByteArrayInputStream(fullFileBuffer.array());
    }

    private static short swapEndianness(short original) {
        ByteBuffer wrapper = ByteBuffer.allocate(2)
                .putShort(original);
        wrapper.order(wrapper.order().equals(ByteOrder.LITTLE_ENDIAN)
                ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        return wrapper.getShort(0);
    }

    private static int swapEndianness(int original) {
        ByteBuffer wrapper = ByteBuffer.allocate(4)
                .putInt(original);
        wrapper.order(wrapper.order().equals(ByteOrder.LITTLE_ENDIAN)
                ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        return wrapper.getInt(0);
    }

    private static long swapEndianness(long original) {
        ByteBuffer wrapper = ByteBuffer.allocate(8)
                .putLong(original);
        wrapper.order(wrapper.order().equals(ByteOrder.LITTLE_ENDIAN)
                ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
        return wrapper.getLong(0);
    }
}

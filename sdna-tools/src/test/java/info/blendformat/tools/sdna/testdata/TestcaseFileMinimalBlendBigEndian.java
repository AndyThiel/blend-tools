package info.blendformat.tools.sdna.testdata;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class TestcaseFileMinimalBlendBigEndian {

    private static final int SIZE_METADATA = 24;
    private static final int SIZE_BLOCK_GLOBAL = swapEndianness((int) 24);
    private static final int SIZE_BLOCK_SCENE = swapEndianness((int) 256);
    private static final int SIZE_BLOCK_CATALOG = swapEndianness((int) 512);
    private static final int SIZE_BLOCK_END = swapEndianness((int) 0);

    private final byte[] header = new byte[]{
            'B', 'L', 'E', 'N', 'D', 'E', 'R',
            '-', 'V',
            '2', '7', '9'};

    private final ByteBuffer metaDataGlobal = ByteBuffer
            .allocate(SIZE_METADATA)
            .order(ByteOrder.LITTLE_ENDIAN)
            .put(new byte[]{'G', 'L', 'O', 'B'})
            .putInt(SIZE_BLOCK_GLOBAL)
            .putLong(swapEndianness((long) 1))
            .putInt(swapEndianness(1))
            .putInt(swapEndianness(1));

    private final ByteBuffer metaDataScene = ByteBuffer
            .allocate(SIZE_METADATA)
            .order(ByteOrder.LITTLE_ENDIAN)
            .put(new byte[]{'S', 'C', 0, 0})
            .putInt(SIZE_BLOCK_SCENE)
            .putLong(swapEndianness((long) 2))
            .putInt(swapEndianness(2))
            .putInt(swapEndianness(1));

    private final ByteBuffer metaDataCatalog = ByteBuffer
            .allocate(SIZE_METADATA)
            .order(ByteOrder.LITTLE_ENDIAN)
            .put(new byte[]{'D', 'N', 'A', '1'})
            .putInt(SIZE_BLOCK_CATALOG)
            .putLong(swapEndianness((long) 3))
            .putInt(swapEndianness(0))
            .putInt(swapEndianness(1));

    private final ByteBuffer metaDataEnd = ByteBuffer
            .allocate(SIZE_METADATA)
            .order(ByteOrder.LITTLE_ENDIAN)
            .put(new byte[]{'E', 'N', 'D', 'B'})
            .putInt(SIZE_BLOCK_END)
            .putLong(swapEndianness((long) 0))
            .putInt(swapEndianness(0))
            .putInt(swapEndianness(1));

    // private final ByteBuffer contentGlobal = ByteBuffer.allocate(SIZE_BLOCK_GLOBAL);
    // private final ByteBuffer contentScene = ByteBuffer.allocate(SIZE_BLOCK_SCENE);
    // private final ByteBuffer contentCatalog = ByteBuffer.allocate(SIZE_BLOCK_CATALOG);

    public ByteArrayInputStream toInputStream() {

        byte[] contentGlobal = new byte[24];
        byte[] contentScene = new byte[256];
        byte[] contentCatalog = new byte[512];
        Arrays.fill(contentGlobal, (byte) 0);
        Arrays.fill(contentScene, (byte) 0);
        Arrays.fill(contentCatalog, (byte) 0);

        ByteBuffer fullFileBuffer = ByteBuffer.allocate(header.length + (4 * SIZE_METADATA) +
                24 + 256 + 512)
                .put(header)
                .put(metaDataGlobal.array())
                .put(contentGlobal)
                // .put(contentGlobal.array())
                .put(metaDataScene.array())
                .put(contentScene)
                // .put(contentScene.array())
                .put(metaDataCatalog.array())
                .put(contentCatalog)
                // .put(contentCatalog.array())
                .put(metaDataEnd.array());

        return new ByteArrayInputStream(fullFileBuffer.array());
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

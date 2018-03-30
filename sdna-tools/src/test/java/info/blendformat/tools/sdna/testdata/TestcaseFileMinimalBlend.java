package info.blendformat.tools.sdna.testdata;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class TestcaseFileMinimalBlend {

    private static final int SIZE_METADATA = 24;
    private static final int SIZE_BLOCK_GLOBAL = 24;
    private static final int SIZE_BLOCK_SCENE = 256;
    private static final int SIZE_BLOCK_CATALOG = 512;
    private static final int SIZE_BLOCK_END = 0;

    private final byte[] header = new byte[]{
            'B', 'L', 'E', 'N', 'D', 'E', 'R',
            '-', 'v',
            '2', '7', '9'};

    private final ByteBuffer metaDataGlobal = ByteBuffer
            .allocate(SIZE_METADATA)
            .order(ByteOrder.LITTLE_ENDIAN)
            .put(new byte[]{'G', 'L', 'O', 'B'})
            .putInt(SIZE_BLOCK_GLOBAL)
            .putLong(1)
            .putInt(1)
            .putInt(1);

    private final ByteBuffer metaDataScene = ByteBuffer
            .allocate(SIZE_METADATA)
            .order(ByteOrder.LITTLE_ENDIAN)
            .put(new byte[]{'S', 'C', 0, 0})
            .putInt(SIZE_BLOCK_SCENE)
            .putLong(2)
            .putInt(2)
            .putInt(1);

    private final ByteBuffer metaDataCatalog = ByteBuffer
            .allocate(SIZE_METADATA)
            .order(ByteOrder.LITTLE_ENDIAN)
            .put(new byte[]{'D', 'N', 'A', '1'})
            .putInt(SIZE_BLOCK_CATALOG)
            .putLong(3)
            .putInt(0)
            .putInt(1);

    private final ByteBuffer metaDataEnd = ByteBuffer
            .allocate(SIZE_METADATA)
            .order(ByteOrder.LITTLE_ENDIAN)
            .put(new byte[]{'E', 'N', 'D', 'B'})
            .putInt(SIZE_BLOCK_END)
            .putLong(0)
            .putInt(0)
            .putInt(1);

    public ByteArrayInputStream toInputStream() {

        byte[] contentGlobal = new byte[SIZE_BLOCK_GLOBAL];
        byte[] contentScene = new byte[SIZE_BLOCK_SCENE];
        byte[] contentCatalog = new byte[SIZE_BLOCK_CATALOG];
        Arrays.fill(contentGlobal, (byte) 0);
        Arrays.fill(contentScene, (byte) 0);
        Arrays.fill(contentCatalog, (byte) 0);

        ByteBuffer fullFileBuffer = ByteBuffer.allocate(header.length + (4 * SIZE_METADATA) +
                SIZE_BLOCK_GLOBAL + SIZE_BLOCK_SCENE + SIZE_BLOCK_CATALOG)
                .put(header)
                .put(metaDataGlobal.array())
                .put(contentGlobal)
                .put(metaDataScene.array())
                .put(contentScene)
                .put(metaDataCatalog.array())
                .put(contentCatalog)
                .put(metaDataEnd.array());

        return new ByteArrayInputStream(fullFileBuffer.array());
    }
}

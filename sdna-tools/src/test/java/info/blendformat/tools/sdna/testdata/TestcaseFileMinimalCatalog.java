package info.blendformat.tools.sdna.testdata;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class TestcaseFileMinimalCatalog {

    private static final int SIZE_METADATA = 24;
    private static final int SIZE_BLOCK_TEST = 24;
    private static final int SIZE_BLOCK_CATALOG = 40;
    private static final int SIZE_BLOCK_END = 0;

    private final byte[] header = new byte[]{
            'C', 'A', 'T', 'A', 'L', 'O', 'G',
            '-', 'v',
            '0', '1', '2'};

    private final ByteBuffer metaDataTest = ByteBuffer
            .allocate(SIZE_METADATA)
            .order(ByteOrder.LITTLE_ENDIAN)
            .put(new byte[]{'T', 'E', 'S', 'T'})
            .putInt(SIZE_BLOCK_TEST)
            .putLong(1)
            .putInt(1)
            .putInt(1);

    private final ByteBuffer metaDataCatalog = ByteBuffer
            .allocate(SIZE_METADATA)
            .order(ByteOrder.LITTLE_ENDIAN)
            .put(new byte[]{'D', 'N', 'A', '1'})
            .putInt(SIZE_BLOCK_CATALOG)
            .putLong(2)
            .putInt(0)
            .putInt(1);

    private final ByteBuffer dataCatalog = ByteBuffer
            .allocate(SIZE_BLOCK_CATALOG)
            .order(ByteOrder.LITTLE_ENDIAN)
            .put(new byte[]{'S', 'D', 'N', 'A', 'N', 'A', 'M', 'E'})
            .putInt(0)
            .put(new byte[]{'T', 'Y', 'P', 'E'})
            .putInt(1)
            .put(new byte[]{'c', 'h', 'a', 'r', 0,
                    'T', 'L', 'E', 'N'})
            .putShort((short) 1)
            .put((byte) 0)
            .put(new byte[]{'S', 'T', 'R', 'C'})
            .putInt(0);

    private final ByteBuffer metaDataEnd = ByteBuffer
            .allocate(SIZE_METADATA)
            .order(ByteOrder.LITTLE_ENDIAN)
            .put(new byte[]{'E', 'N', 'D', 'B'})
            .putInt(SIZE_BLOCK_END)
            .putLong(3)
            .putInt(0)
            .putInt(1);

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
}

package info.blendformat.tools.sdna.reader.types;

import info.blendformat.tools.sdna.model.SDNABlockMetaData;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class SDNAFileBlockDataReader {

    public static final int BUFFER_SIZE = 1024;

    public byte[] readData(SDNABlockMetaData metaData,
                           BufferedInputStream inputStream) throws IOException {

        ByteBuffer data = ByteBuffer.allocate(metaData.getSize());

        byte[] buffer = new byte[BUFFER_SIZE];
        int read, readTotal = 0, toRead = Math.min(
                BUFFER_SIZE,
                metaData.getSize());

        while (0 < (read = inputStream.read(buffer, 0, toRead))) {
            data.put(Arrays.copyOfRange(buffer, 0, read));
            readTotal += read;
            toRead = Math.min(
                    BUFFER_SIZE,
                    (metaData.getSize() - readTotal));
        }

        return data.array();
    }
}

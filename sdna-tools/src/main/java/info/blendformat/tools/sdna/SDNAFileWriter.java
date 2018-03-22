package info.blendformat.tools.sdna;

import info.blendformat.tools.sdna.model.SDNAFileContent;
import info.blendformat.tools.sdna.reader.ReaderConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SDNAFileWriter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SDNAFileReader.class);

    public void write(ReaderConfig config,
                     String path, String fileName,
                     SDNAFileContent content) throws IOException {
        InputStream inputStream = getByteStream(config, content);
        if (null == inputStream) {
            return;
        }
        // TODO Implement
    }

    public void write(ReaderConfig config,
                      OutputStream outputStream,
                      SDNAFileContent content) throws IOException {
        InputStream inputStream = getByteStream(config, content);
        if (null == inputStream) {
            return;
        }
        // TODO Implement
    }

    public InputStream getByteStream(ReaderConfig config,
                                     SDNAFileContent content) throws IOException {
        // TODO Implement
        return null;
    }
}

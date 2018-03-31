package info.blendformat.tools.sdna;

import info.blendformat.tools.sdna.model.SDNACatalog;
import info.blendformat.tools.sdna.model.SDNAFileContent;
import info.blendformat.tools.sdna.reader.ReaderConfig;
import info.blendformat.tools.sdna.reader.SDNAFileStreamReader;
import info.blendformat.tools.sdna.reader.events.FileStreamEventSubscriber;
import info.blendformat.tools.sdna.reader.events.SDNACatalogSubscriber;
import info.blendformat.tools.sdna.reader.events.SDNAFileContentSubscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class SDNAFileReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(SDNAFileReader.class);

    public SDNAFileContent read(ReaderConfig config,
                                String path,
                                String fileName) throws IOException {
        return read(config,
                path + File.separator + fileName);
    }

    public SDNAFileContent read(ReaderConfig config,
                                String filePath) throws IOException {
        return read(config, new File(filePath));
    }

    public SDNAFileContent read(ReaderConfig config,
                                File fileHandle) throws IOException {
        FileInputStream inputStream = new FileInputStream(fileHandle);
        SDNAFileContent result = read(config, inputStream);
        try {
            inputStream.close();
        } catch (IOException e) {
            LOGGER.warn("Closing the file input stream did not work.");
        }
        return result;
    }

    public SDNAFileContent read(ReaderConfig config,
                                InputStream inputStream) throws IOException {
        return read(config, new BufferedInputStream(inputStream));
    }

    public SDNACatalog extractCatalog(ReaderConfig config,
                                      String path,
                                      String fileName) throws IOException {
        return extractCatalog(config,
                path + File.separator + fileName);
    }

    public SDNACatalog extractCatalog(ReaderConfig config,
                                      String filePath) throws IOException {
        return extractCatalog(config, new File(filePath));
    }

    public SDNACatalog extractCatalog(ReaderConfig config,
                                      File fileHandle) throws IOException {
        FileInputStream inputStream = new FileInputStream(fileHandle);
        SDNACatalog catalog = extractCatalog(config, inputStream);
        try {
            inputStream.close();
        } catch (IOException e) {
            LOGGER.warn("Closing the file input stream did not work.");
        }
        return catalog;
    }

    public SDNACatalog extractCatalog(ReaderConfig config,
                                      InputStream inputStream) throws IOException {
        return extractCatalog(config, new BufferedInputStream(inputStream));
    }

    private SDNAFileContent read(ReaderConfig config,
                                 BufferedInputStream inputStream)
            throws IOException {

        SDNAFileContentSubscriber subscriber = new SDNAFileContentSubscriber();
        readFileStream(config, inputStream, subscriber);
        SDNAFileContent content = subscriber.getFileContent();
        return content;
    }

    private SDNACatalog extractCatalog(ReaderConfig config,
                                       BufferedInputStream inputStream)
            throws IOException {

        LOGGER.debug("Executing extractCatalog ...");

        SDNACatalogSubscriber subscriber = new SDNACatalogSubscriber();
        readFileStream(config, inputStream, subscriber);
        SDNACatalog content = subscriber.getCatalog();

        return content;
    }

    private void readFileStream(ReaderConfig config,
                                BufferedInputStream inputStream,
                                FileStreamEventSubscriber subscriber) throws IOException {

        LOGGER.debug("Executing readFileStream ...");

        SDNAFileStreamReader fileStreamReader = new SDNAFileStreamReader();
        fileStreamReader.addSubscriber(subscriber);
        fileStreamReader.readFile(config, inputStream);
        fileStreamReader.removeSubscriber(subscriber);
    }
}

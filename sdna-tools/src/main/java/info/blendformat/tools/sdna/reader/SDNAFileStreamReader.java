package info.blendformat.tools.sdna.reader;

import info.blendformat.tools.sdna.model.SDNABlockMetaData;
import info.blendformat.tools.sdna.model.SDNACatalog;
import info.blendformat.tools.sdna.model.SDNAFileInfo;
import info.blendformat.tools.sdna.model.SDNAHeader;
import info.blendformat.tools.sdna.reader.events.FileStreamEventPublisher;
import info.blendformat.tools.sdna.reader.events.FileStreamEventSubscriber;
import info.blendformat.tools.sdna.reader.types.SDNAFileBlockDataReader;
import info.blendformat.tools.sdna.reader.types.SDNAFileBlockMetaDataReader;
import info.blendformat.tools.sdna.reader.types.SDNAFileCatalogReader;
import info.blendformat.tools.sdna.reader.types.SDNAFileHeaderReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SDNAFileStreamReader implements FileStreamEventPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(SDNAFileStreamReader.class);

    private final ArrayList<FileStreamEventSubscriber> subscribers
            = new ArrayList<>();

    private SDNAFileHeaderReader headerReader = new SDNAFileHeaderReader();
    private SDNAFileCatalogReader catalogReader = new SDNAFileCatalogReader();

    private SDNAFileBlockMetaDataReader metaDataReader =
            new SDNAFileBlockMetaDataReader();

    private SDNAFileBlockDataReader dataReader =
            new SDNAFileBlockDataReader();

    public void readFile(ReaderConfig config,
                         BufferedInputStream inputStream) throws IOException {

        fireReadProcessStarted();

        SDNAFileInfo fileInfo = new SDNAFileInfo();

        SDNAHeader header = headerReader.readHeader(config, inputStream);
        fireHeaderRead(header);

        SDNABlockMetaData metaData;
        final String catalogCode = config.getCatalogCode();
        LOGGER.info("... starting meta data loop ...");
        while (null != (metaData = metaDataReader.readMetaData(
                config, header, inputStream))) {

            LOGGER.info("... reading: " + metaData + " ...");

            fireBlockMetaDataRead(metaData);

            byte[] data = dataReader.readData(metaData, inputStream);
            fireBlockDataRead(metaData, data);

            if (catalogCode.equals(metaData.getCode())) {
                LOGGER.info("... calling catalogReader.readCatalog ...");
                SDNACatalog catalog = catalogReader.readCatalog(
                        config, header, metaData, data);
                fireSDNACatalogRead(catalog);
            }
        }

        fireReadProcessComplete(fileInfo);
    }

    @Override
    public void addSubscriber(FileStreamEventSubscriber subscsriber) {
        subscribers.add(subscsriber);
    }

    @Override
    public void removeSubscriber(FileStreamEventSubscriber subscsriber) {
        subscribers.remove(subscsriber);
    }

    @Override
    public void fireReadProcessStarted() {
        for (FileStreamEventSubscriber subscriber : subscribers) {
            subscriber.onReadProcessStarted();
        }
    }

    @Override
    public void fireHeaderRead(SDNAHeader header) {
        for (FileStreamEventSubscriber subscriber : subscribers) {
            subscriber.onHeaderRead(header);
        }
    }

    @Override
    public void fireBlockMetaDataRead(SDNABlockMetaData metaData) {
        for (FileStreamEventSubscriber subscriber : subscribers) {
            subscriber.onBlockMetaDataRead(metaData);
        }
    }

    @Override
    public void fireBlockDataRead(SDNABlockMetaData metaData, byte[] data) {
        for (FileStreamEventSubscriber subscriber : subscribers) {
            subscriber.onBlockDataRead(metaData, data);
        }
    }

    @Override
    public void fireSDNACatalogRead(SDNACatalog catalog) {
        for (FileStreamEventSubscriber subscriber : subscribers) {
            subscriber.onSDNACatalogRead(catalog);
        }
    }

    @Override
    public void fireReadProcessComplete(SDNAFileInfo fileInfo) {
        for (FileStreamEventSubscriber subscriber : subscribers) {
            subscriber.onReadProcessComplete(fileInfo);
        }
    }
}

package info.blendformat.tools.sdna.reader;

import info.blendformat.tools.sdna.model.SDNABlockMetaData;
import info.blendformat.tools.sdna.model.SDNACatalog;
import info.blendformat.tools.sdna.model.SDNAFileInfo;
import info.blendformat.tools.sdna.model.SDNAHeader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SDNAFileStreamReader implements FileStreamEventPublisher {

    public static final String BLOCKCODE_DNA1 = "DNA1";

    private final ArrayList<FileStreamEventSubscriber> subscribers
            = new ArrayList<>();

    public void readFile(ReaderConfig config,
                         BufferedInputStream inputStream) throws IOException {

        fireReadProcessStarted();

        SDNAFileInfo fileInfo = new SDNAFileInfo();

        SDNAHeader header = readHeader(inputStream);
        fireHeaderRead(header);

        SDNABlockMetaData metaData;
        while (null != (metaData = readNextBlockMetaData(inputStream))) {
            fireBlockMetaDataRead(metaData);
            byte[] data = readBlockData(metaData, inputStream);
            fireBlockDataRead(metaData, data);

            if (BLOCKCODE_DNA1.equals(metaData.getCode())) {
                SDNACatalog catalog = readCatalog(metaData, data);
                fireSDNACatalogRead(catalog);
            }
        }

        fireReadProcessComplete(fileInfo);
    }

    private SDNAHeader readHeader(BufferedInputStream inputStream) throws IOException {
        return new SDNAHeader();
    }

    private SDNABlockMetaData readNextBlockMetaData(BufferedInputStream inputStream) throws IOException {
        return new SDNABlockMetaData();
    }

    private byte[] readBlockData(SDNABlockMetaData metaData,
                                 BufferedInputStream inputStream) throws IOException {

        return new byte[0];
    }

    private SDNACatalog readCatalog(SDNABlockMetaData metaData,
                                    byte[] data) throws IOException {
        return new SDNACatalog();
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

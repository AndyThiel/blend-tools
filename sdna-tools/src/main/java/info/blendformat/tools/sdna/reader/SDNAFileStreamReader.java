package info.blendformat.tools.sdna.reader;

import info.blendformat.tools.sdna.model.SDNABlockMetaData;
import info.blendformat.tools.sdna.model.SDNACatalog;
import info.blendformat.tools.sdna.model.SDNAFileInfo;
import info.blendformat.tools.sdna.model.SDNAHeader;
import info.blendformat.tools.sdna.parser.NumberValueParser;
import info.blendformat.tools.sdna.parser.StringValueParser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SDNAFileStreamReader implements FileStreamEventPublisher {

    public static final String BLOCKCODE_DNA1 = "DNA1";

    private final ArrayList<FileStreamEventSubscriber> subscribers
            = new ArrayList<>();

    private StringValueParser stringValueParser = new StringValueParser();
    private NumberValueParser numberValueParser = new NumberValueParser();

    public void readFile(ReaderConfig config,
                         BufferedInputStream inputStream) throws IOException {

        fireReadProcessStarted();

        SDNAFileInfo fileInfo = new SDNAFileInfo();

        SDNAHeader header = readHeader(config, inputStream);
        fireHeaderRead(header);

        SDNABlockMetaData metaData;
        while (null != (metaData = readNextBlockMetaData(
                config, header, inputStream))) {

            fireBlockMetaDataRead(metaData);
            byte[] data = readBlockData(config, metaData, inputStream);
            fireBlockDataRead(metaData, data);

            if (BLOCKCODE_DNA1.equals(metaData.getCode())) {
                SDNACatalog catalog = readCatalog(config, metaData, data);
                fireSDNACatalogRead(catalog);
            }
        }

        fireReadProcessComplete(fileInfo);
    }

    private SDNAHeader readHeader(ReaderConfig config,
                                  BufferedInputStream inputStream) throws IOException {

        int sizeIdentifier = config.getSizeIdentifier();
        int sizeHeader = sizeIdentifier + 5;
        int offset = 0;

        byte[] buffer = new byte[sizeHeader];
        int read = inputStream.read(buffer, offset, sizeHeader);
        if (read != sizeHeader) {
            throw new IOException();
        }

        String identifier = stringValueParser.parseString(
                fetchRange(buffer, offset, sizeIdentifier));
        offset += sizeIdentifier;

        char codePointerSize = stringValueParser.parseChar(
                fetchRange(buffer, offset, 1)[0]);
        offset += 1;

        char codeEndianness = stringValueParser.parseChar(
                fetchRange(buffer, offset, 1)[0]);
        offset += 1;

        char[] codeVersion = stringValueParser.parseCharArray(
                fetchRange(buffer, offset, 3));

        SDNAHeader header = new SDNAHeader();
        header.setIdentifier(identifier);
        header.setCodePointerSize(codePointerSize);
        header.setCodeEndianness(codeEndianness);
        header.setCodeVersion(codeVersion);
        return header;
    }

    private SDNABlockMetaData readNextBlockMetaData(ReaderConfig config,
                                                    SDNAHeader header,
                                                    BufferedInputStream inputStream) throws IOException {

        int pointerSize = header.getPointerSize();
        boolean littleEndian = header.isLittleEndian();
        int sizeMetaData = pointerSize + 16;
        int offset = 0;

        byte[] buffer = new byte[sizeMetaData];
        int read = inputStream.read(buffer, offset, sizeMetaData);
        if (0 >= read) {
            return null;
        }

        String code = stringValueParser.parseString(
                fetchRange(buffer, offset, 4));
        offset += 4;

        int size = numberValueParser.readInteger(
                fetchRange(buffer, offset, NumberValueParser.LENGTH_INT),
                littleEndian);
        offset += NumberValueParser.LENGTH_INT;

        long address = numberValueParser.readLong(
                fetchRange(buffer, offset, NumberValueParser.LENGTH_LONG),
                littleEndian);
        offset += NumberValueParser.LENGTH_LONG;

        int sdnaIndex = numberValueParser.readInteger(
                fetchRange(buffer, offset, NumberValueParser.LENGTH_INT),
                littleEndian);
        offset += NumberValueParser.LENGTH_INT;

        int count = numberValueParser.readInteger(
                fetchRange(buffer, offset, NumberValueParser.LENGTH_INT),
                littleEndian);

        SDNABlockMetaData metaData = new SDNABlockMetaData();
        metaData.setCode(code);
        metaData.setSize(size);
        metaData.setAddress(address);
        metaData.setSdnaIndex(sdnaIndex);
        metaData.setCount(count);

        return metaData;
    }

    private byte[] readBlockData(ReaderConfig config,
                                 SDNABlockMetaData metaData,
                                 BufferedInputStream inputStream) throws IOException {
        byte[] data = new byte[metaData.getSize()];
        int read = inputStream.read(data, 0, metaData.getSize());
        if (metaData.getSize() != read) {
            throw new IOException();
        }
        return data;
    }

    private SDNACatalog readCatalog(ReaderConfig config,
                                    SDNABlockMetaData metaData,
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

    private final byte[] fetchRange(byte[] buffer, int offset, int length) {
        return Arrays.copyOfRange(buffer, offset, (offset + length));
    }
}

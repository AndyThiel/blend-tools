package info.blendformat.tools.sdna.reader.types;

import com.google.gson.JsonObject;
import info.blendformat.tools.sdna.model.SDNAHeader;
import info.blendformat.tools.sdna.model.SDNAStructDescriptor;
import info.blendformat.tools.sdna.parser.StructParser;
import info.blendformat.tools.sdna.reader.ReaderConfig;

import java.io.BufferedInputStream;
import java.io.IOException;

public class SDNAFileHeaderReader {

    private StructParser structParser = new StructParser();

    public SDNAHeader readHeader(ReaderConfig config,
                                 BufferedInputStream inputStream) throws IOException {

        SDNAStructDescriptor headerDescriptor = config.getBaseHeaderDescriptor();

        Integer baseHeaderSize = config.getBaseHeaderDescriptor().getSize();
        byte[] buffer = new byte[baseHeaderSize];
        inputStream.read(buffer, 0, baseHeaderSize);

        JsonObject baseHeaderValues = structParser.parseStruct(
                ReaderConfig.POINTERSIZE_UNKNOWN,
                config.getInitialCatalog(),
                headerDescriptor,
                buffer,
                true);

        SDNAHeader header = new SDNAHeader();
        header.setIdentifier(baseHeaderValues
                .get(SDNAHeader.FIELDID_IDENTIFIER).getAsString());
        char pointerSize = baseHeaderValues
                .get(SDNAHeader.FIELDID_POINTERSIZE).getAsCharacter();
        header.setPointerSize((SDNAHeader.CODE_POINTERSIZE_8 == pointerSize) ?
                (short) 8 : (short) 4);
        char endianness = baseHeaderValues
                .get(SDNAHeader.FIELDID_ENDIANNESS).getAsCharacter();
        header.setLittleEndian(SDNAHeader.CODE_LITTLE_ENDIAN == endianness);

        SDNAStructDescriptor extendedHeaderDescriptor = config.getExtendedHeaderDescriptor();
        if (SDNAStructDescriptor.VALUE_NUMBER_UNKNOWN ==
                extendedHeaderDescriptor.getSize()) {
            System.out.println("Calculating size!");
            extendedHeaderDescriptor.calculateSize(
                    config.getInitialCatalog(),
                    header.getPointerSize());
        }

        Integer extendedHeaderSize = extendedHeaderDescriptor.getSize();
        if ((0 == extendedHeaderSize) ||
                (SDNAStructDescriptor.VALUE_NUMBER_UNKNOWN ==
                        extendedHeaderDescriptor.getSize())) {
            return header;
        }

        buffer = new byte[extendedHeaderSize];
        inputStream.read(buffer, 0, extendedHeaderSize);

        JsonObject extendedHeaderValues = structParser.parseStruct(
                header.getPointerSize(),
                config.getInitialCatalog(),
                extendedHeaderDescriptor,
                buffer,
                header.isLittleEndian());
        header.setExtendedHeaderValues(extendedHeaderValues);

        return header;
    }
}

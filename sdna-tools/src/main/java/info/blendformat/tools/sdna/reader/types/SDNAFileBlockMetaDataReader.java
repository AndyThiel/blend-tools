package info.blendformat.tools.sdna.reader.types;

import com.google.gson.JsonObject;
import info.blendformat.tools.sdna.model.SDNABlockMetaData;
import info.blendformat.tools.sdna.model.SDNAHeader;
import info.blendformat.tools.sdna.model.SDNAStructDescriptor;
import info.blendformat.tools.sdna.parser.StructParser;
import info.blendformat.tools.sdna.reader.ReaderConfig;

import java.io.BufferedInputStream;
import java.io.IOException;

public class SDNAFileBlockMetaDataReader {

    private StructParser structParser = new StructParser();

    public SDNABlockMetaData readMetaData(ReaderConfig config,
                                          SDNAHeader header,
                                          BufferedInputStream inputStream) throws IOException {

        Short pointerSize = header.getPointerSize();
        boolean littleEndian = header.isLittleEndian();

        SDNAStructDescriptor baseMetaDataDescriptor = config
                .getBaseMetaDataDescriptor(pointerSize);

        byte[] buffer = new byte[baseMetaDataDescriptor.getSize()];
        int read = inputStream.read(buffer, 0, buffer.length);

        if (0 >= read) {
            return null;
        } else if (read != baseMetaDataDescriptor.getSize()) {
            throw new IllegalStateException();
        }

        JsonObject baseMetaDataValues = structParser.parseStruct(
                pointerSize,
                config.getInitialCatalog(),
                baseMetaDataDescriptor,
                buffer,
                littleEndian);

        SDNABlockMetaData metaData = new SDNABlockMetaData();
        metaData.setCode(baseMetaDataValues
                .get(SDNABlockMetaData.FIELDID_CODE).getAsString());
        metaData.setSize(baseMetaDataValues
                .get(SDNABlockMetaData.FIELDID_SIZE).getAsInt());
        metaData.setAddress(baseMetaDataValues
                .get(SDNABlockMetaData.FIELDID_ADDRESS).getAsLong());
        metaData.setSdnaIndex(baseMetaDataValues
                .get(SDNABlockMetaData.FIELDID_SDNAINDEX).getAsInt());
        metaData.setCount(baseMetaDataValues
                .get(SDNABlockMetaData.FIELDID_COUNT).getAsInt());

//        SDNAStructDescriptor extendedMetaDataDescriptor = config
//                .getBaseMetaDataDescriptor(pointerSize);
//
//        buffer = new byte[extendedMetaDataDescriptor.getSize()];
//        inputStream.read(buffer, 0, buffer.length);
//        JsonObject extendedMetaDataValues = structParser.parseStruct(
//                pointerSize,
//                config.getInitialCatalog(),
//                extendedMetaDataDescriptor,
//                buffer,
//                littleEndian);
//
//        metaData.setExtendedMetaDataValues(extendedMetaDataValues);

        return metaData;
    }
}

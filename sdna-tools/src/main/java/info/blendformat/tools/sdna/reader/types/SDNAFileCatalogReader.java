package info.blendformat.tools.sdna.reader.types;

import info.blendformat.tools.sdna.model.*;
import info.blendformat.tools.sdna.parser.NumberValueParser;
import info.blendformat.tools.sdna.parser.StringValueParser;
import info.blendformat.tools.sdna.reader.ReaderConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SDNAFileCatalogReader {

    public static final String LABEL_SDNA = "SDNA";
    public static final String LABEL_NAME = "NAME";
    public static final String LABEL_TYPE = "TYPE";
    public static final String LABEL_LENGTH = "TLEN";
    public static final String LABEL_STRUCT = "STRC";

    private StringValueParser stringValueParser
            = new StringValueParser();
    private NumberValueParser numberValueParser
            = new NumberValueParser();

    public SDNACatalog readCatalog(ReaderConfig config,
                                   SDNAHeader header,
                                   SDNABlockMetaData metaData,
                                   byte[] data) throws IOException {

        boolean littleEndian = header.isLittleEndian();
        int currentOffset = 0;

        SDNACatalog sdnaCatalog = new SDNACatalog();

        String labelSDNA = stringValueParser.parseString(
                Arrays.copyOfRange(data, currentOffset, (currentOffset + 4)));
        currentOffset += 4;
        checkLabel(LABEL_SDNA, labelSDNA);

        ArrayList<String> codes = new ArrayList<>();
        currentOffset = readCodes(data, currentOffset, codes, littleEndian);

        ArrayList<String> types = new ArrayList<>();
        currentOffset = readTypes(data, currentOffset, types, littleEndian);

        currentOffset = readLengths(data, currentOffset, types,
                sdnaCatalog,
                littleEndian);

        readStructDescriptors(data, currentOffset,
                codes, types, sdnaCatalog,
                littleEndian);

        return sdnaCatalog;
    }

    private int readCodes(byte[] buffer,
                          int offset,
                          ArrayList<String> codes,
                          boolean littleEndian) {

        return checkLabelAndFetchStrings(
                buffer, offset, codes,
                littleEndian,
                LABEL_NAME);
    }


    private int readTypes(byte[] buffer,
                          int offset,
                          ArrayList<String> types,
                          boolean littleEndian) {

        return checkLabelAndFetchStrings(
                buffer, offset, types,
                littleEndian,
                LABEL_TYPE);
    }

    private int readLengths(byte[] buffer,
                            int offset,
                            ArrayList<String> types,
                            SDNACatalog sdnaCatalog,
                            boolean littleEndian) {

        int currentOffset = offset;

        String labelLength = stringValueParser.parseString(
                Arrays.copyOfRange(buffer, currentOffset, (currentOffset + 4)));
        checkLabel(LABEL_LENGTH, labelLength);
        currentOffset += 4;

        for (int index = 0; index < types.size(); index++) {
            short typeSize = numberValueParser.readShort(
                    Arrays.copyOfRange(buffer, currentOffset, (currentOffset + 2)),
                    littleEndian);
            currentOffset += 2;
            sdnaCatalog.registerSize(types.get(index), (int) typeSize);
        }

        // Expecting 0 character bytes
        while (0 == buffer[currentOffset]) {
            currentOffset++;
        }

        return currentOffset;
    }

    private void readStructDescriptors(byte[] buffer,
                                       int offset,
                                       ArrayList<String> codes,
                                       ArrayList<String> types,
                                       SDNACatalog sdnaCatalog,
                                       boolean littleEndian) {

        int currentOffset = offset;

        String labelStructs = stringValueParser.parseString(
                Arrays.copyOfRange(buffer, currentOffset, (currentOffset + 4)));
        checkLabel(LABEL_STRUCT, labelStructs);
        currentOffset += 4;

        int countStructures = numberValueParser.readInteger(
                Arrays.copyOfRange(buffer, currentOffset, (currentOffset + 4)),
                littleEndian);
        currentOffset += 4;

        for (int indexStruct = 0; indexStruct < countStructures; indexStruct++) {

            SDNAStructDescriptor descriptor = new SDNAStructDescriptor();

            Short structTypeIndex = numberValueParser.readShort(
                    Arrays.copyOfRange(buffer, currentOffset, (currentOffset + 2)),
                    littleEndian);
            currentOffset += 2;

            Short countFields = numberValueParser.readShort(
                    Arrays.copyOfRange(buffer, currentOffset, (currentOffset + 2)),
                    littleEndian);
            currentOffset += 2;

            String type = types.get(structTypeIndex);
            descriptor.setType(type);
            sdnaCatalog.registerStruct(indexStruct, type);

            ArrayList<SDNAFieldDescriptor> fieldDescriptors = new ArrayList<>();
            for (int indexField = 0; indexField < countFields; indexField++) {

                Short fieldTypeIndex = numberValueParser.readShort(
                        Arrays.copyOfRange(buffer, currentOffset, (currentOffset + 2)),
                        littleEndian);
                currentOffset += 2;

                Short fieldNameIndex = numberValueParser.readShort(
                        Arrays.copyOfRange(buffer, currentOffset, (currentOffset + 2)),
                        littleEndian);
                currentOffset += 2;

                String fieldStructType = types.get(fieldTypeIndex);
                Integer fieldStructTypeSize = sdnaCatalog.getSize(type);
                String fieldCode = codes.get(fieldNameIndex);

                SDNAFieldDescriptor fieldDescriptor = new SDNAFieldDescriptor();
                fieldDescriptor.setStructType(fieldStructType);
                fieldDescriptor.setCode(fieldCode);
                fieldDescriptor.setFieldType(FieldType.getByFieldMetaData(
                        fieldDescriptor.getStructType(),
                        fieldStructTypeSize,
                        fieldCode));

                fieldDescriptors.add(fieldDescriptor);
            }
            descriptor.setFieldDescriptors(fieldDescriptors);

            sdnaCatalog.registerStructDescriptor(type, descriptor);
        }
    }

    private int checkLabelAndFetchStrings(
            byte[] buffer, int offset, ArrayList<String> codes,
            boolean littleEndian,
            String labelName) throws IllegalStateException {
        int currentOffset = offset;

        String labelNames = stringValueParser.parseString(
                Arrays.copyOfRange(buffer, currentOffset, (currentOffset + 4)));
        checkLabel(labelName, labelNames);
        currentOffset += 4;

        int countCodes = numberValueParser.readInteger(
                Arrays.copyOfRange(buffer, currentOffset, (currentOffset + 4)),
                littleEndian);
        currentOffset += 4;

        currentOffset = readNullTerminatedStrings(buffer, currentOffset, codes, countCodes);

        return currentOffset;
    }

    private void checkLabel(String expected,
                            String actual) throws IllegalStateException {
        if (!expected.equals(actual)) {
            throw new IllegalStateException(new StringBuilder()
                    .append("Expected ").append(expected).append(" but was ").append(actual)
                    .toString());
        }
    }

    private int readNullTerminatedStrings(
            byte[] buffer,
            int offset,
            ArrayList<String> container,
            int nrOfStrings) {

        int currentOffset = offset;
        for (int index = 0; index < nrOfStrings; index++) {
            StringBuilder builder = new StringBuilder();
            while (true) {
                char charValue = (char) buffer[currentOffset];
                currentOffset++;
                if (0 == charValue) {
                    String currentValue = builder.toString();
                    container.add(currentValue);
                    break;
                }
                builder.append(charValue);
            }
        }

        while (0 == buffer[currentOffset]) {
            currentOffset++;
        }

        return currentOffset;
    }
}

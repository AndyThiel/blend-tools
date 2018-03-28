package info.blendformat.tools.sdna.reader;

import info.blendformat.tools.sdna.defaults.DefaultCatalogPrimitives;
import info.blendformat.tools.sdna.defaults.DefaultExtendedHeaderDescriptor;
import info.blendformat.tools.sdna.model.*;

import java.text.MessageFormat;
import java.util.ArrayList;

public class ReaderConfigDefault implements ReaderConfig {

    public static final SDNACatalog DEFAULT_CATALOG
            = new DefaultCatalogPrimitives();

    private short identifierSize = 7;

    private short metadataCodeSize = 4;

    private SDNAStructDescriptor extendedHeaderDescriptor
            = new DefaultExtendedHeaderDescriptor();

    private SDNAStructDescriptor extendedMetaDataDescriptor
            = null;

    private SDNACatalog initialCatalog
            = DEFAULT_CATALOG;

    private String catalogCode = "DNA1";

    @Override
    public short getIdentifierSize() {
        return identifierSize;
    }

    @Override
    public void setIdentifierSize(short identifierSize) {
        this.identifierSize = identifierSize;
    }

    @Override
    public short getMetadataCodeSize() {
        return metadataCodeSize;
    }

    @Override
    public void setMetadataCodeSize(short metadataCodeSize) {
        this.metadataCodeSize = metadataCodeSize;
    }

    @Override
    public SDNAStructDescriptor getBaseHeaderDescriptor() {
        SDNAStructDescriptor headerDescriptor = new SDNAStructDescriptor();
        headerDescriptor.setType("HEAD");
        headerDescriptor.setSize(2 + identifierSize);

        ArrayList<SDNAFieldDescriptor> fieldDescriptors
                = new ArrayList<>();

        SDNAFieldDescriptor identifierDescriptor
                = new SDNAFieldDescriptor();
        identifierDescriptor.setFieldType(FieldType.STRING);
        identifierDescriptor.setCode("identifier[" + identifierSize + "]");
        identifierDescriptor.setStructType(DefaultCatalogPrimitives.PRIMITIVE_CHAR);
        fieldDescriptors.add(identifierDescriptor);

        SDNAFieldDescriptor pointerSizeDescriptor
                = new SDNAFieldDescriptor();
        pointerSizeDescriptor.setFieldType(FieldType.CHAR);
        pointerSizeDescriptor.setCode(SDNAHeader.FIELDID_POINTERSIZE);
        pointerSizeDescriptor.setStructType(DefaultCatalogPrimitives.PRIMITIVE_CHAR);
        fieldDescriptors.add(pointerSizeDescriptor);

        SDNAFieldDescriptor endiannessDescriptor
                = new SDNAFieldDescriptor();
        endiannessDescriptor.setFieldType(FieldType.CHAR);
        endiannessDescriptor.setCode(SDNAHeader.FIELDID_ENDIANNESS);
        endiannessDescriptor.setStructType(DefaultCatalogPrimitives.PRIMITIVE_CHAR);
        fieldDescriptors.add(endiannessDescriptor);

        headerDescriptor.setFieldDescriptors(fieldDescriptors);

        return headerDescriptor;
    }

    @Override
    public SDNAStructDescriptor getExtendedHeaderDescriptor() {
        return extendedHeaderDescriptor;
    }

    @Override
    public void setExtendedHeaderDescriptor(SDNAStructDescriptor extendedHeaderDescriptor) {
        this.extendedHeaderDescriptor = extendedHeaderDescriptor;
    }

    @Override
    public SDNAStructDescriptor getBaseMetaDataDescriptor(short pointerSize) {
        SDNAStructDescriptor metaDataDescriptor = new SDNAStructDescriptor();
        metaDataDescriptor.setType("META");
        metaDataDescriptor.setSize(12 +
                metadataCodeSize +
                pointerSize);

        ArrayList<SDNAFieldDescriptor> fieldDescriptors
                = new ArrayList<>();

        SDNAFieldDescriptor descriptorCode = new SDNAFieldDescriptor();
        descriptorCode.setFieldType(FieldType.STRING);
        descriptorCode.setCode(MessageFormat.format("{0}[{1}]",
                SDNABlockMetaData.FIELDID_CODE,
                metadataCodeSize));
        descriptorCode.setStructType(DefaultCatalogPrimitives.PRIMITIVE_CHAR);
        fieldDescriptors.add(descriptorCode);

        SDNAFieldDescriptor descriptorSize = new SDNAFieldDescriptor();
        descriptorSize.setFieldType(FieldType.NUMBER);
        descriptorSize.setCode(SDNABlockMetaData.FIELDID_SIZE);
        descriptorSize.setStructType(DefaultCatalogPrimitives.PRIMITIVE_INT);
        fieldDescriptors.add(descriptorSize);

        SDNAFieldDescriptor descriptorAddress = new SDNAFieldDescriptor();
        descriptorAddress.setFieldType(FieldType.ADDRESS);
        descriptorAddress.setCode(SDNABlockMetaData.FIELDID_ADDRESS);
        descriptorAddress.setStructType(
                (8 == pointerSize) ?
                        DefaultCatalogPrimitives.PRIMITIVE_UINT64_T :
                        DefaultCatalogPrimitives.PRIMITIVE_INT);
        fieldDescriptors.add(descriptorAddress);

        SDNAFieldDescriptor descriptorSDNAIndex = new SDNAFieldDescriptor();
        descriptorSDNAIndex.setFieldType(FieldType.NUMBER);
        descriptorSDNAIndex.setCode(SDNABlockMetaData.FIELDID_SDNAINDEX);
        descriptorSDNAIndex.setStructType(DefaultCatalogPrimitives.PRIMITIVE_INT);
        fieldDescriptors.add(descriptorSDNAIndex);

        SDNAFieldDescriptor descriptorCount = new SDNAFieldDescriptor();
        descriptorCount.setFieldType(FieldType.NUMBER);
        descriptorCount.setCode(SDNABlockMetaData.FIELDID_COUNT);
        descriptorCount.setStructType(DefaultCatalogPrimitives.PRIMITIVE_INT);
        fieldDescriptors.add(descriptorCount);

        metaDataDescriptor.setFieldDescriptors(fieldDescriptors);

        return metaDataDescriptor;
    }

    @Override
    public SDNAStructDescriptor getExtendedMetaDataDescriptor() {
        return extendedMetaDataDescriptor;
    }

    @Override
    public void setExtendedMetaDataDescriptor(
            SDNAStructDescriptor extendedMetaDataDescriptor) {
        this.extendedMetaDataDescriptor = extendedMetaDataDescriptor;
    }

    @Override
    public SDNACatalog getInitialCatalog() {
        return initialCatalog;
    }

    @Override
    public void setInitialCatalog(SDNACatalog initialCatalog) {
        this.initialCatalog = initialCatalog;
    }

    @Override
    public String getCatalogCode() {
        return catalogCode;
    }

    @Override
    public void setCatalogCode(String catalogCode) {
        this.catalogCode = catalogCode;
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "ReaderConfigDefault'{'identifierSize={0}, metadataCodeSize={1}, extendedHeaderDescriptor={2}, extendedMetaDataDescriptor={3}, initialCatalog={4}, catalogCode=''{5}'''}'",
                identifierSize, metadataCodeSize,
                extendedHeaderDescriptor, extendedMetaDataDescriptor,
                initialCatalog, catalogCode);
    }
}

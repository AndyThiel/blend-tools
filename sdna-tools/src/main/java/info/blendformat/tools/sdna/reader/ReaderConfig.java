package info.blendformat.tools.sdna.reader;

import info.blendformat.tools.sdna.model.SDNACatalog;
import info.blendformat.tools.sdna.model.SDNAStructDescriptor;

import java.io.Serializable;

public interface ReaderConfig extends Serializable {

    short POINTERSIZE_UNKNOWN = -1;

    short getIdentifierSize();

    void setIdentifierSize(short identifierSize);

    short getMetadataCodeSize();

    void setMetadataCodeSize(short metadataCodeSize);

    SDNAStructDescriptor getBaseHeaderDescriptor();

    SDNAStructDescriptor getExtendedHeaderDescriptor();

    void setExtendedHeaderDescriptor(SDNAStructDescriptor extendedHeaderDescriptor);

    SDNAStructDescriptor getBaseMetaDataDescriptor(short pointerSize);

    SDNAStructDescriptor getExtendedMetaDataDescriptor();

    void setExtendedMetaDataDescriptor(SDNAStructDescriptor extendedMetaDataDescriptor);

    SDNACatalog getInitialCatalog();

    void setInitialCatalog(SDNACatalog initialCatalog);

    String getCatalogCode();

    void setCatalogCode(String catalogCode);
}

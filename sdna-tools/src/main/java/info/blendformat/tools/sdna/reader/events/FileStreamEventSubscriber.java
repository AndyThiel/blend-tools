package info.blendformat.tools.sdna.reader.events;

import info.blendformat.tools.sdna.model.SDNABlockMetaData;
import info.blendformat.tools.sdna.model.SDNACatalog;
import info.blendformat.tools.sdna.model.SDNAFileInfo;
import info.blendformat.tools.sdna.model.SDNAHeader;

public interface FileStreamEventSubscriber {

    void onReadProcessStarted();

    void onHeaderRead(SDNAHeader header);

    void onBlockMetaDataRead(SDNABlockMetaData metaData);

    void onBlockDataRead(SDNABlockMetaData metaData,
                         byte[] data);

    void onSDNACatalogRead(SDNACatalog catalog);

    void onReadProcessComplete(SDNAFileInfo fileInfo);
}

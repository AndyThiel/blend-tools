package info.blendformat.tools.sdna.reader.events;

import info.blendformat.tools.sdna.model.SDNABlockMetaData;

public interface FileBlockEventSubscriber {

    void onBlockMetaDataRead(SDNABlockMetaData metaData);

    void onBlockDataRead(SDNABlockMetaData metaData,
                         byte[] data);
}

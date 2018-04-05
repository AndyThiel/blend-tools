package info.blendformat.tools.sdna.reader.events;

import info.blendformat.tools.sdna.model.SDNABlockMetaData;

public interface FileBlockEventPublisher {

    void addSubscriber(FileBlockEventSubscriber subscriber);

    void removeSubscriber(FileBlockEventSubscriber subscriber);

    void fireBlockMetaDataRead(SDNABlockMetaData metaData);

    void fireBlockDataRead(SDNABlockMetaData metaData,
                           byte[] data);
}

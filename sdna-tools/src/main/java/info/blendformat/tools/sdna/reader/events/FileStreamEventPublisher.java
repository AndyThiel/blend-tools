package info.blendformat.tools.sdna.reader.events;

import info.blendformat.tools.sdna.model.SDNABlockMetaData;
import info.blendformat.tools.sdna.model.SDNACatalog;
import info.blendformat.tools.sdna.model.SDNAFileInfo;
import info.blendformat.tools.sdna.model.SDNAHeader;

public interface FileStreamEventPublisher {

    void addSubscriber(FileStreamEventSubscriber subscriber);

    void removeSubscriber(FileStreamEventSubscriber subscriber);

    void fireReadProcessStarted();

    void fireHeaderRead(SDNAHeader header);

    void fireBlockMetaDataRead(SDNABlockMetaData metaData);

    void fireBlockDataRead(SDNABlockMetaData metaData,
                           byte[] data);

    void fireSDNACatalogRead(SDNACatalog catalog);

    void fireReadProcessComplete(SDNAFileInfo fileInfo);
}

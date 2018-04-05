package info.blendformat.tools.sdna.reader.events;

import info.blendformat.tools.sdna.model.SDNACatalog;
import info.blendformat.tools.sdna.model.SDNAHeader;

public interface FileStreamEventPublisher extends FileBlockEventPublisher {

    void addSubscriber(FileStreamEventSubscriber subscriber);

    void removeSubscriber(FileStreamEventSubscriber subscriber);

    void fireReadProcessStarted();

    void fireHeaderRead(SDNAHeader header);

    void fireSDNACatalogRead(SDNACatalog catalog);

    void fireReadProcessComplete();
}

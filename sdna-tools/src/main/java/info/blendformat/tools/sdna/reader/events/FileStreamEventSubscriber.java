package info.blendformat.tools.sdna.reader.events;

import info.blendformat.tools.sdna.model.SDNACatalog;
import info.blendformat.tools.sdna.model.SDNAHeader;

public interface FileStreamEventSubscriber extends FileBlockEventSubscriber {

    void onReadProcessStarted();

    void onHeaderRead(SDNAHeader header);

    void onSDNACatalogRead(SDNACatalog catalog);

    void onReadProcessComplete();
}

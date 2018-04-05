package info.blendformat.tools.sdna.reader.events;

import info.blendformat.tools.sdna.model.SDNABlockMetaData;
import info.blendformat.tools.sdna.model.SDNACatalog;
import info.blendformat.tools.sdna.model.SDNAHeader;

import java.util.ArrayList;

public class FileStreamEventPublisherSDNACatalog implements FileStreamEventPublisher {

    private final ArrayList<FileStreamEventSubscriber> subscribers
            = new ArrayList<>();

    @Override
    public void addSubscriber(FileBlockEventSubscriber subscsriber) {
    }

    @Override
    public void removeSubscriber(FileBlockEventSubscriber subscsriber) {
    }

    @Override
    public void addSubscriber(FileStreamEventSubscriber subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void removeSubscriber(FileStreamEventSubscriber subscriber) {
        subscribers.remove(subscriber);
    }

    @Override
    public void fireReadProcessStarted() {
    }

    @Override
    public void fireHeaderRead(SDNAHeader header) {
    }

    @Override
    public void fireBlockMetaDataRead(SDNABlockMetaData metaData) {
    }

    @Override
    public void fireBlockDataRead(SDNABlockMetaData metaData, byte[] data) {
    }

    @Override
    public void fireSDNACatalogRead(SDNACatalog catalog) {
        for (FileStreamEventSubscriber subscriber : subscribers) {
            subscriber.onSDNACatalogRead(catalog);
        }
    }

    @Override
    public void fireReadProcessComplete() {
    }
}

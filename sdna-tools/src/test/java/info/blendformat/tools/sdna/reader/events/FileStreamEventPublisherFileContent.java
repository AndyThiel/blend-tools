package info.blendformat.tools.sdna.reader.events;

import info.blendformat.tools.sdna.model.SDNABlockMetaData;
import info.blendformat.tools.sdna.model.SDNACatalog;
import info.blendformat.tools.sdna.model.SDNAHeader;

import java.util.ArrayList;

public class FileStreamEventPublisherFileContent implements FileStreamEventPublisher {

    private final ArrayList<FileBlockEventSubscriber> blockSubscribers
            = new ArrayList<>();

    private final ArrayList<FileStreamEventSubscriber> subscribers
            = new ArrayList<>();

    @Override
    public void addSubscriber(FileBlockEventSubscriber subscsriber) {
        blockSubscribers.add(subscsriber);
    }

    @Override
    public void removeSubscriber(FileBlockEventSubscriber subscsriber) {
        blockSubscribers.remove(subscsriber);
    }

    @Override
    public void addSubscriber(FileStreamEventSubscriber subscsriber) {
        subscribers.add(subscsriber);
    }

    @Override
    public void removeSubscriber(FileStreamEventSubscriber subscsriber) {
        subscribers.remove(subscsriber);
    }

    @Override
    public void fireReadProcessStarted() {
        for (FileStreamEventSubscriber subscriber : subscribers) {
            subscriber.onReadProcessStarted();
        }
    }

    @Override
    public void fireHeaderRead(SDNAHeader header) {
        for (FileStreamEventSubscriber subscriber : subscribers) {
            subscriber.onHeaderRead(header);
        }
    }

    @Override
    public void fireBlockMetaDataRead(SDNABlockMetaData metaData) {
        for (FileBlockEventSubscriber subscriber : blockSubscribers) {
            subscriber.onBlockMetaDataRead(metaData);
        }
        for (FileStreamEventSubscriber subscriber : subscribers) {
            subscriber.onBlockMetaDataRead(metaData);
        }
    }

    @Override
    public void fireBlockDataRead(SDNABlockMetaData metaData, byte[] data) {
        for (FileBlockEventSubscriber subscriber : blockSubscribers) {
            subscriber.onBlockDataRead(metaData, data);
        }
        for (FileStreamEventSubscriber subscriber : subscribers) {
            subscriber.onBlockDataRead(metaData, data);
        }
    }

    @Override
    public void fireSDNACatalogRead(SDNACatalog catalog) {
        for (FileStreamEventSubscriber subscriber : subscribers) {
            subscriber.onSDNACatalogRead(catalog);
        }
    }

    @Override
    public void fireReadProcessComplete() {
        for (FileStreamEventSubscriber subscriber : subscribers) {
            subscriber.onReadProcessComplete();
        }
    }
}

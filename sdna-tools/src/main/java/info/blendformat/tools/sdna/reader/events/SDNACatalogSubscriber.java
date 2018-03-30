package info.blendformat.tools.sdna.reader.events;

import info.blendformat.tools.sdna.model.SDNABlockMetaData;
import info.blendformat.tools.sdna.model.SDNACatalog;
import info.blendformat.tools.sdna.model.SDNAHeader;

public class SDNACatalogSubscriber implements FileStreamEventSubscriber {

    private SDNACatalog catalog = null;

    public SDNACatalog getCatalog() {
        return catalog;
    }

    @Override
    public void onSDNACatalogRead(SDNACatalog catalog) {
        this.catalog = catalog;
    }

    @Override
    public void onReadProcessStarted() {
    }

    @Override
    public void onHeaderRead(SDNAHeader header) {
    }

    @Override
    public void onBlockMetaDataRead(SDNABlockMetaData metaData) {
    }

    @Override
    public void onBlockDataRead(SDNABlockMetaData metaData, byte[] data) {
    }

    @Override
    public void onReadProcessComplete() {
    }
}

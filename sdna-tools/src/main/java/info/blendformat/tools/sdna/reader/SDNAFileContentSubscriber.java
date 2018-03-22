package info.blendformat.tools.sdna.reader;

import info.blendformat.tools.sdna.model.*;

public class SDNAFileContentSubscriber implements FileStreamEventSubscriber {

    private SDNAFileContent fileContent = new SDNAFileContent();

    public SDNAFileContent getFileContent() {
        return fileContent;
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
    public void onSDNACatalogRead(SDNACatalog catalog) {

    }

    @Override
    public void onReadProcessComplete(SDNAFileInfo fileInfo) {
    }
}

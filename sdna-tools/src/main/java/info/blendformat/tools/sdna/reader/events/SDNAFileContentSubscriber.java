package info.blendformat.tools.sdna.reader.events;

import info.blendformat.tools.sdna.model.*;

public class SDNAFileContentSubscriber implements FileStreamEventSubscriber {

    private SDNAFileContent fileContent = null;

    public SDNAFileContent getFileContent() {
        return fileContent;
    }

    @Override
    public void onReadProcessStarted() {
        fileContent = new SDNAFileContent();
    }

    @Override
    public void onHeaderRead(SDNAHeader header) {
        fileContent.setHeader(header);
    }

    @Override
    public void onBlockMetaDataRead(SDNABlockMetaData metaData) {

    }

    @Override
    public void onBlockDataRead(SDNABlockMetaData metaData, byte[] data) {

    }

    @Override
    public void onSDNACatalogRead(SDNACatalog catalog) {
        fileContent.setCatalog(catalog);
    }

    @Override
    public void onReadProcessComplete(SDNAFileInfo fileInfo) {
    }
}

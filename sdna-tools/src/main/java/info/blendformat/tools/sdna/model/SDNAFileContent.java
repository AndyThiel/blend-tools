package info.blendformat.tools.sdna.model;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;

public class SDNAFileContent implements Serializable {

    private SDNAHeader header;
    private SDNACatalog catalog;

    private ArrayList<SDNABlockMetaData> metaDataList
            = new ArrayList<>();
    private ArrayList<byte[]> dataList
            = new ArrayList<>();

    public SDNAHeader getHeader() {
        return header;
    }

    public void setHeader(SDNAHeader header) {
        this.header = header;
    }

    public SDNACatalog getCatalog() {
        return catalog;
    }

    public void setCatalog(SDNACatalog catalog) {
        this.catalog = catalog;
    }

    public ArrayList<SDNABlockMetaData> getMetaDataList() {
        return metaDataList;
    }

    public ArrayList<byte[]> getDataList() {
        return dataList;
    }

    public void registerDataBlock(SDNABlockMetaData metaData,
                                  byte[] data) {
        metaDataList.add(metaData);
        dataList.add(data);
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "SDNAFileContent'{'header={0}, catalog={1}, metaDataList={2}, dataList={3}'}'",
                header, catalog, metaDataList, dataList);
    }
}

package info.blendformat.tools.sdna.model;

import java.io.Serializable;

public class SDNAFileContent implements Serializable {

    private SDNAHeader header;
    private SDNACatalog catalog;

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

    @Override
    public String toString() {
        return String.format("SDNAFileContent{header=%scatalog=%s}",
                header,
                catalog);
    }
}

package info.blendformat.tools.sdna.model;

import java.io.Serializable;

public class SDNAFileContent implements Serializable {

    private SDNACatalog catalog;

    public SDNACatalog getCatalog() {
        return catalog;
    }

    public void setCatalog(SDNACatalog catalog) {
        this.catalog = catalog;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("SDNAFileContent{")
                .append("catalog=").append(catalog)
                .append('}').toString();
    }
}

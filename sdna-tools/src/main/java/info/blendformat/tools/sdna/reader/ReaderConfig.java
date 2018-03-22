package info.blendformat.tools.sdna.reader;

import java.io.Serializable;

public class ReaderConfig implements Serializable {

    private int sizeIdentifier = 7;

    public int getSizeIdentifier() {
        return sizeIdentifier;
    }

    public void setSizeIdentifier(int sizeIdentifier) {
        this.sizeIdentifier = sizeIdentifier;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("ReaderConfig{")
                .append("sizeIdentifier=").append(sizeIdentifier)
                .append('}').toString();
    }
}

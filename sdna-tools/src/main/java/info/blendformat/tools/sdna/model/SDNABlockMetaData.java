package info.blendformat.tools.sdna.model;

import java.io.Serializable;

public class SDNABlockMetaData implements Serializable {

    private String code;
    private int size;
    private long address;
    private int sdnaIndex;
    private int count;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setAddress(long address) {
        this.address = address;
    }

    public long getAddress() {
        return address;
    }

    public void setSdnaIndex(int sdnaIndex) {
        this.sdnaIndex = sdnaIndex;
    }

    public int getSdnaIndex() {
        return sdnaIndex;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return String.format("SDNABlockMetaData{code='%s', size=%d, address=%d, sdnaIndex=%d, count=%d}",
                code,
                size,
                address,
                sdnaIndex,
                count);
    }
}

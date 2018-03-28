package info.blendformat.tools.sdna.model;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.text.MessageFormat;

public class SDNABlockMetaData implements Serializable {

    public static final String FIELDID_CODE = "code";
    public static final String FIELDID_SIZE = "size";
    public static final String FIELDID_ADDRESS = "address";
    public static final String FIELDID_SDNAINDEX = "sdnaIndex";
    public static final String FIELDID_COUNT = "count";

    private String code;
    private int size;
    private long address;
    private int sdnaIndex;
    private int count;

    private JsonObject extendedMetaDataValues;

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

    public JsonObject getExtendedMetaDataValues() {
        return extendedMetaDataValues;
    }

    public void setExtendedMetaDataValues(JsonObject extendedMetaDataValues) {
        this.extendedMetaDataValues = extendedMetaDataValues;
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "SDNABlockMetaData'{'code=''{0}'', size={1}, address={2}, sdnaIndex={3}, count={4}, extendedMetaDataValues={5}'}'",
                code, size, address, sdnaIndex, count,
                extendedMetaDataValues);
    }
}

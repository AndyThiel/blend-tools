package info.blendformat.tools.sdna;

import com.google.gson.JsonObject;
import info.blendformat.tools.sdna.model.SDNABlockMetaData;
import info.blendformat.tools.sdna.model.SDNACatalog;
import info.blendformat.tools.sdna.model.SDNAFileContent;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SDNAFileConverter {

    private LinkedHashMap<Long, JsonObject> addressObjectMap
            = new LinkedHashMap<>();

    private SDNAParser parser = new SDNAParser();

    public void convertFileContent(SDNAFileContent content) {

        SDNACatalog catalog = content.getCatalog();
        ArrayList<SDNABlockMetaData> metaDataList = content.getMetaDataList();
        ArrayList<byte[]> dataList = content.getDataList();

        assert metaDataList.size() == dataList.size();

        int index = 0;
        for (SDNABlockMetaData metaData : metaDataList) {
            byte[] data = dataList.get(index);
            JsonObject object = parser.parseSDNAStructure(catalog,
                    metaData,
                    data);
            addressObjectMap.put(
                    metaData.getAddress(),
                    object);
            index++;
        }
    }

    public ArrayList<Long> getAddresses() {
        return new ArrayList<>(
                addressObjectMap.keySet());
    }

    public ArrayList<JsonObject> getObjects() {
        return new ArrayList<>(
                addressObjectMap.values());
    }

    public JsonObject get(Long address) {
        return addressObjectMap.get(address);
    }
}

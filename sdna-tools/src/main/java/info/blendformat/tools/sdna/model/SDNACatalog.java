package info.blendformat.tools.sdna.model;

import java.io.Serializable;
import java.util.HashMap;

public class SDNACatalog implements Serializable {

    private final HashMap<Integer, String> indexStructMap = new HashMap<>();

    private final HashMap<String, Integer> typeSizeMap = new HashMap<>();

    private final HashMap<String, SDNAStructDescriptor> typeStructDescriptorMap
            = new HashMap<>();

    public String getStruct(Integer index) {
        return indexStructMap.get(index);
    }

    public Integer getSize(String type) {
        return typeSizeMap.get(type);
    }

    public SDNAStructDescriptor getStructDescriptor(String type) {
        return typeStructDescriptorMap.get(type);
    }

    public String registerStruct(Integer index, String struct) {
        return indexStructMap.put(index, struct);
    }

    public Integer registerSize(String type, Integer size) {
        return typeSizeMap.put(type, size);
    }

    public SDNAStructDescriptor registerStructDescriptor(
            String type,
            SDNAStructDescriptor structDescriptor) {
        return typeStructDescriptorMap.put(type, structDescriptor);
    }
}

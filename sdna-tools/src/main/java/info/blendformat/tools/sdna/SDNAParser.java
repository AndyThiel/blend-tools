package info.blendformat.tools.sdna;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import info.blendformat.tools.sdna.model.SDNABlockMetaData;
import info.blendformat.tools.sdna.model.SDNACatalog;

public class SDNAParser {

    public JsonArray parseSDNAStructures(SDNACatalog catalog,
                                         SDNABlockMetaData metaData,
                                         byte[] buffer) {
        return new JsonArray();
    }

    public JsonObject parseSDNAStructure(SDNACatalog catalog,
                                         SDNABlockMetaData metaData,
                                         byte[] buffer) {
        return new JsonObject();
    }
}

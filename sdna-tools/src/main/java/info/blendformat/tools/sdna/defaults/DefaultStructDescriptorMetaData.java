package info.blendformat.tools.sdna.defaults;

import info.blendformat.tools.sdna.model.SDNAFieldDescriptor;
import info.blendformat.tools.sdna.model.SDNAStructDescriptor;

import java.util.ArrayList;

public class DefaultStructDescriptorMetaData extends SDNAStructDescriptor {

    public DefaultStructDescriptorMetaData(String typeCode) {
        super.setType(typeCode);

        ArrayList<SDNAFieldDescriptor> fieldDescriptors
                = new ArrayList<>();

        super.setFieldDescriptors(fieldDescriptors);
    }
}

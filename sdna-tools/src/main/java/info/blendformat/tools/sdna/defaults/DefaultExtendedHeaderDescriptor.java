package info.blendformat.tools.sdna.defaults;

import info.blendformat.tools.sdna.model.FieldType;
import info.blendformat.tools.sdna.model.SDNAFieldDescriptor;
import info.blendformat.tools.sdna.model.SDNAStructDescriptor;

import java.util.ArrayList;

public class DefaultExtendedHeaderDescriptor extends SDNAStructDescriptor {

    public DefaultExtendedHeaderDescriptor() {
        setType("H++");
        ArrayList<SDNAFieldDescriptor> fieldDescriptors
                = new ArrayList<>();

        SDNAFieldDescriptor versionDescriptor
                = new SDNAFieldDescriptor();
        versionDescriptor.setFieldType(FieldType.STRING);
        versionDescriptor.setCode("version[3]");
        versionDescriptor.setStructType(DefaultCatalogPrimitives.PRIMITIVE_CHAR);
        fieldDescriptors.add(versionDescriptor);

        setFieldDescriptors(fieldDescriptors);
        setSize(3);
    }
}

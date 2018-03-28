package info.blendformat.tools.sdna.model;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;

public class SDNAStructDescriptor implements Serializable {

    public static final int VALUE_NUMBER_UNKNOWN = -1;
    public static final String VALUE_TEXT_UNKNOWN = "?";

    private String type = VALUE_TEXT_UNKNOWN;

    private Integer size = VALUE_NUMBER_UNKNOWN;

    private ArrayList<SDNAFieldDescriptor> fieldDescriptors
            = new ArrayList<>();

    public void calculateSize(SDNACatalog catalog,
                              Short pointerSize) {

        int currentSize = 0;
        for (SDNAFieldDescriptor currentFieldDescriptor : fieldDescriptors) {

            String structType = currentFieldDescriptor.getStructType();
            SDNAStructDescriptor fieldStructDescriptor =
                    catalog.getStructDescriptor(structType);

            FieldType fieldType = currentFieldDescriptor.getFieldType();
            Integer fieldStructSize = fieldType.getSize(fieldStructDescriptor,
                    pointerSize,
                    catalog.getSize(structType),
                    currentFieldDescriptor.getCode());

            currentSize += (fieldStructSize);
        }
        this.size = currentSize;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public ArrayList<SDNAFieldDescriptor> getFieldDescriptors() {
        return fieldDescriptors;
    }

    public void setFieldDescriptors(ArrayList<SDNAFieldDescriptor> fieldDescriptors) {
        this.fieldDescriptors = fieldDescriptors;
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "SDNAStructDescriptor'{'type=''{0}'', size={1}, fieldDescriptors={2}'}'",
                type,
                size,
                fieldDescriptors);
    }
}

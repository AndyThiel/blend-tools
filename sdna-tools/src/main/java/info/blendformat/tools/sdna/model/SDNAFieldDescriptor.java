package info.blendformat.tools.sdna.model;

import java.io.Serializable;
import java.text.MessageFormat;

public class SDNAFieldDescriptor implements Serializable {

    private FieldType fieldType;

    private String code;

    private String structType;

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStructType() {
        return structType;
    }

    public void setStructType(String structType) {
        this.structType = structType;
    }

    @Override
    public String toString() {
        return MessageFormat.format(
                "SDNAFieldDescriptor'{'fieldType={0}, code=''{1}'', structType=''{2}'''}'",
                fieldType,
                code,
                structType);
    }
}

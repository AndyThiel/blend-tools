package info.blendformat.tools.sdna.api.model;

import info.blendformat.tools.sdna.model.FieldType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name = "SDNAStructField", uniqueConstraints = @UniqueConstraint(
        columnNames = {"type_id", "name"}))
public class SDNAStructField {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @ManyToOne
    private SDNACatalogType type;

    @Null
    @ManyToOne
    private SDNACatalogType referenceType;

    @NotNull
    private String name;

    @NotNull
    private Short index;

    @NotNull
    private Integer size;

    @NotNull
    @Enumerated(EnumType.STRING)
    private FieldType fieldType;

    @NotNull
    private String code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SDNACatalogType getType() {
        return type;
    }

    public void setType(SDNACatalogType type) {
        this.type = type;
    }

    public SDNACatalogType getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(SDNACatalogType referenceType) {
        this.referenceType = referenceType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getIndex() {
        return index;
    }

    public void setIndex(Short index) {
        this.index = index;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

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

    @Override
    public String toString() {
        return String.format(
                "SDNAStructField{id=%d, type=%s, referenceType=%s, name='%s', index=%s, size=%d, fieldType=%s, code='%s'}",
                id, type, referenceType, name, index, size, fieldType, code);
    }
}

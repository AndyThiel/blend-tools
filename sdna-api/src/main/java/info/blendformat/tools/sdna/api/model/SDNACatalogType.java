package info.blendformat.tools.sdna.api.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@XmlRootElement
@Table(name = "SDNACatalogType", uniqueConstraints = @UniqueConstraint(
        columnNames = {"file_id", "name"}))
public class SDNACatalogType implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @ManyToOne
    private SDNAFile file;

    @NotNull
    private String name;

    @NotNull
    private Integer size;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SDNAFile getFile() {
        return file;
    }

    public void setFile(SDNAFile file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return String.format("SDNACatalogType{id=%d, file=%s, name='%s', size=%d}",
                id, file, name, size);
    }
}

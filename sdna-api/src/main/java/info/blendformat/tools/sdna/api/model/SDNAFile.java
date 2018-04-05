package info.blendformat.tools.sdna.api.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@XmlRootElement
@Table(name = "SDNAFile", uniqueConstraints = @UniqueConstraint(
        columnNames = {"project_id", "name"}))
public class SDNAFile implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @ManyToOne
    private SDNAProject project;

    @NotNull
    @Size(min = 3, max = 256, message = "3-256 characters")
    private String name;

    @NotNull
    private Boolean littleEndian;

    @NotNull
    private Short pointerSize;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SDNAProject getProject() {
        return project;
    }

    public void setProject(SDNAProject project) {
        this.project = project;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getLittleEndian() {
        return littleEndian;
    }

    public void setLittleEndian(Boolean littleEndian) {
        this.littleEndian = littleEndian;
    }

    public Short getPointerSize() {
        return pointerSize;
    }

    public void setPointerSize(Short pointerSize) {
        this.pointerSize = pointerSize;
    }

    @Override
    public String toString() {
        return String.format("SDNAFile{id=%d, project=%s, name='%s', littleEndian=%s, pointerSize=%s}",
                id, project, name, littleEndian, pointerSize);
    }
}

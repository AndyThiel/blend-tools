package info.blendformat.tools.sdna.api.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@XmlRootElement
@Table(name = "SDNAProject", uniqueConstraints = @UniqueConstraint(
        columnNames = {"name"}))
public class SDNAProject implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 3, max = 256, message = "3-256 characters")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("SDNAProject{id=%d, name='%s'}",
                id, name);
    }
}

package info.blendformat.tools.sdna.api.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@XmlRootElement
@Table(name = "SDNAFileBlock", uniqueConstraints = @UniqueConstraint(
        columnNames = {"file_id", "address"}))
public class SDNAFileBlock implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @ManyToOne
    private SDNAFile file;

    @NotNull
    private Long address;

    @NotNull
    @Size(min = 3, max = 32, message = "3-32 characters")
    private String code;

    @NotNull
    private Integer size;

    @Lob
    @Column
    private String content;

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

    public Long getAddress() {
        return address;
    }

    public void setAddress(Long address) {
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return String.format("SDNAFileBlock{id=%d, file=%s, address=%d, code='%s', size=%d}",
                id, file, address, code, size);
    }
}

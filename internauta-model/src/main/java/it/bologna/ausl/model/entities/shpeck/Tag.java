package it.bologna.ausl.model.entities.shpeck;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.bologna.ausl.model.entities.baborg.Pec;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Salo
 */
@Entity
@Table(name = "tags", catalog = "internauta", schema = "shpeck")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Tag implements Serializable {

    public static enum TagType {
        SYSTEM_INSERTABLE_DELETABLE,
        SYSTEM_INSERTABLE_NOT_DELETABLE,
        SYSTEM_NOT_INSERTABLE_DELETABLE,
        SYSTEM_NOT_INSERTABLE_NOT_DELETABLE,
        CUSTOM
    }

    public static enum SystemTagName {
        replied,
        assigned,
        replied_all,
        readdressed_in,
        readdressed_out,
        forwarded,
        annotated,
        in_error,
        registered,
        in_registration,
        technical_error
    }

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "name")
    private String name;

    @Size(max = 2147483647)
    @Column(name = "description")
    private String description;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "type")
    private String type;

    @JoinColumn(name = "id_pec", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Pec idPec;

    @Basic(optional = false)
    @NotNull
    @Column(name = "visible")
    private Boolean visible = false;

    @Basic(optional = false)
    @NotNull
    @Column(name = "first_level")
    private Boolean firstLevel = false;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTag", fetch = FetchType.LAZY)
    @JsonBackReference(value = "messageTagList")
    private List<MessageTag> messageTagList;
        
    @Version()
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime version;

    public LocalDateTime getVersion() {
        return version;
    }

    public void setVersion(LocalDateTime version) {
        this.version = version;
    }
 
    public Tag() {
    }

    public Tag(Integer id) {
        this.id = id;
    }

    public Tag(Integer id, String name, TagType type) {
        this.id = id;
        this.name = name;
        this.type = type.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TagType getType() {
        return TagType.valueOf(type);
    }

    public void setType(TagType type) {
        this.type = type.toString();
    }

    public Pec getIdPec() {
        return idPec;
    }

    public void setIdPec(Pec idPec) {
        this.idPec = idPec;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Boolean getFirstLevel() {
        return firstLevel;
    }

    public void setFirstLevel(Boolean firstLevel) {
        this.firstLevel = firstLevel;
    }

    public List<MessageTag> getMessageTagList() {
        return messageTagList;
    }

    public void setMessageTagList(List<MessageTag> messageTagList) {
        this.messageTagList = messageTagList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tag)) {
            return false;
        }
        Tag other = (Tag) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.bologna.ausl.model.entities.shpeck.Tag[ id=" + id + " ]";
    }

}

package it.bologna.ausl.model.entities.shpeck;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author spritz
 */
@Entity
@Table(name = "upload_queue", catalog = "internauta", schema = "shpeck")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UploadQueue implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
       
    @Basic(optional = false)
    @NotNull
    @Column(name = "uploaded")
    private Boolean uploaded = false;
    
    @Size(min = 1, max = 2147483647)
    @Column(name = "path", columnDefinition = "text")
    private String path;
    
    @Size(min = 1, max = 2147483647)
    @Column(name = "name", columnDefinition = "text")
    private String name;
    
    @Size(min = 1, max = 2147483647)
    @Column(name = "uuid", columnDefinition = "text")
    private String uuid;
    
    @JoinColumn(name = "id_raw_message", referencedColumnName = "id")
    // dovrebbe essere OneToOne, ma mettendolo Hibernate lo caricherebbe sempre nonostante il Lazy
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private RawMessage idRawMessage;
        
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
 
    public UploadQueue() {
    }

    public UploadQueue(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getUploaded() {
        return uploaded;
    }

    public void setUploaded(Boolean uploaded) {
        this.uploaded = uploaded;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public RawMessage getIdRawMessage() {
        return idRawMessage;
    }

    public void setIdRawMessage(RawMessage idRawMessage) {
        this.idRawMessage = idRawMessage;
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
        if (!(object instanceof UploadQueue)) {
            return false;
        }
        UploadQueue other = (UploadQueue) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.bologna.ausl.model.entities.shpeck.UploadQueue[ id=" + id + " ]";
    }
}

package it.bologna.ausl.model.entities.shpeck;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.bologna.ausl.model.entities.baborg.Pec;
import it.bologna.ausl.model.entities.configuration.Applicazione;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
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
@Table(name = "recepits", schema = "shpeck")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Recepit implements Serializable {

    public static enum RecepitType {
        ACCETTAZIONE,
        PREAVVISO_ERRORE_CONSEGNA,
        PRESA_IN_CARICO,
        NON_ACCETTAZIONE, 
        RILEVAZIONE_VIRUS,
        ERRORE_CONSEGNA,
        CONSEGNA
    }
    
    private static final long serialVersionUID = 1L;
    
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
//    @Id
    @OneToOne(optional = false, fetch = FetchType.LAZY)
//    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "id", referencedColumnName = "id")
    @MapsId
    private Message idMessage;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "recepit_type")
    private String recepitType;
        
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
 
    public Recepit() {
    }

    public Recepit(Integer id, String uuidMessage, Pec idPec, Applicazione idApplicazione, Message idRelated, String subject, String messageStatus, String inOut, LocalDateTime createTime, LocalDateTime updateTime, String messageType, Boolean isPec, Integer nAttachments, String uuidMongo, String mongoPath, String name, LocalDateTime receiveDate, RecepitType recepitType) {
        this.recepitType = recepitType.toString();
        this.idMessage = new Message(id, uuidMessage, idPec, idApplicazione, idRelated, subject, messageStatus, inOut, createTime, updateTime, messageType, isPec, nAttachments, uuidMongo, mongoPath, name, receiveDate);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Message getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(Message idMessage) {
        this.idMessage = idMessage;
    }

    public RecepitType getRecepitType() {
        return RecepitType.valueOf(recepitType);
    }

    public void setRecepitType(RecepitType recepitType) {
        this.recepitType = recepitType.toString();
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.idMessage.getId() != null ? this.idMessage.hashCode() : 0);
        return hash;
    }  

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Recepit other = (Recepit) obj;
        return Objects.equals(this.recepitType, other.recepitType);
    }

    @Override
    public String toString() {
        return "it.bologna.ausl.model.entities.shpeck.Recepit[ id=" + this.idMessage.getId() + " ]";
    }
    
}

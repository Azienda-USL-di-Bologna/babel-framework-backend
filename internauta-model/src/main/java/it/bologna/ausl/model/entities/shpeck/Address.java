package it.bologna.ausl.model.entities.shpeck;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Salo
 */
@Entity
@Table(name = "addresses", catalog = "internauta", schema = "shpeck")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Cacheable(false)
public class Address implements Serializable {

    private  static final long serialVersionUID = 1L;

    public static enum RecipientType {
        PEC, REGULAR_EMAIL, UNKNOWN
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "mail_address")
    private String mailAddress;
    
    @Column(name = "original_address")
    private String originalAddress;
    
    @Column(name = "recipient_type")
    private String recipientType;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAddress", fetch = FetchType.LAZY)
    @JsonBackReference(value = "messageAddressList")
    private List<MessageAddress> messageAddressList;
        
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
 
    public Address() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getOriginalAddress() {
        return originalAddress;
    }

    public void setOriginalAddress(String originalAddress) {
        this.originalAddress = originalAddress;
    }

    public RecipientType getRecipientType() {
        return RecipientType.valueOf(recipientType);
    }

    public void setRecipientType(RecipientType recipientType) {
        this.recipientType = recipientType.toString();
    }

    public List<MessageAddress> getMessageAddressList() {
        return messageAddressList;
    }

    public void setMessageAddressList(List<MessageAddress> messageAddressList) {
        this.messageAddressList = messageAddressList;
    }
 
    @Override
    public String toString() {
        return "it.bologna.ausl.model.entities.shpeck.Address[ id=" + id + " ]";
    }
}

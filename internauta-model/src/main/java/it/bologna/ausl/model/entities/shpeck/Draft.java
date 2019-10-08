package it.bologna.ausl.model.entities.shpeck;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.bologna.ausl.internauta.utils.jpa.tools.GenericArrayUserType;
import it.bologna.ausl.model.entities.baborg.Pec;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
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
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Giuseppe Russo <g.russo@nsi.it>
 */
@Entity
@Table(name = "drafts", schema = "shpeck")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Cacheable(false)
public class Draft implements Serializable {
    
    public static enum MessageRelatedType {
        REPLIED, REPLIED_ALL, FORWARDED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @JoinColumn(name = "id_pec", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Pec idPec;
    
    @Size(max = 2147483647)
    @Column(name = "subject")
    private String subject;
    
    @Column(name = "to_addresses", columnDefinition = "to_addresses[]")
    @Type(type = "array", parameters = @Parameter(name = "elements-type", value = GenericArrayUserType.TEXT_ELEMENT_TYPE))
    private String[] toAddresses;
    
    @Column(name = "cc_addresses", columnDefinition = "cc_addresses[]")
    @Type(type = "array", parameters = @Parameter(name = "elements-type", value = GenericArrayUserType.TEXT_ELEMENT_TYPE))
    private String[] ccAddresses;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "hidden_recipients")
    private Boolean hiddenRecipients = false;
    
    @Basic(optional = false)
    @Column(name = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createTime = LocalDateTime.now();
    
    @Basic(optional = false)
    @Column(name = "update_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updateTime = LocalDateTime.now();
    
    @Column(name = "attachments_number")
    private Integer attachmentsNumber;
    
    @Column(name = "attachments_name", columnDefinition = "attachments_name[]")
    @Type(type = "array", parameters = @Parameter(name = "elements-type", value = GenericArrayUserType.TEXT_ELEMENT_TYPE))
    private String[] attachmentsName;
    
    @Size(max = 2147483647)
    @Column(name = "body")
    private String body;
    
    @Column(name = "eml")
    private byte[] eml;
    
    @Basic(optional = true)
    @JoinColumn(name = "id_message_related", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Message idMessageRelated;
    
    @Column(name = "message_related_type")
    private String messageRelatedType;
          
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
   
    public Draft() {
    }

    public Draft(Integer id) {
        this.id = id;
    }

    public Draft(Integer id, Pec idPec, String subject, String[] toAddresses, String[] ccAddresses, 
            Boolean hiddenRecipients, LocalDateTime createTime, LocalDateTime updateTime, Integer attachmentsNumber, 
            String[] attachmentsName, String body, byte[] eml, Message idMessageRelated, String messageRelatedType) {
        this.id = id;
        this.idPec = idPec;
        this.subject = subject;
        this.toAddresses = toAddresses;
        this.ccAddresses = ccAddresses;
        this.hiddenRecipients = hiddenRecipients;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.attachmentsNumber = attachmentsNumber;
        this.attachmentsName = attachmentsName;
        this.body = body;
        this.eml = eml;
        this.idMessageRelated = idMessageRelated;
        this.messageRelatedType = messageRelatedType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Pec getIdPec() {
        return idPec;
    }

    public void setIdPec(Pec idPec) {
        this.idPec = idPec;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String[] getToAddresses() {
        return toAddresses;
    }

    public void setToAddresses(String[] toAddresses) {
        this.toAddresses = toAddresses;
    }

    public String[] getCcAddresses() {
        return ccAddresses;
    }

    public void setCcAddresses(String[] ccAddresses) {
        this.ccAddresses = ccAddresses;
    }

    public Boolean getHiddenRecipients() {
        return hiddenRecipients;
    }

    public void setHiddenRecipients(Boolean hiddenRecipients) {
        this.hiddenRecipients = hiddenRecipients;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getAttachmentsNumber() {
        return attachmentsNumber;
    }

    public void setAttachmentsNumber(Integer attachmentsNumber) {
        this.attachmentsNumber = attachmentsNumber;
    }

    public String[] getAttachmentsName() {
        return attachmentsName;
    }

    public void setAttachmentsName(String[] attachmentsName) {
        this.attachmentsName = attachmentsName;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public byte[] getEml() {
        return eml;
    }

    public void setEml(byte[] eml) {
        this.eml = eml;
    }

    public Message getIdMessageRelated() {
        return idMessageRelated;
    }

    public void setIdMessageRelated(Message idMessageRelated) {
        this.idMessageRelated = idMessageRelated;
    }

    public MessageRelatedType getMessageRelatedType() {
        if (this.messageRelatedType != null) {
            return MessageRelatedType.valueOf(messageRelatedType);
        } else {
            return null;
        }
    }

    public void setMessageRelatedType(MessageRelatedType messageRelatedType) {
        this.messageRelatedType = messageRelatedType.toString();
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
        if (!(object instanceof Draft)) {
            return false;
        }
        Draft other = (Draft) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.bologna.ausl.model.entities.shpeck.Draft[ id=" + id + " ]";
    }
    
}

package it.bologna.ausl.model.entities.shpeck;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.bologna.ausl.internauta.utils.jpa.tools.GenericArrayUserType;
import it.bologna.ausl.model.entities.baborg.Pec;
import it.bologna.ausl.model.entities.configuration.Applicazione;
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
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Salo
 */
@Entity
@Table(name = "outbox", catalog = "internauta", schema = "shpeck")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Outbox implements Serializable {

    private static final long serialVersionUID = 1L;

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

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "raw_data")
    private String rawData;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ignore")
    private Boolean ignore = false;

    @JoinColumn(name = "id_applicazione", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Applicazione idApplicazione;

    @Size(min = 1, max = 2147483647)
    @Column(name = "external_id")
    private String externalId;
        
    @Size(max = 2147483647)
    @Column(name = "subject")
    private String subject;

    @Column(name = "to_addresses", columnDefinition = "to_addresses[]")
    @Type(type = "array", parameters = @Parameter(name = "elements-type", value = GenericArrayUserType.TEXT_ELEMENT_TYPE))
    private String[] toAddresses;

    @Column(name = "cc_addresses", columnDefinition = "cc_addresses[]")
    @Type(type = "array", parameters = @Parameter(name = "elements-type", value = GenericArrayUserType.TEXT_ELEMENT_TYPE))
    private String[] ccAddresses;

    @Column(name = "hidden_recipients")
    private Boolean hiddenRecipients = false;

    @Column(name = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createTime = LocalDateTime.now();

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
    
    public Outbox() {
    }

    public Outbox(Integer id, Pec idPec, String rawData) {
        this.id = id;
        this.idPec = idPec;
        this.rawData = rawData;
    }

    public Outbox(Integer id, Pec idPec, Boolean ignore, String rawData, Applicazione idApplicazione,
            String externalId, LocalDateTime inserted, String subject, String[] toAddresses, String[] ccAddresses,
            Boolean hiddenRecipients, LocalDateTime createTime, LocalDateTime updateTime,
            Integer attachmentsNumber, String[] attachmentsName) {
        this.id = id;
        this.idPec = idPec;
        this.ignore = ignore;
        this.rawData = rawData;
        this.idApplicazione = idApplicazione;
        this.externalId = externalId;
        this.subject = subject;
        this.toAddresses = toAddresses;
        this.ccAddresses = ccAddresses;
        this.hiddenRecipients = hiddenRecipients;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.attachmentsNumber = attachmentsNumber;
        this.attachmentsName = attachmentsName;
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

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public Boolean getIgnore() {
        return ignore;
    }

    public void setIgnore(Boolean ignore) {
        this.ignore = ignore;
    }

    public Applicazione getIdApplicazione() {
        return idApplicazione;
    }

    public void setIdApplicazione(Applicazione idApplicazione) {
        this.idApplicazione = idApplicazione;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
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

    @Override
    public String toString() {
        return "Outbox{" + "id=" + id + ", idPec=" + idPec + ", rawData=" + rawData + ", ignore=" + ignore + ", idApplicazione=" + idApplicazione + ", externalId=" + externalId + '}';
    }
}

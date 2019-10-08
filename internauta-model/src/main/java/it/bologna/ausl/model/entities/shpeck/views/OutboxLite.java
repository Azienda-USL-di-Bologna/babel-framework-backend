/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.bologna.ausl.model.entities.shpeck.views;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.bologna.ausl.internauta.utils.jpa.tools.GenericArrayUserType;
import it.bologna.ausl.model.entities.baborg.Pec;
import it.bologna.ausl.model.entities.configuration.Applicazione;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Top
 */
@Entity
@Table(name = "outbox_lite", schema = "shpeck")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@Cacheable(false)
public class OutboxLite implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;

    @NotNull
    @JoinColumn(name = "id_pec", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Pec idPec;

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

    @Column(name = "inserted")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime inserted = LocalDateTime.now();

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

    public OutboxLite() {
    }

    public OutboxLite(Integer id) {
        this.id = id;
    }

    public OutboxLite(Integer id, Pec idPec, Boolean ignore, Applicazione idApplicazione,
            String externalId, LocalDateTime inserted, String subject, String[] toAddresses, String[] ccAddresses,
            Boolean hiddenRecipients, LocalDateTime createTime, LocalDateTime updateTime,
            Integer attachmentsNumber, String[] attachmentsName) {
        this.id = id;
        this.idPec = idPec;
        this.ignore = ignore;
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

    public LocalDateTime getInserted() {
        return inserted;
    }

    public void setInserted(LocalDateTime inserted) {
        this.inserted = inserted;
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

}

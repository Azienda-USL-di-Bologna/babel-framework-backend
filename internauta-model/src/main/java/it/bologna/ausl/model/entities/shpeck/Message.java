package it.bologna.ausl.model.entities.shpeck;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.bologna.ausl.model.entities.baborg.Pec;
import it.bologna.ausl.model.entities.configuration.Applicazione;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "messages", schema = "shpeck")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Cacheable(false)
public class Message implements Serializable {

    public static enum InOut {
        IN, OUT
    }

    public static enum MessageStatus {
        RECEIVED, SENT, TO_SEND, WAITING_RECEPIT, ERROR, CONFIRMED, ACCEPTED, RESENT
    }

    public static enum MessageType {
        ERROR, MAIL, PEC, RECEPIT, DRAFT
    }

    public static enum RelationType {
        RICEVUTA, INOLTRA, RISPONDI, RISPONDI_TUTTI, REINDIRIZZA
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
    @Column(name = "uuid_message")
    private String uuidMessage;

    @Basic(optional = false)
    @NotNull
    @JoinColumn(name = "id_pec", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Pec idPec;

    @JoinColumn(name = "id_applicazione", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Applicazione idApplicazione;

    @JoinColumn(name = "id_related", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Message idRelated;

    @Size(max = 2147483647)
    @Column(name = "subject")
    private String subject;

    @Column(name = "message_status")
    private String messageStatus;

    @Size(max = 2147483647)
    @Column(name = "in_out")
    private String inOut;

    @Basic(optional = false)
    @NotNull
    @Column(name = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createTime = LocalDateTime.now();

    @Basic(optional = false)
    @NotNull
    @Column(name = "update_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updateTime = LocalDateTime.now();

    @Size(max = 2147483647)
    @Column(name = "message_type")
    private String messageType;

    @Basic(optional = false)
    @NotNull
    @Column(name = "is_pec")
    private Boolean isPec;

    @Basic(optional = false)
    @NotNull
    @Column(name = "attachments_number")
    private Integer attachmentsNumber;

    @Size(max = 2147483647)
    @Column(name = "uuid_repository")
    private String uuidRepository;

    @Size(max = 2147483647)
    @Column(name = "path_repository")
    private String pathRepository;

    @Size(max = 2147483647)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @NotNull
    @Column(name = "receive_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime receiveTime = LocalDateTime.now();

    @Size(max = 2147483647)
    @Column(name = "external_id")
    private String externalId;

    @Basic(optional = false)
    @NotNull
    @Column(name = "seen")
    private Boolean seen = false;

    @Size(max = 2147483647)
    @Column(name = "tscol", columnDefinition = "tsvector")
    private String tscol;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "idMessage", fetch = FetchType.LAZY)
    @JsonBackReference(value = "messageAddressList")
    private List<MessageAddress> messageAddressList;
    
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "id", fetch = FetchType.EAGER)
    @JsonBackReference(value = "messageExtensionList")
    private List<MessageExtension> messageExtensionList;
    
//    @Formula("(select shpeck.messages_addresses ma where ma.address_role = 'FROM' and ma.message = id)")
//    private MessageAddress messageAddressFrom;
    
//    @JsonIgnore
//    @Formula("(select a.mail_address from shpeck.messages_addresses ma join shpeck.addresses a on a.id = ma.address where ma.address_role = 'FROM' and ma.message = id)")
//    private String addressFrom;
    
    
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "idMessage", fetch = FetchType.LAZY)
    @JsonBackReference(value = "messageTagList")
    private List<MessageTag> messageTagList;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "idMessage", fetch = FetchType.LAZY)
    @JsonBackReference(value = "messageFolderList")
    private List<MessageFolder> messageFolderList;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "idMessage", fetch = FetchType.LAZY)
    @JsonBackReference(value = "rawMessageList")
    private List<RawMessage> rawMessageList;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "idRelated", fetch = FetchType.LAZY)
    @JsonBackReference(value = "idRelatedList")
    private List<Message> idRelatedList;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "idMessage", fetch = FetchType.LAZY)
    @JsonBackReference(value = "noteList")
    private List<Note> noteList;

    @OneToOne(optional = true, cascade = CascadeType.ALL, mappedBy = "idMessage", fetch = FetchType.LAZY)
//    @Fetch(FetchMode.JOIN)
    @JsonBackReference(value = "idRecepit")
    private Recepit idRecepit;

    @JsonBackReference(value = "in_reply_to")
    private String inReplyTo;

    @JsonBackReference(value = "relation_type")
    private String relationType;

    @Column(name = "id_outbox")
    private Integer idOutbox;

    @Column(name = "id_message_pecgw")
    private String idMessagePecgw;
        
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
 
    public Message() {
    }

    public Message(Integer id) {
        this.id = id;
    }

    public Message(Integer id, String uuidMessage, Pec idPec, Applicazione idApplicazione, Message idRelated, String subject, String messageStatus, String inOut, LocalDateTime createTime, LocalDateTime updateTime, String messageType, Boolean isPec, Integer attachmentsNumber, String uuidMongo, String mongoPath, String name, LocalDateTime receiveTime) {
        this.id = id;
        this.uuidMessage = uuidMessage;
        this.idPec = idPec;
        this.idApplicazione = idApplicazione;
        this.idRelated = idRelated;
        this.subject = subject;
        this.messageStatus = messageStatus;
        this.inOut = inOut;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.messageType = messageType;
        this.isPec = isPec;
        this.attachmentsNumber = attachmentsNumber;
        this.uuidRepository = uuidMongo;
        this.pathRepository = mongoPath;
        this.name = name;
        this.receiveTime = receiveTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuidMessage() {
        return uuidMessage;
    }

    public void setUuidMessage(String uuidMessage) {
        this.uuidMessage = uuidMessage;
    }

    public Pec getIdPec() {
        return idPec;
    }

    public void setIdPec(Pec idPec) {
        this.idPec = idPec;
    }

    public Applicazione getIdApplicazione() {
        return idApplicazione;
    }

    public void setIdApplicazione(Applicazione idApplicazione) {
        this.idApplicazione = idApplicazione;
    }

    public Message getIdRelated() {
        return idRelated;
    }

    public void setIdRelated(Message idRelated) {
        this.idRelated = idRelated;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public MessageStatus getMessageStatus() {
        return MessageStatus.valueOf(messageStatus);
    }

    public void setMessageStatus(MessageStatus messageStatus) {
        this.messageStatus = messageStatus.toString();
    }

    public InOut getInOut() {
        return InOut.valueOf(inOut);
    }

    public void setInOut(InOut inOut) {
        this.inOut = inOut.toString();
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

    public MessageType getMessageType() {
        return MessageType.valueOf(messageType);
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType.toString();
    }

    public Boolean getIsPec() {
        return isPec;
    }

    public void setIsPec(Boolean isPec) {
        this.isPec = isPec;
    }

    public Integer getAttachmentsNumber() {
        return attachmentsNumber;
    }

    public void setAttachmentsNumber(Integer attachmentsNumber) {
        this.attachmentsNumber = attachmentsNumber;
    }

    public String getUuidRepository() {
        return uuidRepository;
    }

    public void setUuidRepository(String uuidRepository) {
        this.uuidRepository = uuidRepository;
    }

    public String getPathRepository() {
        return pathRepository;
    }

    public void setPathRepository(String pathRepository) {
        this.pathRepository = pathRepository;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getTscol() {
        return tscol;
    }

    public void setTscol(String tscol) {
        this.tscol = tscol;
    }

    public LocalDateTime getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(LocalDateTime receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public List<MessageAddress> getMessageAddressList() {
        return messageAddressList;
    }

    public void setMessageAddressList(List<MessageAddress> messageAddressList) {
        this.messageAddressList = messageAddressList;
    }

    public List<MessageExtension> getMessageExtensionList() {
        return messageExtensionList;
    }

    public void setMessageExtensionList(List<MessageExtension> messageExtensionList) {
        this.messageExtensionList = messageExtensionList;
    }

//    public String getAddressFrom() {
//        return addressFrom;
//    }

//    public MessageAddress getMessageAddressFrom() {
//        return messageAddressFrom;
//    }
//
//    public void setMessageAddressFrom(MessageAddress messageAddressFrom) {
//        this.messageAddressFrom = messageAddressFrom;
//    }
//    public void setAddressFrom(String addressFrom) {
//        this.addressFrom = addressFrom;
//    }

    public List<MessageTag> getMessageTagList() {
        return messageTagList;
    }

    public void setMessageTagList(List<MessageTag> messageTagList) {
        this.messageTagList = messageTagList;
    }

    public List<MessageFolder> getMessageFolderList() {
        return messageFolderList;
    }

    public void setMessageFolderList(List<MessageFolder> messageFolderList) {
        this.messageFolderList = messageFolderList;
    }

    public List<RawMessage> getRawMessageList() {
        return rawMessageList;
    }

    public void setRawMessageList(List<RawMessage> rawMessageList) {
        this.rawMessageList = rawMessageList;
    }

    public List<Message> getIdRelatedList() {
        return idRelatedList;
    }

    public void setIdRelatedList(List<Message> idRelatedList) {
        this.idRelatedList = idRelatedList;
    }

    public List<Note> getNoteList() {
        return noteList;
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
    }

    public Recepit getIdRecepit() {
        return idRecepit;
    }

    public void setIdRecepit(Recepit idRecepit) {
        this.idRecepit = idRecepit;
    }

    public String getInReplyTo() {
        return inReplyTo;
    }

    public void setInReplyTo(String inReplyTo) {
        this.inReplyTo = inReplyTo;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public Integer getIdOutbox() {
        return idOutbox;
    }

    public void setIdOutbox(Integer idOutbox) {
        this.idOutbox = idOutbox;
    }

    public String getIdMessagePecgw() {
        return idMessagePecgw;
    }

    public void setIdMessagePecgw(String idMessagePecgw) {
        this.idMessagePecgw = idMessagePecgw;
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
        if (!(object instanceof Message)) {
            return false;
        }
        Message other = (Message) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Message{" + "id=" + id + ", uuidMessage=" + uuidMessage + ", subject=" + subject + ", messageStatus=" + messageStatus
                + ", inOut=" + inOut + ", createTime=" + createTime + ", updateTime=" + updateTime
                + ", messageType=" + messageType + ", attachmentsNumber=" + attachmentsNumber
                + ", uuidRepository=" + uuidRepository + ", pathRepository=" + pathRepository
                + ", receiveTime=" + receiveTime + ", seen=" + seen + ", inReplyTo=" + inReplyTo
                + ", relationType=" + relationType + ", idOutbox=" + idOutbox + ", id_message_vecchio=" + idMessagePecgw + '}';
    }

    @Override
    public Message clone() throws CloneNotSupportedException {

        // (Message) super.clone();
        Message m = new Message();
        m.setIdPec(this.getIdPec());
        m.setIdApplicazione(this.getIdApplicazione());
        m.setIdRelated(this.getIdRelated());
        m.setSubject(this.getSubject());
        m.setMessageStatus(this.getMessageStatus());
        m.setInOut(this.getInOut());
        m.setCreateTime(this.getCreateTime());
        m.setMessageType(this.getMessageType());
        m.setIsPec(this.getIsPec());
        m.setAttachmentsNumber(this.getAttachmentsNumber());
        m.setUuidRepository(this.getUuidRepository());
        m.setPathRepository(this.getPathRepository());
        m.setName(this.getName());
        m.setReceiveTime(this.getReceiveTime());
        m.setIdMessagePecgw(this.getIdMessagePecgw());
        m.setIdOutbox(this.getIdOutbox());
        m.setIdRecepit(this.getIdRecepit());
        m.setInReplyTo(this.getInReplyTo());

        List<MessageAddress> maList = new ArrayList();
        MessageAddress mma;
        for (MessageAddress ma : this.getMessageAddressList()) {
            mma = ma.clone();
            mma.setIdMessage(m);
            maList.add(mma);
        }
        m.setMessageAddressList(maList);

        List<MessageTag> mtList = new ArrayList();
        for (MessageTag mt : this.getMessageTagList()) {
            mtList.add(mt.clone());
        }
        m.setMessageTagList(mtList);

        m.setRelationType(this.getRelationType());
        m.setSeen(this.getSeen());
        m.setTscol(this.getTscol());
        m.setUuidMessage(this.getUuidMessage());
        m.setMessageStatus(this.getMessageStatus());

        return m;
    }

}

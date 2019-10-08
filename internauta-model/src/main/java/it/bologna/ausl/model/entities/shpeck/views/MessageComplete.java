package it.bologna.ausl.model.entities.shpeck.views;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.bologna.ausl.internauta.utils.jpa.tools.GenericArrayUserType;
import it.bologna.ausl.model.entities.baborg.Pec;
import it.bologna.ausl.model.entities.shpeck.Folder;
import it.bologna.ausl.model.entities.shpeck.Message;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author gdm
 */

@TypeDefs(
        {
            @TypeDef(name = "array", typeClass = GenericArrayUserType.class)
        }
)

@Entity
@Table(name = "messages_complete", catalog = "internauta", schema = "shpeck")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
@Cacheable(false)
public class MessageComplete implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    private Integer id;
    
    @JoinColumn(name = "id_pec", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Pec idPec;
    
    @Size(max = 2147483647)
    @Column(name = "subject")
    private String subject;
    
    @Size(min = 1, max = 2147483647)
    @Column(name = "uuid_message")
    private String uuidMessage;

    @Size(max = 2147483647)
    @Column(name = "in_out")
    private String inOut;
    
    @Column(name = "message_status")
    private String messageStatus;

    @Column(name = "create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createTime;

    @Column(name = "update_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updateTime;
    
    @Size(max = 2147483647)
    @Column(name = "message_type")
    private String messageType;

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

    @Column(name = "receive_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime receiveTime;

    @Column(name = "seen")
    private Boolean seen;

//    @JoinColumn(name = "id_raw_message", referencedColumnName = "id")
//    @ManyToOne(optional = false, fetch = FetchType.LAZY)
//    private RawMessage idRawMessage;

    @Column(name = "folderType")
    private String folderType;

    @JoinColumn(name = "id_folder", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Folder idFolder;
    
    @JoinColumn(name = "id_target_folder", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Folder idTargetFolder;
    
    @Column(name = "id_tags", columnDefinition = "text[]")
    @Type(type = "array", parameters = @Parameter(name = "elements-type", value = GenericArrayUserType.INTEGER_ELEMENT_TYPE))
    private Integer[] idTags;
    
    @Column(name = "from")
    private String from;
    
    @Column(name = "to")
    private String to;
    
    @Column(name = "cc")
    private String cc;

    public MessageComplete() {
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

    public String getUuidMessage() {
        return uuidMessage;
    }

    public void setUuidMessage(String uuidMessage) {
        this.uuidMessage = uuidMessage;
    }

    public Message.InOut getInOut() {
        return Message.InOut.valueOf(inOut);
    }

    public void setInOut(Message.InOut inOut) {
        this.inOut = inOut.toString();
    }

    public Message.MessageStatus getMessageStatus() {
        return Message.MessageStatus.valueOf(messageStatus);
    }

    public void setMessageStatus(Message.MessageStatus messageStatus) {
        this.messageStatus = messageStatus.toString();
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

    public Message.MessageType getMessageType() {
        return Message.MessageType.valueOf(messageType);
    }

    public void setMessageType(Message.MessageType messageType) {
        this.messageType = messageType.toString();
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

    public Folder.FolderType getFolderType() {
        return Folder.FolderType.valueOf(folderType);
    }

    public void setFolderType(Folder.FolderType folderType) {
        this.folderType = folderType.toString();
    }

    public Folder getIdFolder() {
        return idFolder;
    }

    public void setIdFolder(Folder idFolder) {
        this.idFolder = idFolder;
    }

    public Folder getIdTargetFolder() {
        return idTargetFolder;
    }

    public void setIdTargetFolder(Folder idTargetFolder) {
        this.idTargetFolder = idTargetFolder;
    }

    public Integer[] getIdTags() {
        return idTags;
    }

    public void setIdTags(Integer[] idTags) {
        this.idTags = idTags;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

}

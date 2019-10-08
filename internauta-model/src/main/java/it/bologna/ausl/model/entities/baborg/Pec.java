package it.bologna.ausl.model.entities.baborg;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.querydsl.core.annotations.PropertyType;
import com.querydsl.core.annotations.QueryType;
import it.bologna.ausl.internauta.utils.bds.types.PermessoEntitaStoredProcedure;
import it.bologna.ausl.model.entities.shpeck.Draft;
import it.bologna.ausl.model.entities.shpeck.Folder;
import it.bologna.ausl.model.entities.shpeck.Tag;
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
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author solidus83
 */
@Entity
@Table(name = "pec", catalog = "internauta", schema = "baborg")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, ignoreUnknown = true)
public class Pec implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    protected Integer id;

    @Basic(optional = false)
    @NotNull
    @Column(name = "indirizzo", columnDefinition = "text")
    private String indirizzo;

    @Basic(optional = false)
    @NotNull
    @JsonIgnore
    @Column(name = "username", columnDefinition = "text")
    private String username;

    @Basic(optional = false)
    @NotNull
    @JsonIgnore
    @Column(name = "password", columnDefinition = "text")
    private String password;

    @Basic(optional = false)
    @NotNull
    @Column(name = "attiva")
    private Boolean attiva;

    @Basic(optional = false)
    @NotNull
    @Column(name = "message_policy")
    private Integer messagePolicy;

    @Basic(optional = false)
    @NotNull
    @Column(name = "per_riservato")
    private Boolean perRiservato;

    @Column(name = "descrizione", columnDefinition = "text")
    private String descrizione;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "idPec", fetch = FetchType.LAZY)
    @JsonBackReference(value = "pecStrutturaList")
    private List<PecStruttura> pecStrutturaList;

    @JoinColumn(name = "id_azienda_repository", referencedColumnName = "id")
    @ManyToOne(optional = true, fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Azienda idAziendaRepository;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "idPec", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonBackReference(value = "pecAziendaList")
    private List<PecAzienda> pecAziendaList;

    @JoinColumn(name = "id_pec_provider", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private PecProvider idPecProvider;

    @OneToMany(mappedBy = "idPec", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    @JsonBackReference(value = "pecUtenteList")
    private List<PecUtente> pecUtenteList;

    @OneToMany(mappedBy = "idPec", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    @JsonBackReference(value = "tagList")
    private List<Tag> tagList;

    @OneToMany(mappedBy = "idPec", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    @JsonBackReference(value = "folderList")
    private List<Folder> folderList;

    @OneToMany(mappedBy = "idPec", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonBackReference(value = "draftList")
    private List<Draft> draftList;

    @Column(name = "massiva")
    private Boolean massiva;

    @Column(name = "chiusa")
    private Boolean chiusa;

    @Basic(optional = false)
    @NotNull
    @Column(name = "is_pec")
    private Boolean isPec = false;

    @Basic(optional = false)
    @NotNull
    @Column(name = "lastuid")
    private Long lastuid = 0L;

    @Basic(optional = false)
    @NotNull
    @Column(name = "send_delay")
    private Integer sendDelay = -1;

    @Column(name = "reset_lastuid_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime resetLastuidTime;
        
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
    
    @Transient
    @QueryType(PropertyType.SIMPLE)
    private List<PermessoEntitaStoredProcedure> permessi;

    @Transient
    @QueryType(PropertyType.SIMPLE)
    private List<Persona> gestori;
    
    @Transient
    @QueryType(PropertyType.SIMPLE)
    private List<Struttura> strutture;

    public Pec() {
    }

    public Pec(Integer id) {
        this.id = id;
    }

    public Pec(Integer id, String indirizzo, String username, String password, Boolean attiva, Integer messagePolicy, Boolean perRiservato) {
        this.id = id;
        this.indirizzo = indirizzo;
        this.username = username;
        this.password = password;
        this.attiva = attiva;
        this.messagePolicy = messagePolicy;
        this.perRiservato = perRiservato;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAttiva() {
        return attiva;
    }

    public void setAttiva(Boolean attiva) {
        this.attiva = attiva;
    }

    public Integer getMessagePolicy() {
        return messagePolicy;
    }

    public void setMessagePolicy(Integer messagePolicy) {
        this.messagePolicy = messagePolicy;
    }

    public Boolean getPerRiservato() {
        return perRiservato;
    }

    public void setPerRiservato(Boolean perRiservato) {
        this.perRiservato = perRiservato;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public List<PecStruttura> getPecStrutturaList() {
        return pecStrutturaList;
    }

    public void setPecStrutturaList(List<PecStruttura> pecStrutturaList) {
        this.pecStrutturaList = pecStrutturaList;
    }

    public Azienda getIdAziendaRepository() {
        return idAziendaRepository;
    }

    public void setIdAziendaRepository(Azienda idAziendaRepository) {
        this.idAziendaRepository = idAziendaRepository;
    }

    public List<PecAzienda> getPecAziendaList() {
        return pecAziendaList;
    }

    public void setPecAziendaList(List<PecAzienda> pecAziendaList) {
        this.pecAziendaList = pecAziendaList;
    }

    public PecProvider getIdPecProvider() {
        return idPecProvider;
    }

    public void setIdPecProvider(PecProvider idPecProvider) {
        this.idPecProvider = idPecProvider;
    }

    public List<PecUtente> getPecUtenteList() {
        return pecUtenteList;
    }

    public void setPecUtenteList(List<PecUtente> pecUtenteList) {
        this.pecUtenteList = pecUtenteList;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public List<Folder> getFolderList() {
        return folderList;
    }

    public void setFolderList(List<Folder> folderList) {
        this.folderList = folderList;
    }

    public List<Draft> getDraftList() {
        return draftList;
    }

    public void setDraftList(List<Draft> draftList) {
        this.draftList = draftList;
    }

    public Boolean getMassiva() {
        return massiva;
    }

    public void setMassiva(Boolean massiva) {
        this.massiva = massiva;
    }

    public Boolean getChiusa() {
        return chiusa;
    }

    public void setChiusa(Boolean chiusa) {
        this.chiusa = chiusa;
    }

    public List<PermessoEntitaStoredProcedure> getPermessi() {
        return permessi;
    }

    public void setPermessi(List<PermessoEntitaStoredProcedure> permessi) {
        this.permessi = permessi;
    }

    public List<Persona> getGestori() {
        return gestori;
    }

    public void setGestori(List<Persona> gestori) {
        this.gestori = gestori;
    }

    public List<Struttura> getStrutture() {
        return strutture;
    }

    public void setStrutture(List<Struttura> strutture) {
        this.strutture = strutture;
    }
    
    public Boolean getIsPec() {
        return isPec;
    }

    public void setIsPec(Boolean isPec) {
        this.isPec = isPec;
    }

    public Long getLastuid() {
        return lastuid;
    }

    public void setLastuid(Long lastuid) {
        this.lastuid = lastuid;
    }

    public Integer getSendDelay() {
        return sendDelay;
    }

    public void setSendDelay(Integer sendDelay) {
        this.sendDelay = sendDelay;
    }

    public LocalDateTime getResetLastuidTime() {
        return resetLastuidTime;
    }

    public void setResetLastuidTime(LocalDateTime resetLastuidTime) {
        this.resetLastuidTime = resetLastuidTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : super.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pec)) {
            return false;
        }
        Pec other = (Pec) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.bologna.ausl.model.entities.baborg.Pec[ id=" + id + " ]";
    }

}

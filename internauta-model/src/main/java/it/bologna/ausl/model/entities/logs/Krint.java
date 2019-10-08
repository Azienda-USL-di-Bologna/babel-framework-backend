package it.bologna.ausl.model.entities.logs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.bologna.ausl.model.entities.configuration.Applicazione;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @author Guido
 */
@Entity
@Table(name = "krint", catalog = "internauta", schema = "logs")
// TODO: capire se servono le due righe seguenti, le ho copiate dall'entità utenti, se non servono le tolgo
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "authorities"})
@Cacheable(false)
public class Krint implements Serializable {
    
    public static enum TipoOggettoKrint {
        BABORG_PEC, SHPECK_MESSAGE, SHPECK_DRAFT, SHPECK_FOLDER, SHPECK_TAG, SHPECK_OUTBOX
    };
    
//    public static enum KrintField {
//        idSessione, idUtente, descrizioneUtente, informazioniUtente,
//        idRealUser, descrizioneRealUser, informazioniRealUser, idOggetto,
//        tipoOggetto, informazioniOggetto, idOggettoCollegato, tipoOggettoCollegato,
//        informazioniOggettoCollegato, idOperazione         
//    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_sessione")
    private Integer idSessione;
    @Basic(optional = false)
    @NotNull
    @Column(name = "applicazione")
    private String applicazione;
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_utente")
    private Integer idUtente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "descrizione_utente")
    private String descrizioneUtente;
    @Basic(optional = false)
    @NotNull
    @Column(name = "informazioni_utente", columnDefinition = "jsonb")
    private String informazioniUtente;
    @Column(name = "id_real_user")
    private Integer idRealUser;
    @Size(max = 2147483647)
    @Column(name = "descrizione_real_user")
    private String descrizioneRealUser;
    @Column(name = "informazioni_real_user", columnDefinition = "jsonb")
    private String informazioniRealUser;
    @Size(max = 100)
    @Column(name = "id_oggetto")
    private String idOggetto;
    @Size(max = 2147483647)
    @Column(name = "tipo_oggetto")
    private String tipoOggetto;
    @Size(max = 2147483647)
    @Column(name = "descrizione_oggetto")
    private String descrizioneOggetto;
    @Column(name = "informazioni_oggetto", columnDefinition = "jsonb")  
    private String informazioniOggetto;
    @Size(max = 100)
    @Column(name = "id_oggetto_contenitore")
    private String idOggettoContenitore;
    @Size(max = 2147483647)
    @Column(name = "tipo_oggetto_contenitore")
    private String tipoOggettoContenitore;
    @Size(max = 2147483647)
    @Column(name = "descrizione_oggetto_contenitore")
    private String descrizioneOggettoContenitore;
    @Column(name = "informazioni_oggetto_contenitore", columnDefinition = "jsonb")
    private String informazioniOggettoContenitore;
    @JoinColumn(name = "id_operazione_versionata", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private OperazioneVersionataKrint idOperazioneVersionata;
    @Column(name = "dataora_operazione", updatable = false, insertable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataoraOperazione;

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

    
    
    public Krint() {
    }



    public Krint(Integer idSessione, Applicazione.Applicazioni applicazione, Integer idUtente, String descrizioneUtente, String informazioniUtente) {
        this.applicazione = applicazione.name();
        this.idSessione = idSessione;
        this.idUtente = idUtente;
        this.descrizioneUtente = descrizioneUtente;
        this.informazioniUtente = informazioniUtente;
    }
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdSessione() {
        return idSessione;
    }

    public void setIdSessione(Integer idSessione) {
        this.idSessione = idSessione;
    }

    public Applicazione.Applicazioni getApplicazione() {
        return Applicazione.Applicazioni.valueOf(applicazione);
    }

    public void setApplicazione(Applicazione.Applicazioni applicazione) {
        this.applicazione = applicazione.name();
    }
    
    public int getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Integer idUtente) {
        this.idUtente = idUtente;
    }

    public String getDescrizioneUtente() {
        return descrizioneUtente;
    }

    public void setDescrizioneUtente(String descrizioneUtente) {
        this.descrizioneUtente = descrizioneUtente;
    }

    public Object getInformazioniUtente() {
        return informazioniUtente;
    }

    public void setInformazioniUtente(String informazioniUtente) {
        this.informazioniUtente = informazioniUtente;
    }

    public Integer getIdRealUser() {
        return idRealUser;
    }

    public void setIdRealUser(Integer idRealUser) {
        this.idRealUser = idRealUser;
    }

    public String getDescrizioneRealUser() {
        return descrizioneRealUser;
    }

    public void setDescrizioneRealUser(String descrizioneRealUser) {
        this.descrizioneRealUser = descrizioneRealUser;
    }

    public Object getInformazioniRealUser() {
        return informazioniRealUser;
    }

    public void setInformazioniRealUser(String informazioniRealUser) {
        this.informazioniRealUser = informazioniRealUser;
    }

    public String getIdOggetto() {
        return idOggetto;
    }

    public void setIdOggetto(String idOggetto) {
        this.idOggetto = idOggetto;
    }

    public TipoOggettoKrint getTipoOggetto() {
        return TipoOggettoKrint.valueOf(tipoOggetto);
    }

    public void setTipoOggetto(TipoOggettoKrint tipoOggetto) {
        this.tipoOggetto = tipoOggetto.toString();
    }
    
    
    public String getDescrizioneOggetto() {
        return descrizioneOggetto;
    }

    public void setDescrizioneOggetto(String descrizioneOggetto) {
        this.descrizioneOggetto = descrizioneOggetto;
    }

    public Object getInformazioniOggetto() {
        return informazioniOggetto;
    }

    public void setInformazioniOggetto(String informazioniOggetto) {
        this.informazioniOggetto = informazioniOggetto;
    }

    public String getIdOggettoContenitore() {
        return idOggettoContenitore;
    }

    public void setIdOggettoContenitore(String idOggettoContenitore) {
        this.idOggettoContenitore = idOggettoContenitore;
    }

    public TipoOggettoKrint getTipoOggettoContenitore() {
        return TipoOggettoKrint.valueOf(tipoOggettoContenitore);
    }

    public void setTipoOggettoContenitore(TipoOggettoKrint tipoOggettoContenitore) {
        this.tipoOggettoContenitore = tipoOggettoContenitore.toString();
    }
    
    public String getDescrizioneOggettoContenitore() {
        return descrizioneOggettoContenitore;
    }

    public void setDescrizioneOggettoContenitore(String descrizioneOggettoContenitore) {
        this.descrizioneOggettoContenitore = descrizioneOggettoContenitore;
    }


    public Object getInformazioniOggettoContenitore() {
        return informazioniOggettoContenitore;
    }

    public void setInformazioniOggettoContenitore(String informazioniOggettoContenitore) {
        this.informazioniOggettoContenitore = informazioniOggettoContenitore;
    }

    public LocalDateTime getDataoraOperazione() {
        return dataoraOperazione;
    }

    public void setDataoraOperazione(LocalDateTime dataoraOperazione) {
        this.dataoraOperazione = dataoraOperazione;
    }

    public OperazioneVersionataKrint getIdOperazioneVersionata() {
        return idOperazioneVersionata;
    }

    public void setIdOperazioneVersionata(OperazioneVersionataKrint idOperazioneVersionata) {
        this.idOperazioneVersionata = idOperazioneVersionata;
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
        if (!(object instanceof Krint)) {
            return false;
        }
        Krint other = (Krint) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.bologna.ausl.model.entities.logs.Krint[ id=" + id + " ]";
    }
    
}

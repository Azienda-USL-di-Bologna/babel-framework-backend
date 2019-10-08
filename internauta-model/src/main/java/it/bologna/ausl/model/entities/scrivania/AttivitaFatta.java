package it.bologna.ausl.model.entities.scrivania;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.bologna.ausl.model.entities.baborg.Azienda;
import it.bologna.ausl.model.entities.baborg.Persona;
import it.bologna.ausl.model.entities.configuration.Applicazione;
import it.bologna.ausl.internauta.utils.jpa.tools.GenericArrayUserType;
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
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author gus
 */
@TypeDefs(
        {
            @TypeDef(name = "array", typeClass = GenericArrayUserType.class)
        }
)
@Entity
@Table(name = "attivita_fatte", catalog = "internauta", schema = "scrivania")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Cacheable(false)
public class AttivitaFatta implements Serializable {

    public enum TipoAttivitaFatta {
        ATTIVITA("attivita"),
        NOTIFICA("notifica");

        private final String key;

        TipoAttivitaFatta(String key) {
            this.key = key;
        }

        public static AttivitaFatta.TipoAttivitaFatta fromString(String key) {
            return key == null
                    ? null
                    : AttivitaFatta.TipoAttivitaFatta.valueOf(key.toUpperCase());
        }

        public String getKey() {
            return key;
        }

        @Override
        public String toString() {
            return getKey();
        }
    }
    
    public enum IdApplicazione{
        PICO("procton"),
        DETE("dete"),
        DELI("deli");
        
        private final String key;
        
        IdApplicazione(String key) {
            this.key = key;
        }
        public static Attivita.IdApplicazione fromString(String key) {
            return key == null
                    ? null
                    : Attivita.IdApplicazione.valueOf(key.toUpperCase());
        }

        public String getKey() {
            return key;
        }

        @Override
        public String toString() {
            return getKey();
        }
    }
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @JoinColumn(name = "id_azienda", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Azienda idAzienda;
    @Basic(optional = false)
    @NotNull
    @JoinColumn(name = "id_persona", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Persona idPersona;
    @Basic(optional = false)
    @NotNull
    @JoinColumn(name = "id_applicazione", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Applicazione idApplicazione;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo", columnDefinition = "text")
    private String tipo;
    @Column(name = "oggetto", columnDefinition = "text")
    private String oggetto;
    @Column(name = "descrizione", columnDefinition = "text")
    private String descrizione;
    @Column(name = "urls", columnDefinition = "text")
    private String urls;
    @Column(name = "aperta")
    private Boolean aperta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime data;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_inserimento_riga")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataInserimentoRiga;
    @Column(name = "data_ultima_modifica")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataUltimaModifica;
    @Column(name = "note", columnDefinition = "text")
    private String note;
    @Column(name = "provenienza", columnDefinition = "text")
    private String provenienza;
    @Column(name = "data_scadenza")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataScadenza;
    @Column(name = "priorita")
    private Integer priorita;
    @Column(name = "tags", columnDefinition = "text[]")
    @Type(type = "array", parameters = @Parameter(name = "elements-type", value = GenericArrayUserType.TEXT_ELEMENT_TYPE))
    private String[] tags;
    @Column(name = "oggetto_esterno")
    private String oggettoEsterno;
    @Column(name = "tipo_oggetto_esterno", columnDefinition = "text")
    private String tipoOggettoEsterno;
    @Column(name = "oggetto_esterno_secondario")
    private String oggettoEsternoSecondario;
    @Column(name = "tipo_oggetto_esterno_secondario", columnDefinition = "text")
    private String tipoOggettoEsternoSecondario;
    @Column(name = "dati_aggiuntivi", columnDefinition = "text")
    private String datiAggiuntivi;
    @Column(name = "classe", columnDefinition = "text")
    private String classe;
    @Column(name = "allegati", columnDefinition = "text")
    private String allegati;
        
//    @Version()
//    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
//    private LocalDateTime version;
//
//    public LocalDateTime getVersion() {
//        return version;
//    }
//
//    public void setVersion(LocalDateTime version) {
//        this.version = version;
//    }
 
    @Transient
    private String compiledUrls;
        
    public AttivitaFatta() {
    }

    public AttivitaFatta(Integer id) {
        this.id = id;
    }

    public AttivitaFatta(Integer id, Azienda idAzienda, Persona idPersona, Applicazione idApplicazione, String tipo, LocalDateTime data, LocalDateTime dataInserimentoRiga) {
        this.id = id;
        this.idAzienda = idAzienda;
        this.idPersona = idPersona;
        this.idApplicazione = idApplicazione;
        this.tipo = tipo;
        this.data = data;
        this.dataInserimentoRiga = dataInserimentoRiga;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Azienda getIdAzienda() {
        return idAzienda;
    }

    public void setIdAzienda(Azienda idAzienda) {
        this.idAzienda = idAzienda;
    }

    public Persona getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Persona idPersona) {
        this.idPersona = idPersona;
    }

    public Applicazione getIdApplicazione() {
        return idApplicazione;
    }

    public void setIdApplicazione(Applicazione idApplicazione) {
        this.idApplicazione = idApplicazione;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getOggetto() {
        return oggetto;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    public Boolean getAperta() {
        return aperta;
    }

    public void setAperta(Boolean aperta) {
        this.aperta = aperta;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public LocalDateTime getDataInserimentoRiga() {
        return dataInserimentoRiga;
    }

    public void setDataInserimentoRiga(LocalDateTime dataInserimentoRiga) {
        this.dataInserimentoRiga = dataInserimentoRiga;
    }

    public LocalDateTime getDataUltimaModifica() {
        return dataUltimaModifica;
    }

    public void setDataUltimaModifica(LocalDateTime dataUltimaModifica) {
        this.dataUltimaModifica = dataUltimaModifica;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getProvenienza() {
        return provenienza;
    }

    public void setProvenienza(String provenienza) {
        this.provenienza = provenienza;
    }

    public LocalDateTime getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(LocalDateTime dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public Integer getPriorita() {
        return priorita;
    }

    public void setPriorita(Integer priorita) {
        this.priorita = priorita;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getOggettoEsterno() {
        return oggettoEsterno;
    }

    public void setOggettoEsterno(String oggettoEsterno) {
        this.oggettoEsterno = oggettoEsterno;
    }

    public String getTipoOggettoEsterno() {
        return tipoOggettoEsterno;
    }

    public void setTipoOggettoEsterno(String tipoOggettoEsterno) {
        this.tipoOggettoEsterno = tipoOggettoEsterno;
    }

    public String getOggettoEsternoSecondario() {
        return oggettoEsternoSecondario;
    }

    public void setOggettoEsternoSecondario(String oggettoEsternoSecondario) {
        this.oggettoEsternoSecondario = oggettoEsternoSecondario;
    }

    public String getTipoOggettoEsternoSecondario() {
        return tipoOggettoEsternoSecondario;
    }

    public void setTipoOggettoEsternoSecondario(String tipoOggettoEsternoSecondario) {
        this.tipoOggettoEsternoSecondario = tipoOggettoEsternoSecondario;
    }

    public String getDatiAggiuntivi() {
        return datiAggiuntivi;
    }

    public void setDatiAggiuntivi(String datiAggiuntivi) {
        this.datiAggiuntivi = datiAggiuntivi;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getAllegati() {
        return allegati;
    }

    public void setAllegati(String allegati) {
        this.allegati = allegati;
    }

    public String getCompiledUrls() {
        return compiledUrls;
    }

    public void setCompiledUrls(String compiledUrls) {
        this.compiledUrls = compiledUrls;
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
        if (!(object instanceof AttivitaFatta)) {
            return false;
        }
        AttivitaFatta other = (AttivitaFatta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.bologna.ausl.model.entities.scrivania.AttivitaFatte[ id=" + id + " ]";
    }

}

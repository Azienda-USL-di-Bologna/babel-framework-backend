package it.bologna.ausl.model.entities.baborg;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.bologna.ausl.internauta.utils.bds.types.PermessoEntitaStoredProcedure;
import it.bologna.ausl.internauta.utils.jpa.tools.GenericArrayUserType;
import it.bologna.ausl.model.entities.configuration.ImpostazioniApplicazioni;
import it.bologna.ausl.model.entities.scrivania.Attivita;
import it.bologna.ausl.model.entities.scrivania.AttivitaFatta;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author solidus83
 */
@TypeDefs(
    {
        @TypeDef(name = "array", typeClass = GenericArrayUserType.class)
    }
)
@Entity
@Table(name = "persone", catalog = "internauta", schema = "baborg")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Cacheable(false)
public class Persona implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "nome", columnDefinition = "text")
    private String nome;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "cognome", columnDefinition = "text")
    private String cognome;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "codice_fiscale", columnDefinition = "text")
    private String codiceFiscale;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "bit_ruoli")
    private Integer bitRuoli;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "attiva")
    private Boolean attiva;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "descrizione", columnDefinition = "text")
    private String descrizione;
    
    @OneToMany(mappedBy = "idPersona", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference(value = "utenteList")
    private List<Utente> utenteList;
    
    @OneToMany(mappedBy = "idPersona", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference(value = "attivitaList")
    private List<Attivita> attivitaList;
    
    @OneToMany(mappedBy = "idPersona", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference(value = "attivitaFattaList")
    private List<AttivitaFatta> attivitaFattaList;
    
    @OneToMany(mappedBy = "idPersona", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference(value = "impostazioniApplicazioniList")
    private List<ImpostazioniApplicazioni> impostazioniApplicazioniList;

    @Column(name = "messaggi_visti", columnDefinition = "messaggi_visti[]")
    @Type(type = "array", parameters = @Parameter(name = "elements-type", value = GenericArrayUserType.INTEGER_ELEMENT_TYPE))
    private Integer[] messaggiVisti;
    
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
      
    @Transient //TODO: togliere e usare permessiPec (è usato in PersonaInterceptor)
    private List<PermessoEntitaStoredProcedure> permessi;

    @Transient
    private Map<Integer, List<String>> permessiPec;
    
//    @Transient
//    @QueryType(PropertyType.SIMPLE)
//    private String applicazione;

    public Persona() {
    }

    public Persona(Integer id) {
        this.id = id;
    }

    public Persona(Integer id, String nome, String cognome, String codiceFiscale, Integer bitRuoli, Boolean attiva, String descrizione, List<Utente> utenteList) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.bitRuoli = bitRuoli;
        this.attiva = attiva;
        this.descrizione = descrizione;
        this.utenteList = utenteList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public Integer getBitRuoli() {
        return bitRuoli;
    }

    public void setBitRuoli(Integer bitRuoli) {
        this.bitRuoli = bitRuoli;
    }

    public Boolean getAttiva() {
        return attiva;
    }

    public void setAttiva(Boolean attiva) {
        this.attiva = attiva;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public List<Utente> getUtenteList() {
        return utenteList;
    }

    public void setUtenteList(List<Utente> utenteList) {
        this.utenteList = utenteList;
    }

    public List<Attivita> getAttivitaList() {
        return attivitaList;
    }

    public void setAttivitaList(List<Attivita> attivitaList) {
        this.attivitaList = attivitaList;
    }

    public List<AttivitaFatta> getAttivitaFattaList() {
        return attivitaFattaList;
    }

    public void setAttivitaFattaList(List<AttivitaFatta> attivitaFattaList) {
        this.attivitaFattaList = attivitaFattaList;
    }

    public List<ImpostazioniApplicazioni> getImpostazioniApplicazioniList() {
        return impostazioniApplicazioniList;
    }

    public void setImpostazioniApplicazioniList(List<ImpostazioniApplicazioni> impostazioniApplicazioniList) {
        this.impostazioniApplicazioniList = impostazioniApplicazioniList;
    }

    public Integer[] getMessaggiVisti() {
        return messaggiVisti;
    }

    public void setMessaggiVisti(Integer[] messaggiVisti) {
        this.messaggiVisti = messaggiVisti;
    }

    public List<PermessoEntitaStoredProcedure> getPermessi() {
        return permessi;
    }

    public void setPermessi(List<PermessoEntitaStoredProcedure> permessi) {
        this.permessi = permessi;
    }

    public Map<Integer, List<String>> getPermessiPec() {
        return permessiPec;
    }

    public void setPermessiPec(Map<Integer, List<String>> permessiPec) {
        this.permessiPec = permessiPec;
    }

//    public String getApplicazione() {
//        return applicazione;
//    }
//
//    public void setApplicazione(String applicazione) {
//        this.applicazione = applicazione;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : super.hashCode());
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Persona)) {
            return false;
        }
        Persona other = (Persona) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.bologna.ausl.model.entities.baborg.Persona[ id=" + id + " ]";
    }

}

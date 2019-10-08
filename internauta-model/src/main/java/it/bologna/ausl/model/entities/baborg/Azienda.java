package it.bologna.ausl.model.entities.baborg;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.bologna.ausl.model.entities.scrivania.Attivita;
import it.bologna.ausl.model.entities.scrivania.AttivitaFatta;
import it.bologna.ausl.model.entities.scrivania.Menu;
import it.bologna.ausl.internauta.utils.jpa.tools.GenericArrayUserType;
import it.bologna.ausl.model.entities.ribaltoneutils.RibaltoneDaLanciare;
import it.bologna.ausl.model.entities.ribaltoneutils.StoricoAttivazione;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
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
@Table(name = "aziende", catalog = "internauta", schema = "baborg")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Cacheable(false)
public class Azienda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "codice")
    // TODO: sostituire i codiciAzienda con un enum
    private String codice;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "descrizione")
    private String descrizione;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "aoo")
    private String aoo;
    @Size(max = 20)
    @Column(name = "schema_gru")
    private String schemaGru;
    @Column(name = "id_azienda_gru")
    private Integer idAziendaGru;
    @Column(name = "parametri")
    private String parametri;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "codice_regione")
    private String codiceRegione;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ribalta_internauta")
    private Boolean ribaltaInternauta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ribalta_argo")
    private Boolean ribaltaArgo;
    @Basic(optional = true)
    @Column(name = "path", columnDefinition = "text[]")
    @Type(type = "array", parameters = @Parameter(name = "elements-type", value = GenericArrayUserType.TEXT_ELEMENT_TYPE))
    private String[] path;
    @OneToMany(mappedBy = "idAzienda", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference(value = "idpEntityIdList")
    private List<IdpEntityId> idpEntityIdList;
    @OneToMany(mappedBy = "idAzienda", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference(value = "utenteList")
    private List<Utente> utenteList;
    @OneToMany(mappedBy = "idAzienda", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference(value = "strutturaList")
    private List<Struttura> strutturaList;
    @OneToMany(mappedBy = "idAziendaRepository", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference(value = "pecList")
    private List<Pec> pecList;
    @OneToMany(mappedBy = "idAzienda", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    @JsonBackReference(value = "attivitaList")
    private List<Attivita> attivitaList;
    @OneToMany(mappedBy = "idAzienda", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference(value = "attivitaFattaList")
    private List<AttivitaFatta> attivitaFattaList;
    @OneToMany(mappedBy = "idAzienda", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference(value = "menuList")
    private List<Menu> menuList;
    @OneToMany(mappedBy = "idAzienda", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference(value = "storicoAttivazioneList")
    private List<StoricoAttivazione> storicoAttivazioneList;
    @OneToMany(mappedBy = "idAzienda", fetch = FetchType.LAZY)
    @JsonBackReference(value = "ribaltoneDaLanciareList")
    private List<RibaltoneDaLanciare> ribaltoneDaLanciareList;
        
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
    
    public Azienda() {
    }

    public Azienda(Integer id) {
        this.id = id;
    }

    public Azienda(Integer id, String codice, String nome, String descrizione, String aoo, String schemaGru, Integer idAziendaGru, String parametri, String codiceRegione, Boolean ribaltaInternauta, Boolean ribaltaArgo, String[] path, List<IdpEntityId> idpEntityIdList, List<Utente> utenteList, List<Struttura> strutturaList, List<Pec> pecList, List<Attivita> attivitaList) {
        this.id = id;
        this.codice = codice;
        this.nome = nome;
        this.descrizione = descrizione;
        this.aoo = aoo;
        this.schemaGru = schemaGru;
        this.idAziendaGru = idAziendaGru;
        this.parametri = parametri;
        this.codiceRegione = codiceRegione;
        this.ribaltaInternauta = ribaltaInternauta;
        this.ribaltaArgo = ribaltaArgo;
        this.path = path;
        this.idpEntityIdList = idpEntityIdList;
        this.utenteList = utenteList;
        this.strutturaList = strutturaList;
        this.pecList = pecList;
        this.attivitaList = attivitaList;
    }

    public Azienda(Integer id, String codice, String nome, String descrizione, String aoo, String codiceRegione, boolean ribaltaInternauta, boolean ribaltaArgo) {
        this.id = id;
        this.codice = codice;
        this.nome = nome;
        this.descrizione = descrizione;
        this.aoo = aoo;
        this.codiceRegione = codiceRegione;
        this.ribaltaInternauta = ribaltaInternauta;
        this.ribaltaArgo = ribaltaArgo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getAoo() {
        return aoo;
    }

    public void setAoo(String aoo) {
        this.aoo = aoo;
    }

    public String getSchemaGru() {
        return schemaGru;
    }

    public void setSchemaGru(String schemaGru) {
        this.schemaGru = schemaGru;
    }

    public Integer getIdAziendaGru() {
        return idAziendaGru;
    }

    public void setIdAziendaGru(Integer idAziendaGru) {
        this.idAziendaGru = idAziendaGru;
    }

    public String getParametri() {
        return parametri;
    }

    public void setParametri(String parametri) {
        this.parametri = parametri;
    }

    public String getCodiceRegione() {
        return codiceRegione;
    }

    public void setCodiceRegione(String codiceRegione) {
        this.codiceRegione = codiceRegione;
    }

    public Boolean getRibaltaInternauta() {
        return ribaltaInternauta;
    }

    public void setRibaltaInternauta(Boolean ribaltaInternauta) {
        this.ribaltaInternauta = ribaltaInternauta;
    }

    public Boolean getRibaltaArgo() {
        return ribaltaArgo;
    }

    public void setRibaltaArgo(Boolean ribaltaArgo) {
        this.ribaltaArgo = ribaltaArgo;
    }

    public String[] getPath() {
        return path;
    }

    public void setPath(String[] path) {
        this.path = path;
    }

    public List<IdpEntityId> getIdpEntityIdList() {
        return idpEntityIdList;
    }

    public void setIdpEntityIdList(List<IdpEntityId> idpEntityIdList) {
        this.idpEntityIdList = idpEntityIdList;
    }

    public List<Utente> getUtenteList() {
        return utenteList;
    }

    public void setUtenteList(List<Utente> utenteList) {
        this.utenteList = utenteList;
    }

    public List<Struttura> getStrutturaList() {
        return strutturaList;
    }

    public void setStrutturaList(List<Struttura> strutturaList) {
        this.strutturaList = strutturaList;
    }

    public List<Pec> getPecList() {
        return pecList;
    }

    public void setPecList(List<Pec> pecList) {
        this.pecList = pecList;
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

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public List<StoricoAttivazione> getStoricoAttivazioneList() {
        return storicoAttivazioneList;
    }

    public void setStoricoAttivazioneList(List<StoricoAttivazione> storicoAttivazioneList) {
        this.storicoAttivazioneList = storicoAttivazioneList;
    }

    public List<RibaltoneDaLanciare> getRibaltoneDaLanciareList() {
        return ribaltoneDaLanciareList;
    }

    public void setRibaltoneDaLanciareList(List<RibaltoneDaLanciare> ribaltoneDaLanciareList) {
        this.ribaltoneDaLanciareList = ribaltoneDaLanciareList;
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
        if (!(object instanceof Azienda)) {
            return false;
        }
        Azienda other = (Azienda) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.bologna.ausl.model.entities.baborg.Azienda[ id=" + id + " ]";
    }

}

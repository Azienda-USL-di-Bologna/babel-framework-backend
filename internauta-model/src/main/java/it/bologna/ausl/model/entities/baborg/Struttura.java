package it.bologna.ausl.model.entities.baborg;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.querydsl.core.annotations.PropertyType;
import com.querydsl.core.annotations.QueryType;
import it.bologna.ausl.internauta.utils.bds.types.PermessoEntitaStoredProcedure;
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
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author solidus83
 */
@Entity
@Table(name = "strutture", catalog = "internauta", schema = "baborg")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Struttura implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codice")
    private Integer codice;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "nome")
    private String nome;
    @Size(max = 10)
    @Column(name = "codice_dislocazione")
    private String codiceDislocazione;
    @Size(max = 100)
    @Column(name = "dislocazione")
    private String dislocazione;
    @Column(name = "data_attivazione")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataAttivazione;
    @Column(name = "data_cessazione")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataCessazione;
    @Basic(optional = false)
    @NotNull
    @Column(name = "attiva")
    private Boolean attiva;
    @Basic(optional = false)
    @NotNull
    @Column(name = "spettrale")
    private Boolean spettrale;
    @Basic(optional = false)
    @NotNull
    @Column(name = "usa_segreteria_bucata_padre")
    private Boolean usaSegreteriaBucataPadre;
    @Column(name = "foglia")
    private Boolean foglia;
    @OneToMany(mappedBy = "idStruttura", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference(value = "pecStrutturaList")
    private List<PecStruttura> pecStrutturaList;
    @JoinColumn(name = "id_azienda", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Azienda idAzienda;
    @OneToMany(mappedBy = "idStrutturaPadre", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference(value = "struttureFiglieList")
    private List<Struttura> struttureFiglieList;
    @JoinColumn(name = "id_struttura_padre", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference(value = "idStrutturaPadre")
    private Struttura idStrutturaPadre;
    @OneToMany(mappedBy = "idStrutturaSegreteria", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference(value = "struttureSegretariateList")
    private List<Struttura> struttureSegretariateList;
    @JoinColumn(name = "id_struttura_segreteria", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference(value = "idStrutturaSegreteria")
    private Struttura idStrutturaSegreteria;
    @OneToMany(mappedBy = "idStrutturaDestinazione", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference(value = "strutturaUnificataDestinazioneList")
    private List<StrutturaUnificata> strutturaUnificataDestinazioneList;
    @OneToMany(mappedBy = "idStrutturaSorgente", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference(value = "strutturaUnificataSorgenteList")
    private List<StrutturaUnificata> strutturaUnificataSorgenteList;
    @OneToMany(mappedBy = "idStruttura", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference(value = "utenteStrutturaList")
    private List<UtenteStruttura> utenteStrutturaList;
    @JoinColumn(name = "id_struttura_replicata", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference(value = "idStrutturaReplicata")
    private Struttura idStrutturaReplicata;
    @Transient
    @QueryType(PropertyType.SIMPLE)
    private List<PermessoEntitaStoredProcedure> permessi;
    @Transient
    @QueryType(PropertyType.SIMPLE)
    private Boolean isPermessoPecPrincipale;
    @Transient
    @QueryType(PropertyType.SIMPLE)
    private Boolean propagaPermessoPec;


        
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
    
    public Struttura() {
    }

    public Struttura(Integer id) {
        this.id = id;
    }


    public Struttura(Integer id, Integer codice, String nome, String codiceDislocazione, String dislocazione, LocalDateTime dataAttivazione, LocalDateTime dataCessazione, Boolean attiva, Boolean spettrale, Boolean usaSegreteriaBucataPadre, List<PecStruttura> pecStrutturaList, Azienda idAzienda, List<Struttura> struttureFiglieList, Struttura idStrutturaPadre, List<Struttura> struttureSegretariateList, Struttura idStrutturaSegreteria, List<StrutturaUnificata> strutturaUnificataDestinazioneList, List<StrutturaUnificata> strutturaUnificataSorgenteList, List<UtenteStruttura> utenteStrutturaList) {
        this.id = id;
        this.codice = codice;
        this.nome = nome;
        this.codiceDislocazione = codiceDislocazione;
        this.dislocazione = dislocazione;
        this.dataAttivazione = dataAttivazione;
        this.dataCessazione = dataCessazione;
        this.attiva = attiva;
        this.spettrale = spettrale;
        this.usaSegreteriaBucataPadre = usaSegreteriaBucataPadre;
        this.pecStrutturaList = pecStrutturaList;
        this.idAzienda = idAzienda;
        this.struttureFiglieList = struttureFiglieList;
        this.idStrutturaPadre = idStrutturaPadre;
        this.struttureSegretariateList = struttureSegretariateList;
        this.idStrutturaSegreteria = idStrutturaSegreteria;
        this.strutturaUnificataDestinazioneList = strutturaUnificataDestinazioneList;
        this.strutturaUnificataSorgenteList = strutturaUnificataSorgenteList;
        this.utenteStrutturaList = utenteStrutturaList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCodice() {
        return codice;
    }

    public void setCodice(Integer codice) {
        this.codice = codice;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodiceDislocazione() {
        return codiceDislocazione;
    }

    public void setCodiceDislocazione(String codiceDislocazione) {
        this.codiceDislocazione = codiceDislocazione;
    }

    public String getDislocazione() {
        return dislocazione;
    }

    public void setDislocazione(String dislocazione) {
        this.dislocazione = dislocazione;
    }

    public LocalDateTime getDataAttivazione() {
        return dataAttivazione;
    }

    public void setDataAttivazione(LocalDateTime dataAttivazione) {
        this.dataAttivazione = dataAttivazione;
    }

    public LocalDateTime getDataCessazione() {
        return dataCessazione;
    }

    public void setDataCessazione(LocalDateTime dataCessazione) {
        this.dataCessazione = dataCessazione;
    }

    public Boolean getAttiva() {
        return attiva;
    }

    public void setAttiva(Boolean attiva) {
        this.attiva = attiva;
    }

    public Boolean getSpettrale() {
        return spettrale;
    }

    public void setSpettrale(Boolean spettrale) {
        this.spettrale = spettrale;
    }

    public Boolean getUsaSegreteriaBucataPadre() {
        return usaSegreteriaBucataPadre;
    }

    public void setUsaSegreteriaBucataPadre(Boolean usaSegreteriaBucataPadre) {
        this.usaSegreteriaBucataPadre = usaSegreteriaBucataPadre;
    }

    public Boolean getFoglia() {
        return foglia;
    }

    public void setFoglia(Boolean foglia) {
        this.foglia = foglia;
    }

    public List<PecStruttura> getPecStrutturaList() {
        return pecStrutturaList;
    }

    public void setPecStrutturaList(List<PecStruttura> pecStrutturaList) {
        this.pecStrutturaList = pecStrutturaList;
    }

    public Azienda getIdAzienda() {
        return idAzienda;
    }

    public void setIdAzienda(Azienda idAzienda) {
        this.idAzienda = idAzienda;
    }

    public List<Struttura> getStruttureFiglieList() {
        return struttureFiglieList;
    }

    public void setStruttureFiglieList(List<Struttura> struttureFiglieList) {
        this.struttureFiglieList = struttureFiglieList;
    }

    public Struttura getIdStrutturaPadre() {
        return idStrutturaPadre;
    }

    public void setIdStrutturaPadre(Struttura idStrutturaPadre) {
        this.idStrutturaPadre = idStrutturaPadre;
    }

    public List<Struttura> getStruttureSegretariateList() {
        return struttureSegretariateList;
    }

    public void setStruttureSegretariateList(List<Struttura> struttureSegretariateList) {
        this.struttureSegretariateList = struttureSegretariateList;
    }

    public Struttura getIdStrutturaSegreteria() {
        return idStrutturaSegreteria;
    }

    public void setIdStrutturaSegreteria(Struttura idStrutturaSegreteria) {
        this.idStrutturaSegreteria = idStrutturaSegreteria;
    }

    public List<StrutturaUnificata> getStrutturaUnificataDestinazioneList() {
        return strutturaUnificataDestinazioneList;
    }

    public void setStrutturaUnificataDestinazioneList(List<StrutturaUnificata> strutturaUnificataDestinazioneList) {
        this.strutturaUnificataDestinazioneList = strutturaUnificataDestinazioneList;
    }

    public List<StrutturaUnificata> getStrutturaUnificataSorgenteList() {
        return strutturaUnificataSorgenteList;
    }

    public void setStrutturaUnificataSorgenteList(List<StrutturaUnificata> strutturaUnificataSorgenteList) {
        this.strutturaUnificataSorgenteList = strutturaUnificataSorgenteList;
    }

    public List<UtenteStruttura> getUtenteStrutturaList() {
        return utenteStrutturaList;
    }

    public void setUtenteStrutturaList(List<UtenteStruttura> utenteStrutturaList) {
        this.utenteStrutturaList = utenteStrutturaList;
    }
    
    public Struttura getIdStrutturaReplicata() {
        return idStrutturaReplicata;
    }

    public void setIdStrutturaReplicata(Struttura idStrutturaReplicata) {
        this.idStrutturaReplicata = idStrutturaReplicata;
    }
    
    public List<PermessoEntitaStoredProcedure> getPermessi() {
        return permessi;
    }

    public void setPermessi(List<PermessoEntitaStoredProcedure> permessi) {
        this.permessi = permessi;
    }
    
    public Boolean getIsPermessoPecPrincipale() {
        return isPermessoPecPrincipale;
    }

    public void setIsPermessoPecPrincipale(Boolean isPermessoPecPrincipale) {
        this.isPermessoPecPrincipale = isPermessoPecPrincipale;
    }

    public Boolean getPropagaPermessoPec() {
        return propagaPermessoPec;
    }

    public void setPropagaPermessoPec(Boolean propagaPermessoPec) {
        this.propagaPermessoPec = propagaPermessoPec;
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
        if (!(object instanceof Struttura)) {
            return false;
        }
        Struttura other = (Struttura) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.bologna.ausl.model.entities.baborg.Struttura[ id=" + id + " ]";
    }

}

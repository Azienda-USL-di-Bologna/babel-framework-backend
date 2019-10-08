package it.bologna.ausl.model.entities.scrivania;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.bologna.ausl.model.entities.baborg.Azienda;
import it.bologna.ausl.model.entities.configuration.Applicazione;
import it.bologna.ausl.internauta.utils.jpa.tools.GenericArrayUserType;
import java.io.Serializable;
import java.time.LocalDateTime;
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
import javax.persistence.Table;
import javax.persistence.Transient;
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
 * @author fayssel
 */
@TypeDefs(
        {
            @TypeDef(name = "array", typeClass = GenericArrayUserType.class)
        }
)
@Entity
@Table(name = "menu", catalog = "internauta", schema = "scrivania")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Cacheable(false)
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @JoinColumn(name = "id_azienda", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Azienda idAzienda;
    
    @Basic(optional = false)
    @NotNull
    @JoinColumn(name = "id_applicazione", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Applicazione idApplicazione;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "descrizione")
    private String descrizione;
    
    @Size(max = 2147483647)
    @Column(name = "open_command")
    private String openCommand;
    @Column(name = "permessi_sufficienti", columnDefinition = "text[]")
    @Type(type = "array", parameters = @Parameter(name = "elements-type", value = GenericArrayUserType.TEXT_ELEMENT_TYPE))
    private String[] permessiSufficienti;
    
    @Column(name = "ruoli_sufficienti", columnDefinition = "text[]")
    @Type(type = "array", parameters = @Parameter(name = "elements-type", value = GenericArrayUserType.TEXT_ELEMENT_TYPE))
    private String[] ruoliSufficienti;
    
    @Column(name = "visibile_aziende", columnDefinition = "text[]")
    @Type(type = "array", parameters = @Parameter(name = "elements-type", value = GenericArrayUserType.TEXT_ELEMENT_TYPE))
    private String[] visibileAziende;
    
    @Basic(optional = false)
    @Column(name = "ordinale")
    private Integer ordinale;
         
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
    private String compiledUrl;
        
    public Menu() {
    }

    public Menu(Integer id) {
        this.id = id;
    }

    public Menu(Integer id, Azienda idAzienda, Applicazione idApplicazione, String descrizione) {
        this.id = id;
        this.idAzienda = idAzienda;
        this.idApplicazione = idApplicazione;
        this.descrizione = descrizione;
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

    public Applicazione getIdApplicazione() {
        return idApplicazione;
    }

    public void setIdApplicazione(Applicazione idApplicazione) {
        this.idApplicazione = idApplicazione;
    }
    
    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getOpenCommand() {
        return openCommand;
    }

    public void setOpenCommand(String openCommand) {
        this.openCommand = openCommand;
    }

    public String[] getPermessiSufficienti() {
        return permessiSufficienti;
    }

    public void setPermessiSufficienti(String[] permessiSufficienti) {
        this.permessiSufficienti = permessiSufficienti;
    }

    public String[] getRuoliSufficienti() {
        return ruoliSufficienti;
    }

    public void setRuoliSufficienti(String[] ruoliSufficienti) {
        this.ruoliSufficienti = ruoliSufficienti;
    }

    public String[] getVisibileAziende() {
        return visibileAziende;
    }

    public void setVisibileAziende(String[] visibileAziende) {
        this.visibileAziende = visibileAziende;
    }

    public Integer getOrdinale() {
        return ordinale;
    }

    public void setOrdinale(Integer ordinale) {
        this.ordinale = ordinale;
    }

    public String getCompiledUrl() {
        return compiledUrl;
    }

    public void setCompiledUrl(String compiledUrls) {
        this.compiledUrl = compiledUrls;
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
        if (!(object instanceof Menu)) {
            return false;
        }
        Menu other = (Menu) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.bologna.ausl.model.entities.scrivania.Menu[ id=" + id + " ]";
    }
}

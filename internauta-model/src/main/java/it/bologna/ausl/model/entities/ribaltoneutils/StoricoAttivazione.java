package it.bologna.ausl.model.entities.ribaltoneutils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import it.bologna.ausl.model.entities.baborg.Azienda;
import it.bologna.ausl.model.entities.baborg.Utente;
import java.time.LocalDateTime;
import javax.persistence.Cacheable;
import javax.persistence.Version;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author gusgus
 */
@Entity
@Table(name = "storico_attivazioni", catalog = "internauta", schema = "ribaltone_utils")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Cacheable(false)
public class StoricoAttivazione implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "id_azienda", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Azienda idAzienda;
    @Basic(optional = false)
    @NotNull
    @Column(name = "data_inserimento_riga")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataInserimentoRiga;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ribalta_internauta")
    private Boolean ribaltaInternauta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ribalta_argo")
    private Boolean ribaltaArgo;
    @JoinColumn(name = "id_utente", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Utente idUtente;
    @Size(max = 2147483647)
    @Column(name = "note")
    private String note;

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
 
    public StoricoAttivazione() {
    }

    public StoricoAttivazione(Integer id) {
        this.id = id;
    }

    public StoricoAttivazione(Integer id, Azienda idAzienda, LocalDateTime dataInserimentoRiga, boolean ribaltaInternauta, boolean ribaltaArgo, Utente idUtente) {
        this.id = id;
        this.idAzienda = idAzienda;
        this.dataInserimentoRiga = dataInserimentoRiga;
        this.ribaltaInternauta = ribaltaInternauta;
        this.ribaltaArgo = ribaltaArgo;
        this.idUtente = idUtente;
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

    public LocalDateTime getDataInserimentoRiga() {
        return dataInserimentoRiga;
    }

    public void setDataInserimentoRiga(LocalDateTime dataInserimentoRiga) {
        this.dataInserimentoRiga = dataInserimentoRiga;
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

    public Utente getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Utente idUtente) {
        this.idUtente = idUtente;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
        if (!(object instanceof StoricoAttivazione)) {
            return false;
        }
        StoricoAttivazione other = (StoricoAttivazione) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.bologna.ausl.model.entities.ribaltoneutils.StoricoAttivazioni[ id=" + id + " ]";
    }
    
}

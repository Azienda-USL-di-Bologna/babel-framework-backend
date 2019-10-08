/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.bologna.ausl.model.entities.logs;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Utente
 */
@Entity
@Table(name = "operazioni_versionate_krint", catalog = "internauta", schema = "logs")
public class OperazioneVersionataKrint implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "versione")
    private Integer versione;
    @Size(max = 2147483647)
    @Column(name = "template_descrizione_operazione")
    private String templateDescrizioneOperazione;
    @JoinColumn(name = "id_operazione", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private OperazioneKrint idOperazione;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idOperazioneVersionata")
    private List<Krint> krintList;

    public OperazioneKrint getIdOperazione() {
        return idOperazione;
    }

    
    public OperazioneVersionataKrint() {
    }

    
    public OperazioneVersionataKrint(Integer id, int versione, String templateDescrizioneOperazione) {
        this.id = id;
        this.versione = versione;
        this.templateDescrizioneOperazione = templateDescrizioneOperazione;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getVersione() {
        return versione;
    }

    public void setVersione(int versione) {
        this.versione = versione;
    }

    public String getTemplateDescrizioneOperazione() {
        return templateDescrizioneOperazione;
    }

    public void setTemplateDescrizioneOperazione(String templateDescrizioneOperazione) {
        this.templateDescrizioneOperazione = templateDescrizioneOperazione;
    }

    public void setIdOperazione(OperazioneKrint idOperazione) {
        this.idOperazione = idOperazione;
    }

    public List<Krint> getKrintList() {
        return krintList;
    }

    public void setKrintList(List<Krint> krintList) {
        this.krintList = krintList;
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
        if (!(object instanceof OperazioneVersionataKrint)) {
            return false;
        }
        OperazioneVersionataKrint other = (OperazioneVersionataKrint) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.bologna.ausl.model.entities.logs.OperazioniVersionateKrint[ id=" + id + " ]";
    }
    
}

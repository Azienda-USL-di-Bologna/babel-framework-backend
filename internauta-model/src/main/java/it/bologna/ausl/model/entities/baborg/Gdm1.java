package it.bologna.ausl.model.entities.baborg;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author gdm
 */
@Entity
@Table(name = "gdm1", catalog = "internauta", schema = "baborg")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Cacheable(false)
public class Gdm1 implements Serializable {
    
    public enum UtenteRole implements GrantedAuthority{
        ROLE_ADMIN(true,true),
        ROLE_USER(true,true);

        private boolean canAccessBackOffice;
        private boolean canAccessFrontOffice;


        UtenteRole(boolean canAccessBackOffice, boolean canAccessFrontOffice) {
            this.canAccessBackOffice = canAccessBackOffice;
            this.canAccessFrontOffice = canAccessFrontOffice;
        }

        @Override
        public String getAuthority() {
            return this.name();
        }

        public boolean isCanAccessBackOffice() {
            return canAccessBackOffice;
        }

        public boolean isCanAccessFrontOffice() {
            return canAccessFrontOffice;
        }
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
        
    @Size(max = 2147483647)
    @Column(name = "descrizione")
    private String descrizione;
    
//    @Column(name = "UTENTE_ROLE", nullable = false, length = 2000)
//    @Enumerated(EnumType.STRING)
//    private UtenteRole utenteRole;
    @Size(max = 2147483647)
    @Column(name = "necessario", nullable = false)
    @NotNull
    private String necessario;
    
    @Version()
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime version;
    
    @OneToMany(mappedBy = "idGdm1", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true, targetEntity = Gdm2.class)
    @JsonBackReference(value = "gdm2List")
    private List<Gdm2> gdm2List;
   
    public Gdm1() {
    }

    public Gdm1(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    public String getNecessario() {
        return necessario;
    }
    

    public void setNecessario(String necessario) {
        this.necessario = necessario;
    }
//
    public List<Gdm2> getGdm2List() {
        return gdm2List;
    }

    public void setGdm2List(List<Gdm2> gdm2List) {
        this.gdm2List = gdm2List;
    }

    public LocalDateTime getVersion() {
        return version;
    }

    public void setVersion(LocalDateTime version) {
        this.version = version;
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
        if (!(object instanceof Gdm1)) {
            return false;
        }
        Gdm1 other = (Gdm1) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "it.bologna.ausl.model.entities.baborg.Gdm1[ id=" + id + " ]";
    }
    
}

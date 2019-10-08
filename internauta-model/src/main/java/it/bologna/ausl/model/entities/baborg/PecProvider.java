package it.bologna.ausl.model.entities.baborg;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author solidus83
 */
@Entity
@Table(name = "pec_provider", catalog = "internauta", schema = "baborg")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PecProvider implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "descrizione", columnDefinition = "text")
    private String descrizione;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pec")
    private Boolean pec;
    @Basic(optional = false)
    @NotNull
    @Column(name = "host", columnDefinition = "text")
    private String host;
    @Basic(optional = false)
    @NotNull
    @Column(name = "port")
    private Integer port;
    @Basic(optional = false)
    @NotNull
    @Column(name = "protocol", columnDefinition = "text")
    private String protocol;
    @Basic(optional = false)
    @NotNull
    @Column(name = "host_out", columnDefinition = "text")
    private String hostOut;
    @Basic(optional = false)
    @NotNull
    @Column(name = "port_out")
    private Integer portOut;
    @Basic(optional = false)
    @NotNull
    @Column(name = "protocol_out", columnDefinition = "text")
    private String protocolOut;
    @OneToMany(mappedBy = "idPecProvider", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonBackReference(value = "pecList")
    private List<Pec> pecList;
        
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
    
    public PecProvider() {
    }

    public PecProvider(Integer id) {
        this.id = id;
    }

    public PecProvider(Integer id, String descrizione, Boolean pec, String host, Integer port, String protocol, String hostOut, Integer portOut, String protocolOut) {
        this.id = id;
        this.descrizione = descrizione;
        this.pec = pec;
        this.host = host;
        this.port = port;
        this.protocol = protocol;
        this.hostOut = hostOut;
        this.portOut = portOut;
        this.protocolOut = protocolOut;
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

    public Boolean getPec() {
        return pec;
    }

    public void setPec(Boolean pec) {
        this.pec = pec;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHostOut() {
        return hostOut;
    }

    public void setHostOut(String hostOut) {
        this.hostOut = hostOut;
    }

    public Integer getPortOut() {
        return portOut;
    }

    public void setPortOut(Integer portOut) {
        this.portOut = portOut;
    }

    public String getProtocolOut() {
        return protocolOut;
    }

    public void setProtocolOut(String protocolOut) {
        this.protocolOut = protocolOut;
    }

    public List<Pec> getPecList() {
        return pecList;
    }

    public void setPecList(List<Pec> pecList) {
        this.pecList = pecList;
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
        if (!(object instanceof PecProvider)) {
            return false;
        }
        PecProvider other = (PecProvider) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PecProvider{" + "id=" + id + ", descrizione=" + descrizione + ", pec=" + pec + ", host=" + host + ", port=" + port + ", protocol=" + protocol + ", hostOut=" + hostOut + ", portOut=" + portOut + ", protocolOut=" + protocolOut + '}';
    }

}

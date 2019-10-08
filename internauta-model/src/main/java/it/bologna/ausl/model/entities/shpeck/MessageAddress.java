package it.bologna.ausl.model.entities.shpeck;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import javax.persistence.Version;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Salo
 */
@Entity
@Table(name = "messages_addresses", catalog = "internauta", schema = "shpeck")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Cacheable(false)
public class MessageAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    public static enum AddressRoleType {
        FROM, TO, CC
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "address", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Address idAddress;

    @JoinColumn(name = "message", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Message idMessage;

    @Column(name = "address_role")
    private String addressRole;

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

    public MessageAddress() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Address getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(Address address) {
        this.idAddress = address;
    }

    public Message getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(Message idMessage) {
        this.idMessage = idMessage;
    }

    public AddressRoleType getAddressRole() {
        return AddressRoleType.valueOf(addressRole);
    }

    public void setAddressRole(AddressRoleType roleType) {
        this.addressRole = roleType.toString();
    }

    @Override
    public String toString() {
        return "MessageAddress{" + "id=" + id + ", idAddress=" + idAddress + ", idMessage=" + idMessage + ", addressRole=" + addressRole + '}';
    }

    @Override
    public MessageAddress clone() throws CloneNotSupportedException {
        MessageAddress ma = new MessageAddress();
        ma.setAddressRole(this.getAddressRole());
        ma.setIdAddress(this.getIdAddress());
        //ma.setIdMessage(this.getIdMessage());

        return ma;
    }
}

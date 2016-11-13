/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author JuanPablo
 */
@Entity
@Table(name = "BILL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bill.findAll", query = "SELECT b FROM Bill b")})
public class Bill implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Lob
    @Column(name = "IMAGE")
    private String image;
    @Basic(optional = false)
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @Basic(optional = false)
    @Column(name = "TITTLE")
    private String tittle;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @JoinColumns({
        @JoinColumn(name = "ID_RESPONSABLE", referencedColumnName = "USER_EMAIL")
        , @JoinColumn(name = "ID_GROUP", referencedColumnName = "GROUP_ID")})
    @ManyToOne(optional = false)
    private UserXGroup userXGroup;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bill")
    private List<Deuda> deudaList;

    public Bill() {
    }

    public Bill(BigDecimal id) {
        this.id = id;
    }

    public Bill(BigDecimal id, String image, BigDecimal amount, String tittle) {
        this.id = id;
        this.image = image;
        this.amount = amount;
        this.tittle = tittle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public UserXGroup getUserXGroup() {
        return userXGroup;
    }

    public void setUserXGroup(UserXGroup userXGroup) {
        this.userXGroup = userXGroup;
    }

    @XmlTransient
    public List<Deuda> getDeudaList() {
        return deudaList;
    }

    public void setDeudaList(List<Deuda> deudaList) {
        this.deudaList = deudaList;
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
        if (!(object instanceof Bill)) {
            return false;
        }
        Bill other = (Bill) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Bill[ id=" + id + " ]";
    }
    
}

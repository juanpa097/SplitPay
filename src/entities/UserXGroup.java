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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
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
@Table(name = "USER_X_GROUP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserXGroup.findAll", query = "SELECT u FROM UserXGroup u")})
public class UserXGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected UserXGroupPK userXGroupPK;
    @Basic(optional = false)
    @Column(name = "BALANCE")
    private BigDecimal balance;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userXGroup")
    private List<Bill> billList;
    @JoinColumn(name = "GROUP_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Grupo grupo;
    @JoinColumn(name = "USER_EMAIL", referencedColumnName = "EMAIL", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public UserXGroup() {
    }

    public UserXGroup(UserXGroupPK userXGroupPK) {
        this.userXGroupPK = userXGroupPK;
    }

    public UserXGroup(UserXGroupPK userXGroupPK, BigDecimal balance) {
        this.userXGroupPK = userXGroupPK;
        this.balance = balance;
    }

    public UserXGroup(String userEmail, BigDecimal groupId) {
        this.userXGroupPK = new UserXGroupPK(userEmail, groupId);
    }

    public UserXGroupPK getUserXGroupPK() {
        return userXGroupPK;
    }

    public void setUserXGroupPK(UserXGroupPK userXGroupPK) {
        this.userXGroupPK = userXGroupPK;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @XmlTransient
    public List<Bill> getBillList() {
        return billList;
    }

    public void setBillList(List<Bill> billList) {
        this.billList = billList;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userXGroupPK != null ? userXGroupPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserXGroup)) {
            return false;
        }
        UserXGroup other = (UserXGroup) object;
        if ((this.userXGroupPK == null && other.userXGroupPK != null) || (this.userXGroupPK != null && !this.userXGroupPK.equals(other.userXGroupPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.UserXGroup[ userXGroupPK=" + userXGroupPK + " ]";
    }
    
}

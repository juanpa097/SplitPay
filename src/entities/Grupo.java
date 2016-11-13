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
@Table(name = "GRUPO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grupo.findAll", query = "SELECT g FROM Grupo g")})
public class Grupo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "NAME")
    private String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID")
    private BigDecimal id;
    @JoinColumn(name = "LEADER_EMAIL", referencedColumnName = "EMAIL")
    @ManyToOne(optional = false)
    private Usuario leaderEmail;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupo")
    private List<UserXGroup> userXGroupList;

    public Grupo() {
    }

    public Grupo(BigDecimal id) {
        this.id = id;
    }

    public Grupo(BigDecimal id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public Usuario getLeaderEmail() {
        return leaderEmail;
    }

    public void setLeaderEmail(Usuario leaderEmail) {
        this.leaderEmail = leaderEmail;
    }

    @XmlTransient
    public List<UserXGroup> getUserXGroupList() {
        return userXGroupList;
    }

    public void setUserXGroupList(List<UserXGroup> userXGroupList) {
        this.userXGroupList = userXGroupList;
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
        if (!(object instanceof Grupo)) {
            return false;
        }
        Grupo other = (Grupo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Grupo[ id=" + id + " ]";
    }
    
}

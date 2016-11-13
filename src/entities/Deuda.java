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
@Table(name = "DEUDA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Deuda.findAll", query = "SELECT d FROM Deuda d")})
public class Deuda implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DeudaPK deudaPK;
    @Basic(optional = false)
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deuda")
    private List<Transaction> transactionList;
    @JoinColumn(name = "BILL_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Bill bill;
    @JoinColumn(name = "DEUDOR", referencedColumnName = "EMAIL", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public Deuda() {
    }

    public Deuda(DeudaPK deudaPK) {
        this.deudaPK = deudaPK;
    }

    public Deuda(DeudaPK deudaPK, BigDecimal amount) {
        this.deudaPK = deudaPK;
        this.amount = amount;
    }

    public Deuda(String deudor, BigDecimal billId) {
        this.deudaPK = new DeudaPK(deudor, billId);
    }

    public DeudaPK getDeudaPK() {
        return deudaPK;
    }

    public void setDeudaPK(DeudaPK deudaPK) {
        this.deudaPK = deudaPK;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @XmlTransient
    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
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
        hash += (deudaPK != null ? deudaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Deuda)) {
            return false;
        }
        Deuda other = (Deuda) object;
        if ((this.deudaPK == null && other.deudaPK != null) || (this.deudaPK != null && !this.deudaPK.equals(other.deudaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Deuda[ deudaPK=" + deudaPK + " ]";
    }
    
}

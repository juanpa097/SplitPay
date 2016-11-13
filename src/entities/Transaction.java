/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JuanPablo
 */
@Entity
@Table(name = "TRANSACTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transaction.findAll", query = "SELECT t FROM Transaction t")})
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "AMOUNT")
    private BigDecimal amount;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_TRANSACTION")
    private BigDecimal idTransaction;
    @JoinColumns({
        @JoinColumn(name = "BILL_ID", referencedColumnName = "BILL_ID")
        , @JoinColumn(name = "DEUDOR_ID", referencedColumnName = "DEUDOR")})
    @ManyToOne(optional = false)
    private Deuda deuda;

    public Transaction() {
    }

    public Transaction(BigDecimal idTransaction) {
        this.idTransaction = idTransaction;
    }

    public Transaction(BigDecimal idTransaction, BigDecimal amount) {
        this.idTransaction = idTransaction;
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(BigDecimal idTransaction) {
        this.idTransaction = idTransaction;
    }

    public Deuda getDeuda() {
        return deuda;
    }

    public void setDeuda(Deuda deuda) {
        this.deuda = deuda;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTransaction != null ? idTransaction.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transaction)) {
            return false;
        }
        Transaction other = (Transaction) object;
        if ((this.idTransaction == null && other.idTransaction != null) || (this.idTransaction != null && !this.idTransaction.equals(other.idTransaction))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Transaction[ idTransaction=" + idTransaction + " ]";
    }
    
}

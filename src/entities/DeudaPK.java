/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author JuanPablo
 */
@Embeddable
public class DeudaPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "DEUDOR")
    private String deudor;
    @Basic(optional = false)
    @Column(name = "BILL_ID")
    private BigDecimal billId;

    public DeudaPK() {
    }

    public DeudaPK(String deudor, BigDecimal billId) {
        this.deudor = deudor;
        this.billId = billId;
    }

    public String getDeudor() {
        return deudor;
    }

    public void setDeudor(String deudor) {
        this.deudor = deudor;
    }

    public BigDecimal getBillId() {
        return billId;
    }

    public void setBillId(BigDecimal billId) {
        this.billId = billId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (deudor != null ? deudor.hashCode() : 0);
        hash += (billId != null ? billId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeudaPK)) {
            return false;
        }
        DeudaPK other = (DeudaPK) object;
        if ((this.deudor == null && other.deudor != null) || (this.deudor != null && !this.deudor.equals(other.deudor))) {
            return false;
        }
        if ((this.billId == null && other.billId != null) || (this.billId != null && !this.billId.equals(other.billId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.DeudaPK[ deudor=" + deudor + ", billId=" + billId + " ]";
    }
    
}

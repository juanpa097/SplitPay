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
public class UserXGroupPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "USER_EMAIL")
    private String userEmail;
    @Basic(optional = false)
    @Column(name = "GROUP_ID")
    private BigDecimal groupId;

    public UserXGroupPK() {
    }

    public UserXGroupPK(String userEmail, BigDecimal groupId) {
        this.userEmail = userEmail;
        this.groupId = groupId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public BigDecimal getGroupId() {
        return groupId;
    }

    public void setGroupId(BigDecimal groupId) {
        this.groupId = groupId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userEmail != null ? userEmail.hashCode() : 0);
        hash += (groupId != null ? groupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserXGroupPK)) {
            return false;
        }
        UserXGroupPK other = (UserXGroupPK) object;
        if ((this.userEmail == null && other.userEmail != null) || (this.userEmail != null && !this.userEmail.equals(other.userEmail))) {
            return false;
        }
        if ((this.groupId == null && other.groupId != null) || (this.groupId != null && !this.groupId.equals(other.groupId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.UserXGroupPK[ userEmail=" + userEmail + ", groupId=" + groupId + " ]";
    }
    
}

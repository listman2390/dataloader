/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author listman
 */
@Entity
@Table(name = "stores")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stores.findAll", query = "SELECT s FROM Stores s"),
    @NamedQuery(name = "Stores.findById", query = "SELECT s FROM Stores s WHERE s.id = :id"),
    @NamedQuery(name = "Stores.findByName", query = "SELECT s FROM Stores s WHERE s.name = :name"),
    @NamedQuery(name = "Stores.findByCode", query = "SELECT s FROM Stores s WHERE s.code = :code"),
    @NamedQuery(name = "Stores.findByAddress", query = "SELECT s FROM Stores s WHERE s.address = :address"),
    @NamedQuery(name = "Stores.findByClickCollect", query = "SELECT s FROM Stores s WHERE s.clickCollect = :clickCollect"),
    @NamedQuery(name = "Stores.findByCreatedAt", query = "SELECT s FROM Stores s WHERE s.createdAt = :createdAt"),
    @NamedQuery(name = "Stores.findByUpdatedAt", query = "SELECT s FROM Stores s WHERE s.updatedAt = :updatedAt"),
    @NamedQuery(name = "Stores.findByDeletedAt", query = "SELECT s FROM Stores s WHERE s.deletedAt = :deletedAt")})
public class Stores implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "code")
    private String code;
    @Basic(optional = false)
    @Column(name = "address")
    private String address;
    @Basic(optional = false)
    @Column(name = "click_collect")
    private boolean clickCollect;
    @Basic(optional = false)
    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Basic(optional = false)
    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "storeId")
    private List<Municipalities> municipalitiesList;

    public Stores() {
    }

    public Stores(Integer id) {
        this.id = id;
    }

    public Stores(Integer id, String name, String code, String address, boolean clickCollect, Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.address = address;
        this.clickCollect = clickCollect;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean getClickCollect() {
        return clickCollect;
    }

    public void setClickCollect(boolean clickCollect) {
        this.clickCollect = clickCollect;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    @XmlTransient
    public List<Municipalities> getMunicipalitiesList() {
        return municipalitiesList;
    }

    public void setMunicipalitiesList(List<Municipalities> municipalitiesList) {
        this.municipalitiesList = municipalitiesList;
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
        if (!(object instanceof Stores)) {
            return false;
        }
        Stores other = (Stores) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.Stores[ id=" + id + " ]";
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author listman
 */
@Entity
@Table(name = "municipalities")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Municipalities.findAll", query = "SELECT m FROM Municipalities m"),
    @NamedQuery(name = "Municipalities.findById", query = "SELECT m FROM Municipalities m WHERE m.id = :id"),
    @NamedQuery(name = "Municipalities.findByName", query = "SELECT m FROM Municipalities m WHERE m.name = :name"),
    @NamedQuery(name = "Municipalities.findByDeletedAt", query = "SELECT m FROM Municipalities m WHERE m.deletedAt = :deletedAt")})
public class Municipalities implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;
    @JoinColumn(name = "region_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Regions regionId;
    @JoinColumn(name = "store_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Stores storeId;

    public Municipalities() {
    }

    public Municipalities(Integer id) {
        this.id = id;
    }

    public Municipalities(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Regions getRegionId() {
        return regionId;
    }

    public void setRegionId(Regions regionId) {
        this.regionId = regionId;
    }

    public Stores getStoreId() {
        return storeId;
    }

    public void setStoreId(Stores storeId) {
        this.storeId = storeId;
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
        if (!(object instanceof Municipalities)) {
            return false;
        }
        Municipalities other = (Municipalities) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.Municipalities[ id=" + id + " ]";
    }
    
}

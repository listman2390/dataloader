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
@Table(name = "regions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Regions.findAll", query = "SELECT r FROM Regions r"),
    @NamedQuery(name = "Regions.findById", query = "SELECT r FROM Regions r WHERE r.id = :id"),
    @NamedQuery(name = "Regions.findByName", query = "SELECT r FROM Regions r WHERE r.name = :name"),
    @NamedQuery(name = "Regions.findByDeletedAt", query = "SELECT r FROM Regions r WHERE r.deletedAt = :deletedAt"),
    @NamedQuery(name = "Regions.findByPriority", query = "SELECT r FROM Regions r WHERE r.priority = :priority")})
public class Regions implements Serializable {
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
    @Column(name = "priority")
    private Integer priority;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "regionId")
    private List<Municipalities> municipalitiesList;

    public Regions() {
    }

    public Regions(Integer id) {
        this.id = id;
    }

    public Regions(Integer id, String name) {
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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
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
        if (!(object instanceof Regions)) {
            return false;
        }
        Regions other = (Regions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.Regions[ id=" + id + " ]";
    }
    
}

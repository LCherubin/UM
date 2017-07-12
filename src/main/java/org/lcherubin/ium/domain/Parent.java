package org.lcherubin.ium.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import org.lcherubin.ium.domain.enumeration.Sexe;

/**
 * A Parent.
 */
@Entity
@Table(name = "parent")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "parent")
public class Parent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "prenoms", nullable = false)
    private String prenoms;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sexe", nullable = false)
    private Sexe sexe;

    @NotNull
    @Column(name = "lien", nullable = false)
    private String lien;

    @OneToOne
    @JoinColumn(unique = true)
    private Contact contact;

    @OneToMany(mappedBy = "parent")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Etudiant> parents = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrenoms() {
        return prenoms;
    }

    public Parent prenoms(String prenoms) {
        this.prenoms = prenoms;
        return this;
    }

    public void setPrenoms(String prenoms) {
        this.prenoms = prenoms;
    }

    public String getNom() {
        return nom;
    }

    public Parent nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public Parent sexe(Sexe sexe) {
        this.sexe = sexe;
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public String getLien() {
        return lien;
    }

    public Parent lien(String lien) {
        this.lien = lien;
        return this;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public Contact getContact() {
        return contact;
    }

    public Parent contact(Contact contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Set<Etudiant> getParents() {
        return parents;
    }

    public Parent parents(Set<Etudiant> etudiants) {
        this.parents = etudiants;
        return this;
    }

    public Parent addParent(Etudiant etudiant) {
        this.parents.add(etudiant);
        etudiant.setParent(this);
        return this;
    }

    public Parent removeParent(Etudiant etudiant) {
        this.parents.remove(etudiant);
        etudiant.setParent(null);
        return this;
    }

    public void setParents(Set<Etudiant> etudiants) {
        this.parents = etudiants;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Parent parent = (Parent) o;
        if (parent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), parent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Parent{" +
            "id=" + getId() +
            ", prenoms='" + getPrenoms() + "'" +
            ", nom='" + getNom() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", lien='" + getLien() + "'" +
            "}";
    }
}

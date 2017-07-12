package org.lcherubin.ium.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import org.lcherubin.ium.domain.enumeration.Sexe;

/**
 * A Enseignant.
 */
@Entity
@Table(name = "enseignant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "enseignant")
public class Enseignant implements Serializable {

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
    @Column(name = "date_naissance", nullable = false)
    private LocalDate dateNaissance;

    @NotNull
    @Column(name = "lieu_naisance", nullable = false)
    private String lieuNaisance;

    @Column(name = "titre")
    private String titre;

    @Column(name = "date_enregistrement")
    private LocalDate dateEnregistrement;

    @OneToOne
    @JoinColumn(unique = true)
    private Contact contact;

    @OneToMany(mappedBy = "enseignant")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Cours> cours = new HashSet<>();

    @OneToMany(mappedBy = "enseignant")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AnneeUniversitaire> annees = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrenoms() {
        return prenoms;
    }

    public Enseignant prenoms(String prenoms) {
        this.prenoms = prenoms;
        return this;
    }

    public void setPrenoms(String prenoms) {
        this.prenoms = prenoms;
    }

    public String getNom() {
        return nom;
    }

    public Enseignant nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public Enseignant sexe(Sexe sexe) {
        this.sexe = sexe;
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public Enseignant dateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
        return this;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaisance() {
        return lieuNaisance;
    }

    public Enseignant lieuNaisance(String lieuNaisance) {
        this.lieuNaisance = lieuNaisance;
        return this;
    }

    public void setLieuNaisance(String lieuNaisance) {
        this.lieuNaisance = lieuNaisance;
    }

    public String getTitre() {
        return titre;
    }

    public Enseignant titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public LocalDate getDateEnregistrement() {
        return dateEnregistrement;
    }

    public Enseignant dateEnregistrement(LocalDate dateEnregistrement) {
        this.dateEnregistrement = dateEnregistrement;
        return this;
    }

    public void setDateEnregistrement(LocalDate dateEnregistrement) {
        this.dateEnregistrement = dateEnregistrement;
    }

    public Contact getContact() {
        return contact;
    }

    public Enseignant contact(Contact contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Set<Cours> getCours() {
        return cours;
    }

    public Enseignant cours(Set<Cours> cours) {
        this.cours = cours;
        return this;
    }

    public Enseignant addCours(Cours cours) {
        this.cours.add(cours);
        cours.setEnseignant(this);
        return this;
    }

    public Enseignant removeCours(Cours cours) {
        this.cours.remove(cours);
        cours.setEnseignant(null);
        return this;
    }

    public void setCours(Set<Cours> cours) {
        this.cours = cours;
    }

    public Set<AnneeUniversitaire> getAnnees() {
        return annees;
    }

    public Enseignant annees(Set<AnneeUniversitaire> anneeUniversitaires) {
        this.annees = anneeUniversitaires;
        return this;
    }

    public Enseignant addAnnees(AnneeUniversitaire anneeUniversitaire) {
        this.annees.add(anneeUniversitaire);
        anneeUniversitaire.setEnseignant(this);
        return this;
    }

    public Enseignant removeAnnees(AnneeUniversitaire anneeUniversitaire) {
        this.annees.remove(anneeUniversitaire);
        anneeUniversitaire.setEnseignant(null);
        return this;
    }

    public void setAnnees(Set<AnneeUniversitaire> anneeUniversitaires) {
        this.annees = anneeUniversitaires;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Enseignant enseignant = (Enseignant) o;
        if (enseignant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), enseignant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Enseignant{" +
            "id=" + getId() +
            ", prenoms='" + getPrenoms() + "'" +
            ", nom='" + getNom() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", lieuNaisance='" + getLieuNaisance() + "'" +
            ", titre='" + getTitre() + "'" +
            ", dateEnregistrement='" + getDateEnregistrement() + "'" +
            "}";
    }
}

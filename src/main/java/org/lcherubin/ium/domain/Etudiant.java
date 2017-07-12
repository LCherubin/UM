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

import org.lcherubin.ium.domain.enumeration.Niveau;

import org.lcherubin.ium.domain.enumeration.Statut;

import org.lcherubin.ium.domain.enumeration.StatInscription;

/**
 * A Etudiant.
 */
@Entity
@Table(name = "etudiant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "etudiant")
public class Etudiant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "matricule", nullable = false)
    private Long matricule;

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

    @NotNull
    @Column(name = "promotion", nullable = false)
    private String promotion;

    @Enumerated(EnumType.STRING)
    @Column(name = "niveau")
    private Niveau niveau;

    @Column(name = "date_inscription")
    private LocalDate dateInscription;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut")
    private Statut statut;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut_inscription")
    private StatInscription statutInscription;

    @OneToOne
    @JoinColumn(unique = true)
    private Contact contact;

    @ManyToOne
    @JoinColumn(unique = true)
    private Filiere filier;

    @OneToMany(mappedBy = "etudiant")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AnneeUniversitaire> annees = new HashSet<>();

    @OneToMany(mappedBy = "etudiant")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Contribution> contributions = new HashSet<>();

    @ManyToOne
    private Parent parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMatricule() {
        return matricule;
    }

    public Etudiant matricule(Long matricule) {
        this.matricule = matricule;
        return this;
    }

    public void setMatricule(Long matricule) {
        this.matricule = matricule;
    }

    public String getPrenoms() {
        return prenoms;
    }

    public Etudiant prenoms(String prenoms) {
        this.prenoms = prenoms;
        return this;
    }

    public void setPrenoms(String prenoms) {
        this.prenoms = prenoms;
    }

    public String getNom() {
        return nom;
    }

    public Etudiant nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public Etudiant sexe(Sexe sexe) {
        this.sexe = sexe;
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public Etudiant dateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
        return this;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaisance() {
        return lieuNaisance;
    }

    public Etudiant lieuNaisance(String lieuNaisance) {
        this.lieuNaisance = lieuNaisance;
        return this;
    }

    public void setLieuNaisance(String lieuNaisance) {
        this.lieuNaisance = lieuNaisance;
    }

    public String getPromotion() {
        return promotion;
    }

    public Etudiant promotion(String promotion) {
        this.promotion = promotion;
        return this;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public Etudiant niveau(Niveau niveau) {
        this.niveau = niveau;
        return this;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public LocalDate getDateInscription() {
        return dateInscription;
    }

    public Etudiant dateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
        return this;
    }

    public void setDateInscription(LocalDate dateInscription) {
        this.dateInscription = dateInscription;
    }

    public Statut getStatut() {
        return statut;
    }

    public Etudiant statut(Statut statut) {
        this.statut = statut;
        return this;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public StatInscription getStatutInscription() {
        return statutInscription;
    }

    public Etudiant statutInscription(StatInscription statutInscription) {
        this.statutInscription = statutInscription;
        return this;
    }

    public void setStatutInscription(StatInscription statutInscription) {
        this.statutInscription = statutInscription;
    }

    public Contact getContact() {
        return contact;
    }

    public Etudiant contact(Contact contact) {
        this.contact = contact;
        return this;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Filiere getFilier() {
        return filier;
    }

    public Etudiant filier(Filiere filiere) {
        this.filier = filiere;
        return this;
    }

    public void setFilier(Filiere filiere) {
        this.filier = filiere;
    }

    public Set<AnneeUniversitaire> getAnnees() {
        return annees;
    }

    public Etudiant annees(Set<AnneeUniversitaire> anneeUniversitaires) {
        this.annees = anneeUniversitaires;
        return this;
    }

    public Etudiant addAnnees(AnneeUniversitaire anneeUniversitaire) {
        this.annees.add(anneeUniversitaire);
        anneeUniversitaire.setEtudiant(this);
        return this;
    }

    public Etudiant removeAnnees(AnneeUniversitaire anneeUniversitaire) {
        this.annees.remove(anneeUniversitaire);
        anneeUniversitaire.setEtudiant(null);
        return this;
    }

    public void setAnnees(Set<AnneeUniversitaire> anneeUniversitaires) {
        this.annees = anneeUniversitaires;
    }

    public Set<Contribution> getContributions() {
        return contributions;
    }

    public Etudiant contributions(Set<Contribution> contributions) {
        this.contributions = contributions;
        return this;
    }

    public Etudiant addContribution(Contribution contribution) {
        this.contributions.add(contribution);
        contribution.setEtudiant(this);
        return this;
    }

    public Etudiant removeContribution(Contribution contribution) {
        this.contributions.remove(contribution);
        contribution.setEtudiant(null);
        return this;
    }

    public void setContributions(Set<Contribution> contributions) {
        this.contributions = contributions;
    }

    public Parent getParent() {
        return parent;
    }

    public Etudiant parent(Parent parent) {
        this.parent = parent;
        return this;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Etudiant etudiant = (Etudiant) o;
        if (etudiant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), etudiant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Etudiant{" +
            "id=" + getId() +
            ", matricule='" + getMatricule() + "'" +
            ", prenoms='" + getPrenoms() + "'" +
            ", nom='" + getNom() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", dateNaissance='" + getDateNaissance() + "'" +
            ", lieuNaisance='" + getLieuNaisance() + "'" +
            ", promotion='" + getPromotion() + "'" +
            ", niveau='" + getNiveau() + "'" +
            ", dateInscription='" + getDateInscription() + "'" +
            ", statut='" + getStatut() + "'" +
            ", statutInscription='" + getStatutInscription() + "'" +
            "}";
    }
}

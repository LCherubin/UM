package org.lcherubin.ium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import org.lcherubin.ium.domain.enumeration.Niveau;

/**
 * A Contribution.
 */
@Entity
@Table(name = "contribution")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "contribution")
public class Contribution implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "niveau", nullable = false)
    private Niveau niveau;

    @Column(name = "frais_insciprion")
    private Double fraisInsciprion;

    @Column(name = "montant_total")
    private Double montantTotal;

    @Column(name = "montant_tranche_1")
    private Double montantTranche1;

    @Column(name = "montant_tranche_2")
    private Double montantTranche2;

    @Column(name = "montant_payer_1")
    private Double montantPayer1;

    @Column(name = "montant_payer_2")
    private Double montantPayer2;

    @Column(name = "total_payer")
    private Double totalPayer;

    @Column(name = "date_payment")
    private LocalDate datePayment;

    @OneToOne
    @JoinColumn(unique = true)
    private AnneeUniversitaire annees;

    @ManyToOne
    private Etudiant etudiant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public Contribution niveau(Niveau niveau) {
        this.niveau = niveau;
        return this;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public Double getFraisInsciprion() {
        return fraisInsciprion;
    }

    public Contribution fraisInsciprion(Double fraisInsciprion) {
        this.fraisInsciprion = fraisInsciprion;
        return this;
    }

    public void setFraisInsciprion(Double fraisInsciprion) {
        this.fraisInsciprion = fraisInsciprion;
    }

    public Double getMontantTotal() {
        return montantTotal;
    }

    public Contribution montantTotal(Double montantTotal) {
        this.montantTotal = montantTotal;
        return this;
    }

    public void setMontantTotal(Double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public Double getMontantTranche1() {
        return montantTranche1;
    }

    public Contribution montantTranche1(Double montantTranche1) {
        this.montantTranche1 = montantTranche1;
        return this;
    }

    public void setMontantTranche1(Double montantTranche1) {
        this.montantTranche1 = montantTranche1;
    }

    public Double getMontantTranche2() {
        return montantTranche2;
    }

    public Contribution montantTranche2(Double montantTranche2) {
        this.montantTranche2 = montantTranche2;
        return this;
    }

    public void setMontantTranche2(Double montantTranche2) {
        this.montantTranche2 = montantTranche2;
    }

    public Double getMontantPayer1() {
        return montantPayer1;
    }

    public Contribution montantPayer1(Double montantPayer1) {
        this.montantPayer1 = montantPayer1;
        return this;
    }

    public void setMontantPayer1(Double montantPayer1) {
        this.montantPayer1 = montantPayer1;
    }

    public Double getMontantPayer2() {
        return montantPayer2;
    }

    public Contribution montantPayer2(Double montantPayer2) {
        this.montantPayer2 = montantPayer2;
        return this;
    }

    public void setMontantPayer2(Double montantPayer2) {
        this.montantPayer2 = montantPayer2;
    }

    public Double getTotalPayer() {
        return totalPayer;
    }

    public Contribution totalPayer(Double totalPayer) {
        this.totalPayer = totalPayer;
        return this;
    }

    public void setTotalPayer(Double totalPayer) {
        this.totalPayer = totalPayer;
    }

    public LocalDate getDatePayment() {
        return datePayment;
    }

    public Contribution datePayment(LocalDate datePayment) {
        this.datePayment = datePayment;
        return this;
    }

    public void setDatePayment(LocalDate datePayment) {
        this.datePayment = datePayment;
    }

    public AnneeUniversitaire getAnnees() {
        return annees;
    }

    public Contribution annees(AnneeUniversitaire anneeUniversitaire) {
        this.annees = anneeUniversitaire;
        return this;
    }

    public void setAnnees(AnneeUniversitaire anneeUniversitaire) {
        this.annees = anneeUniversitaire;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public Contribution etudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
        return this;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contribution contribution = (Contribution) o;
        if (contribution.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contribution.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Contribution{" +
            "id=" + getId() +
            ", niveau='" + getNiveau() + "'" +
            ", fraisInsciprion='" + getFraisInsciprion() + "'" +
            ", montantTotal='" + getMontantTotal() + "'" +
            ", montantTranche1='" + getMontantTranche1() + "'" +
            ", montantTranche2='" + getMontantTranche2() + "'" +
            ", montantPayer1='" + getMontantPayer1() + "'" +
            ", montantPayer2='" + getMontantPayer2() + "'" +
            ", totalPayer='" + getTotalPayer() + "'" +
            ", datePayment='" + getDatePayment() + "'" +
            "}";
    }
}

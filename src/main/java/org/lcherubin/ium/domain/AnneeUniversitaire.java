package org.lcherubin.ium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AnneeUniversitaire.
 */
@Entity
@Table(name = "annee_universitaire")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "anneeuniversitaire")
public class AnneeUniversitaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "annees", nullable = false)
    private String annees;

    @ManyToOne
    private Etudiant etudiant;

    @ManyToOne
    private Enseignant enseignant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnnees() {
        return annees;
    }

    public AnneeUniversitaire annees(String annees) {
        this.annees = annees;
        return this;
    }

    public void setAnnees(String annees) {
        this.annees = annees;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public AnneeUniversitaire etudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
        return this;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public AnneeUniversitaire enseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
        return this;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AnneeUniversitaire anneeUniversitaire = (AnneeUniversitaire) o;
        if (anneeUniversitaire.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), anneeUniversitaire.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AnneeUniversitaire{" +
            "id=" + getId() +
            ", annees='" + getAnnees() + "'" +
            "}";
    }
}

package org.lcherubin.ium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Cours.
 */
@Entity
@Table(name = "cours")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cours")
public class Cours implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code_ue", nullable = false)
    private String codeUE;

    @NotNull
    @Column(name = "intitule_ue", nullable = false)
    private String intituleUE;

    @NotNull
    @Column(name = "semestre", nullable = false)
    private String semestre;

    @NotNull
    @Column(name = "duree", nullable = false)
    private String duree;

    @ManyToOne
    private Enseignant enseignant;

    @ManyToOne
    private Filiere filiere;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodeUE() {
        return codeUE;
    }

    public Cours codeUE(String codeUE) {
        this.codeUE = codeUE;
        return this;
    }

    public void setCodeUE(String codeUE) {
        this.codeUE = codeUE;
    }

    public String getIntituleUE() {
        return intituleUE;
    }

    public Cours intituleUE(String intituleUE) {
        this.intituleUE = intituleUE;
        return this;
    }

    public void setIntituleUE(String intituleUE) {
        this.intituleUE = intituleUE;
    }

    public String getSemestre() {
        return semestre;
    }

    public Cours semestre(String semestre) {
        this.semestre = semestre;
        return this;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public String getDuree() {
        return duree;
    }

    public Cours duree(String duree) {
        this.duree = duree;
        return this;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public Enseignant getEnseignant() {
        return enseignant;
    }

    public Cours enseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
        return this;
    }

    public void setEnseignant(Enseignant enseignant) {
        this.enseignant = enseignant;
    }

    public Filiere getFiliere() {
        return filiere;
    }

    public Cours filiere(Filiere filiere) {
        this.filiere = filiere;
        return this;
    }

    public void setFiliere(Filiere filiere) {
        this.filiere = filiere;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Cours cours = (Cours) o;
        if (cours.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cours.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Cours{" +
            "id=" + getId() +
            ", codeUE='" + getCodeUE() + "'" +
            ", intituleUE='" + getIntituleUE() + "'" +
            ", semestre='" + getSemestre() + "'" +
            ", duree='" + getDuree() + "'" +
            "}";
    }
}

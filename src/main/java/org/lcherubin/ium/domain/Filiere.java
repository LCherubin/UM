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

/**
 * A Filiere.
 */
@Entity
@Table(name = "filiere")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "filiere")
public class Filiere implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "intitule", nullable = false)
    private String intitule;

    @OneToMany(mappedBy = "filiere")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Cours> cours = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Filiere code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIntitule() {
        return intitule;
    }

    public Filiere intitule(String intitule) {
        this.intitule = intitule;
        return this;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public Set<Cours> getCours() {
        return cours;
    }

    public Filiere cours(Set<Cours> cours) {
        this.cours = cours;
        return this;
    }

    public Filiere addCours(Cours cours) {
        this.cours.add(cours);
        cours.setFiliere(this);
        return this;
    }

    public Filiere removeCours(Cours cours) {
        this.cours.remove(cours);
        cours.setFiliere(null);
        return this;
    }

    public void setCours(Set<Cours> cours) {
        this.cours = cours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Filiere filiere = (Filiere) o;
        if (filiere.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), filiere.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Filiere{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", intitule='" + getIntitule() + "'" +
            "}";
    }
}

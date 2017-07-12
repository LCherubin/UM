package org.lcherubin.ium.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Country.
 */
@Entity
@Table(name = "country")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "country")
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "iso", nullable = false)
    private String iso;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "nicename", nullable = false)
    private String nicename;

    @NotNull
    @Column(name = "iso_3", nullable = false)
    private String iso3;

    @NotNull
    @Column(name = "numcode", nullable = false)
    private Long numcode;

    @NotNull
    @Column(name = "phonecode", nullable = false)
    private Long phonecode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIso() {
        return iso;
    }

    public Country iso(String iso) {
        this.iso = iso;
        return this;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getName() {
        return name;
    }

    public Country name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNicename() {
        return nicename;
    }

    public Country nicename(String nicename) {
        this.nicename = nicename;
        return this;
    }

    public void setNicename(String nicename) {
        this.nicename = nicename;
    }

    public String getIso3() {
        return iso3;
    }

    public Country iso3(String iso3) {
        this.iso3 = iso3;
        return this;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    public Long getNumcode() {
        return numcode;
    }

    public Country numcode(Long numcode) {
        this.numcode = numcode;
        return this;
    }

    public void setNumcode(Long numcode) {
        this.numcode = numcode;
    }

    public Long getPhonecode() {
        return phonecode;
    }

    public Country phonecode(Long phonecode) {
        this.phonecode = phonecode;
        return this;
    }

    public void setPhonecode(Long phonecode) {
        this.phonecode = phonecode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Country country = (Country) o;
        if (country.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), country.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Country{" +
            "id=" + getId() +
            ", iso='" + getIso() + "'" +
            ", name='" + getName() + "'" +
            ", nicename='" + getNicename() + "'" +
            ", iso3='" + getIso3() + "'" +
            ", numcode='" + getNumcode() + "'" +
            ", phonecode='" + getPhonecode() + "'" +
            "}";
    }
}

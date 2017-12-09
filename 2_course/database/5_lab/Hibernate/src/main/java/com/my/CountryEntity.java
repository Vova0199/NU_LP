package com.my;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "country", schema = "db_jdbc", catalog = "")
public class CountryEntity {
    private String country;
    private Collection<PersonEntity> peopleByCity;

    public CountryEntity(){}
    public CountryEntity(String c){
        country =c;
    }

    @Id
    @Column(name = "Country", nullable = false, length = 25)
    public String getCountry() {
        return country;
    }

    public void setCountry(String city) {
        this.country = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountryEntity that = (CountryEntity) o;

        if (country != null ? !country.equals(that.country) : that.country != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return country != null ? country.hashCode() : 0;
    }

    @OneToMany(mappedBy = "cityByCity")
    public Collection<PersonEntity> getPeopleByCity() {
        return peopleByCity;
    }

    public void setPeopleByCity(Collection<PersonEntity> peopleByCity) {
        this.peopleByCity = peopleByCity;
    }
}

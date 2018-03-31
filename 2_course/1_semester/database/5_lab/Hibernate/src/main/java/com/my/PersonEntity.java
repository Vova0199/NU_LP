package com.my;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "person", schema = "db_jdbc", catalog = "")
public class PersonEntity {
    private int idPerson;
    private String surname;
    private String name;
    private String email;
    private CountryEntity cityByCity;
    private List<GadgetEntity> gadget;

    public PersonEntity()
    {}
    public PersonEntity(String s,String n,String city,String e){
        surname=s;
        name=n;
        cityByCity=new CountryEntity(city);
        email=e;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDPerson", nullable = false)
    public int getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(int idPerson) {
        this.idPerson = idPerson;
    }

    @Column(name = "Surname", nullable = false, length = 25)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Column(name = "Name", nullable = false, length = 25)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "Email", nullable = true, length = 45)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonEntity that = (PersonEntity) o;

        if (idPerson != that.idPerson) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPerson;
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "Country", referencedColumnName = "Country", nullable = false)
    public CountryEntity getCityByCity() {
        return cityByCity;
    }

    public void setCityByCity(CountryEntity cityByCity) {
        this.cityByCity = cityByCity;
    }

    @ManyToMany(mappedBy = "persons")
    public List<GadgetEntity> getGadget() {
        return gadget;
    }

    public void addBookEntity(GadgetEntity bookEntity){
        if(!getGadget().contains(bookEntity)){
            getGadget().add(bookEntity);
        }
        if(!bookEntity.getPersons().contains(this)){
            bookEntity.getPersons().add(this);
        }
    }

    public void setGadget(List<GadgetEntity> books) {
        this.gadget = books;
    }
}

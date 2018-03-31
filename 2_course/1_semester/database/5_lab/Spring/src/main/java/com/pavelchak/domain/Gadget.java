package com.pavelchak.domain;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "gadget")
public class Gadget {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gadget_id", nullable = false)
    private Long id;
    @Column(name = "gadget_name", nullable = false, length = 45)
    private String gadgetName;
    @Column(name = "author", nullable = false, length = 45)
    private String author;
    @Column(name = "country", nullable = true, length = 50)
    private String country;
    @Column(name = "year_of_creating", nullable = true)
    private Integer year_of_creating;
    @Column(name = "amount", nullable = false)
    private Integer amount;
    @ManyToMany(mappedBy = "persons")
    private Set<Person> persons;

    Gadget(){}
    Gadget(String gadgetName, String author, String country, Integer year_of_creating){
        this.gadgetName=gadgetName;
        this.author=author;
        this.country=country;
        this.year_of_creating=year_of_creating;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long idGadget) {
        this.id = idGadget;
    }

    public String getGadgetName() {
        return gadgetName;
    }
    public void setGadgetName(String gadgetName) {
        this.gadgetName = gadgetName;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return country;
    }
    public void setPublisher(String country) {
        this.country = country;
    }

    public Integer getYear_of_creating() {
        return year_of_creating;
    }
    public void setYear_of_creating(Integer year_of_creating) {
        this.year_of_creating = year_of_creating;
    }

    public Integer getAmount() {
        return amount;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public Set<Person> getPersons() {
        return persons;
    }

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gadget that = (Gadget) o;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (gadgetName != null ? !gadgetName.equals(that.gadgetName) : that.gadgetName != null) return false;
        if (author != null ? !author.equals(that.author) : that.author != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        if (year_of_creating != null ? !year_of_creating.equals(that.year_of_creating) : that.year_of_creating != null) return false;
        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (gadgetName != null ? gadgetName.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (year_of_creating != null ? year_of_creating.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        return result;
    }
}

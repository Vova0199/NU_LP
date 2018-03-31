package com.my;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "gadget", schema = "db_jdbc", catalog = "")
public class GadgetEntity {
    private int IDGadget;
    private String title;
    private String display;
    private int amount;
    private List<PersonEntity> persons;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDGadget", nullable = false)
    public int getIDGadget() {
        return IDGadget;
    }

    public void setIDGadget(int idBook) {
        this.IDGadget = idBook;
    }

    @Basic
    @Column(name = "title", nullable = false, length = 45)
    public String getTitle() {
        return title;
    }

    public void setTitle(String bookName) {
        this.title = bookName;
    }

    @Basic
    @Column(name = "dispaly", nullable = false, length = 45)
    public String getDisplay() {
        return display;
    }

    public void setDisplay(String author) {
        this.display = author;
    }

    @Basic
    @Column(name = "Amount", nullable = false)
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GadgetEntity that = (GadgetEntity) o;

        if (IDGadget != that.IDGadget) return false;
        if (amount != that.amount) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (display != null ? !display.equals(that.display) : that.display != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = IDGadget;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (display != null ? display.hashCode() : 0);
        result = 31 * result + amount;
        return result;
    }

    @ManyToMany
    @JoinTable(name = "persongadget", catalog = "", schema = "db_jdbc", joinColumns =
    @JoinColumn(name = "IDGadget", referencedColumnName = "IDGadget", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "IDPerson",
                    referencedColumnName = "IDPerson", nullable = false))
    public List<PersonEntity> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonEntity> persons) {
        this.persons = persons;
    }
}

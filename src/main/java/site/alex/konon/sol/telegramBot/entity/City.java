package site.alex.konon.sol.telegramBot.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "city")
public class City implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(columnDefinition="TEXT")
    private String text;
    private String name;
    private Timestamp dateCreated;
    private Timestamp dateLastModification;

    public City() {
        this.dateCreated = new Timestamp(new Date().getTime());
    }


    public Timestamp getDateLastModification() {
        return dateLastModification;
    }

    public void setDateLastModification(Timestamp dateLastModification) {
        this.dateLastModification = dateLastModification;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;
        City city = (City) o;
        return Objects.equals(getName(), city.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}

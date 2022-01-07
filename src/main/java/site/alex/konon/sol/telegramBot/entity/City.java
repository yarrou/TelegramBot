package site.alex.konon.sol.telegramBot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Data @AllArgsConstructor
@Entity
@Table(name = "city")
public class City implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(columnDefinition = "TEXT")
    private String text;
    private String name;
    private Timestamp dateCreated;
    private Timestamp dateLastModification;

    public City() {
        this.dateCreated = new Timestamp(new Date().getTime());
    }

    public void setText(String text) {
        this.text = text;
        setDateLastModification(new Timestamp(new Date().getTime()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return Objects.equals(name, city.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

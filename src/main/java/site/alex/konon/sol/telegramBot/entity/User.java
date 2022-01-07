package site.alex.konon.sol.telegramBot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Data @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "Users")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "login")
    private String login;
    @Column(name = "secret")
    private String secret;

    @Column(name = "token")
    private String token;
    @Column(name = "isConfirm")
    private boolean isConfirm = false;
    private Timestamp dateExpire;
}

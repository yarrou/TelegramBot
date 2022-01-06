package site.alex.konon.sol.telegramBot.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

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
    @Column(name = "password")
    private String password;

    @Column(name = "token")
    private String token;
    @Column(name = "isConfirm")
    private boolean isConfirm = false;
    private Timestamp dateExpire;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isConfirm() {
        return isConfirm;
    }

    public void setConfirm(boolean confirm) {
        isConfirm = confirm;
    }

    public Timestamp getDateExpire() {
        return dateExpire;
    }

    public void setDateExpire(Timestamp dateExpire) {
        this.dateExpire = dateExpire;
    }
}

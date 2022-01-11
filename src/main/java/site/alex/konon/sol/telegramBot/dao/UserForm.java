package site.alex.konon.sol.telegramBot.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserForm {
    private String userName;
    private String password;
}

package site.alex.konon.sol.telegramBot.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserFromRequest {
    private String userName;
    private String password;
}

package site.alex.konon.sol.telegramBot.services.impl;

import org.springframework.stereotype.Service;
import site.alex.konon.sol.telegramBot.dao.UserForm;
import site.alex.konon.sol.telegramBot.entity.User;
import site.alex.konon.sol.telegramBot.mapper.UserMapper;
import site.alex.konon.sol.telegramBot.repository.UserRepository;
import site.alex.konon.sol.telegramBot.services.TokenService;
import site.alex.konon.sol.telegramBot.services.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final TokenService tokenService;



    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository, TokenService tokenService) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Override
    public User register(UserForm userForm) {
        User user = convertUserForm(userForm);
        if(getUserByLogin(user.getLogin()).getId()==0){
            user.setToken(tokenService.generateAuthToken(user.getLogin()));
            return save(user);
        }
        else {
            throw new IllegalArgumentException("user with this username already exists");
        }
    }

    @Override
    public String loginUser(User user) {
        String token = tokenService.generateAuthToken(user.getLogin());
        user.setToken(token);
        save(user);
        return token;
    }

    @Override
    public User getUserById(long id) {
        return userRepository.getOne(id);
    }

    @Override
    public boolean isAdmin(User user) {
        return false;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserByLogin(String login) {
        return userRepository.findOneByLogin(login).orElseGet(User::new);
    }

    @Override
    public User getUserFromRequest(UserForm userForm) {
        return getUserByLogin(userForm.getUserName());
    }

    @Override
    public User convertUserForm(UserForm userForm) {
        return userMapper.userFromRequestToUser(userForm);
    }
}
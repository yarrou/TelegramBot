package site.alex.konon.sol.telegramBot.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import site.alex.konon.sol.telegramBot.dao.UserForm;
import site.alex.konon.sol.telegramBot.entity.User;
import site.alex.konon.sol.telegramBot.mapper.UserMapper;
import site.alex.konon.sol.telegramBot.repository.UserRepository;
import site.alex.konon.sol.telegramBot.services.EmailSenderService;
import site.alex.konon.sol.telegramBot.services.MessagesSourcesService;
import site.alex.konon.sol.telegramBot.services.TokenService;
import site.alex.konon.sol.telegramBot.services.UserService;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final EmailSenderService emailSenderService;
    private final MessagesSourcesService messagesSourcesService;
    private String locale;

    public void setLocale(String locale){
        this.locale = locale;
    }


    public UserServiceImpl(UserMapper userMapper,
                           UserRepository userRepository,
                           TokenService tokenService,
                           EmailSenderService emailSenderService,
                           MessagesSourcesService messagesSourcesService) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.emailSenderService = emailSenderService;
        this.messagesSourcesService = messagesSourcesService;
        this.locale = "en";
    }

    @Override
    public User register(UserForm userForm) {
        User user = convertUserForm(userForm);
        if (getUserByLogin(user.getLogin()).getId() == 0) {
            user.setRegistrationToken(tokenService.getRandom());
            user.setToken(tokenService.generateAuthToken(user.getLogin()));
            emailSenderService.sendConfirmRegistrationEmail(user, locale);
            return save(user);
        } else {
            throw new IllegalArgumentException(messagesSourcesService.getStringValue(MessagesSourcesService.MESSAGE_USER_ALREADY_EXISTS));
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
    public ResponseEntity confirmRegister(String code) {
        User user = userRepository.findOneByRegistrationToken(code).orElseGet(User::new);
         if (user.getId()!=0&&!user.isConfirm()) {
            user.setConfirm(true);
            save(user);
            emailSenderService.sendSuccessRegistrationEmail(user,locale);
            return new ResponseEntity(messagesSourcesService.getStringValue(MessagesSourcesService.MESSAGE_SUCCESS_CONFIRM_REGISTRATION), HttpStatus.OK);
        } else if (user.isConfirm()){
            return new ResponseEntity(messagesSourcesService.getStringValue(MessagesSourcesService.MESSAGE_ALREADY_CONFIRM_REGISTRATION),HttpStatus.OK);
         }
         else if (user.getId()==0){
             return new ResponseEntity(messagesSourcesService.getStringValue(MessagesSourcesService.MESSAGE_BAD_CONFIRM_REGISTRATION_TOKEN),HttpStatus.OK);
         }
         else return new ResponseEntity(messagesSourcesService.getStringValue(MessagesSourcesService.MESSAGE_SOMEONE_WRONG),HttpStatus.NOT_ACCEPTABLE);
    }

    @Override
    public User getUserByToken(String token) {
        return userRepository.findOneByToken(token).orElseGet(User::new);
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

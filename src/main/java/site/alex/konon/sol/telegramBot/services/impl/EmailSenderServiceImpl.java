package site.alex.konon.sol.telegramBot.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import site.alex.konon.sol.telegramBot.constants.ConstantsLocalization;
import site.alex.konon.sol.telegramBot.emailSender.EmailSender;
import site.alex.konon.sol.telegramBot.entity.User;
import site.alex.konon.sol.telegramBot.services.EmailSenderService;
import site.alex.konon.sol.telegramBot.services.LocaleService;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    LocaleService localeService;
    @Value("${bot.name}")
    private String botUsername;
    @Value("${S_EMAIL}")
    private String serverEmail;
    @Override
    public void sendRegistrationEmail(User user,String locale) {
        ConstantsLocalization localization = localeService.getLocale(locale);
        String emailSubject = localization.getRegistrationSubjectText() + botUsername;
        String content = localization.getRegistrationEmailText();
        content = content.replace("[[bot]]",botUsername);
        new EmailSender(user.getLogin(),content,emailSubject,mailSender,serverEmail,botUsername);
    }
}

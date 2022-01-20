package site.alex.konon.sol.telegramBot.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import site.alex.konon.sol.telegramBot.constants.localizationConstants.ConstantsLocalization;
import site.alex.konon.sol.telegramBot.emailSender.EmailSender;
import site.alex.konon.sol.telegramBot.constants.Endpoints;
import site.alex.konon.sol.telegramBot.entity.User;
import site.alex.konon.sol.telegramBot.services.EmailSenderService;
import site.alex.konon.sol.telegramBot.services.LocaleService;
import site.alex.konon.sol.telegramBot.util.MyRequestContextListener;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {
    @Autowired
    private MyRequestContextListener requestContextListener;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    LocaleService localeService;
    @Value("${bot.name}")
    private String botUsername;
    @Value("${S_EMAIL}")
    private String serverEmail;
    @Override
    public void sendConfirmRegistrationEmail(User user, String locale) {
        String url = requestContextListener.getMyUrl();
        url = url.replace(Endpoints.REGISTRATION,Endpoints.CONFIRM_REGISTRATION) + "?code=" + user.getRegistrationToken()+ "&lang="+locale;

        ConstantsLocalization localization = localeService.getLocale(locale);
        String emailSubject = localization.getConfirmRegistrationSubjectText();
        String content = localization.getConfirmRegistrationEmailText();
        content = content.replace("[[bot]]",botUsername);
        content = content.replace("[[url]]",url);
        new EmailSender(user.getLogin(),content,emailSubject,mailSender,serverEmail,botUsername);
    }

    @Override
    public void sendSuccessRegistrationEmail(User user, String locale) {
        String url = requestContextListener.getMyUrl();
        ConstantsLocalization localization = localeService.getLocale(locale);
        String emailSubject = localization.getRegistrationSubjectText();
        String content = localization.getRegistrationEmailText();
        content = content.replace("[[bot]]",botUsername);
        new EmailSender(user.getLogin(),content,emailSubject,mailSender,serverEmail,botUsername);
    }
}

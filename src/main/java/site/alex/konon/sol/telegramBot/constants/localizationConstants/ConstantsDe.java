package site.alex.konon.sol.telegramBot.constants.localizationConstants;

public class ConstantsDe extends ConstantsLocalization {
    @Override
    public String getUnknown() {
        return "Wir wissen nichts über diese Stadt.";
    }

    @Override
    public String getGreeting() {
        return
        "Willkommen.Geben Sie den Namen der Stadt oder einen Teil des Namens ein";
    }

    @Override
    public String getSuggestion() {
        return
        "Vielleicht meinten Sie es ";
    }

    @Override
    public String getList() {
        return "Auf Ihre Anfrage wurden folgende Städte gefunden : ";
    }

    @Override
    public String getClarify() {
        return "aktualisieren Sie bitte den Antrag.";
    }

    @Override
    public String getRegistrationEmailText() {
        return "Sehr geehrter Benutzer,<br>"
                + "Sie haben die Registrierung eines Kontos für die Verwaltung der Telegrammdatenbank des Bot [[bot]]<br> bestätigt"
                + "Dank";
    }

    @Override
    public String getRegistrationSubjectText() {
        return "Anmeldung für den ";
    }

    @Override
    public String getConfirmRegistrationEmailText() {
        return "Sehr geehrter Benutzer,<br>"
                + "Sie registrieren ein neues Konto, um die Telegrammdatenbank des Bot [[bot]] zu verwalten<br>"
                + "Bitte klicken Sie auf den Link, um die Registrierung zu bestätigen<br>"
                +"[[url]] <br>"
                + "Danke,<br>"
                + "[[bot]]";
    }

    @Override
    public String getConfirmRegistrationSubjectText() {
        return "Registrierung bestätigen";
    }

    @Override
    public String getSuccessConfirmRegistrationMessage() {
        return "Sie haben die Registrierung erfolgreich bestätigt.";
    }

    @Override
    public String getAlreadyConfirmRegistrationMessage() {
        return "Die Registrierung wurde bereits bestätigt, Sie müssen diese Aktion nicht wiederholen.";
    }

    @Override
    public String getBadConfirmRegistrationTokenMessage() {
        return "Ungültiger Bestätigungscode";
    }

    @Override
    public String getSomeoneWrongMessage() {
        return "etwas ist schief gelaufen...";
    }
}

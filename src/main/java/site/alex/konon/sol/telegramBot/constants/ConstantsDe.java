package site.alex.konon.sol.telegramBot.constants;

public class ConstantsDe extends ConstantsLocalization{
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
                + "Sie haben sich registriert, um die Datenbank des Telegrammbots [[bot]] zu verwalten<br>"
                + "Dank";
    }

    @Override
    public String getRegistrationSubjectText() {
        return "Anmeldung für den ";
    }
}

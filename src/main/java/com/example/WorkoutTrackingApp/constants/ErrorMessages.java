package com.example.WorkoutTrackingApp.constants;

import java.util.HashMap;
import java.util.Map;

public class ErrorMessages {

    private static final Map<String, Map<String, String>> ERROR_MESSAGES = new HashMap<>();

    static {
        Map<String, String> enMessages = new HashMap<>();
        enMessages.put("ERROR_404", "Page not found");
        enMessages.put("ERROR_500", "Internal server error");

        Map<String, String> frMessages = new HashMap<>();
        frMessages.put("ERROR_404", "Page non trouvée");
        frMessages.put("ERROR_500", "Erreur interne du serveur");

        ERROR_MESSAGES.put("en", enMessages);
        ERROR_MESSAGES.put("fr", frMessages);
    }

    public static String getErrorNameRequiredMessageByLanguage(String language, String errorKey) {
        return ERROR_MESSAGES.get(language).get(errorKey);
    }

}

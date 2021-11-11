package fr.flowsqy.boostchecker.io;

import java.util.HashMap;
import java.util.Map;

public class BoostMessagesManager {

    private final Map<String, BoostMessages> boostMessages;

    public BoostMessagesManager() {
        boostMessages = new HashMap<>();
    }

    public Map<String, BoostMessages> getBoostMessages() {
        return boostMessages;
    }
}

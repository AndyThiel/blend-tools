package info.blendformat.tools.sdna.api;

import javax.enterprise.context.ApplicationScoped;
import java.util.ResourceBundle;

@ApplicationScoped
public class Messages {

    private static final ResourceBundle MESSAGES = ResourceBundle.getBundle(
            "info.blendformat.tools.sdna.api.messages");

    public String getMessage(String messageKey) {
        return getMessage(messageKey, null);
    }

    public String getMessage(String messageKey,
                             String defaultValue) {
        String message = MESSAGES.getString(messageKey);
        if (null == message) {
            return defaultValue;
        }
        return message;
    }
}

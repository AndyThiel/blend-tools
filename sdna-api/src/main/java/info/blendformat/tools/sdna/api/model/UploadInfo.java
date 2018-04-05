package info.blendformat.tools.sdna.api.model;

import java.io.Serializable;

public class UploadInfo implements Serializable {

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "UploadInfo{" +
                "message='" + message + '\'' +
                '}';
    }
}

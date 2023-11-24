package pt.isec.pd.projetopd.communication.classes;

import java.io.Serializable;

public class ErrorMessage implements Serializable {
    private String error;

    public ErrorMessage(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}

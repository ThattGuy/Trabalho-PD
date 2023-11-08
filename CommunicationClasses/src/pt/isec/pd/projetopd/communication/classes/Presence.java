package pt.isec.pd.projetopd.communication.classes;

import java.io.Serializable;

/**
 * classe para registar presenças
 */
public class Presence implements Serializable {
    int code;
    public Presence(int code) {
        this.code = code;
    }

    public int getcode() {
        return code;
    }
}

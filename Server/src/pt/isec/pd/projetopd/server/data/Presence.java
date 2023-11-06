package pt.isec.pd.projetopd.server.data;

import java.io.Serializable;

public class Presence implements Serializable {

    int code;

    public Presence(int code) {
        this.code = code;
    }

    public int getcode() {
        return code;
    }
}

package pt.isec.pd.projetopd.communication.classes;

import java.io.Serializable;

/**
 * classe para registar presenças
 */

import java.util.Date;
import java.util.UUID;


public class RegisterCode implements Serializable {

    private UUID code;
    private Date expirationTime;

    public RegisterCode(int minutesActive) {
        this.code = UUID.randomUUID();
        setExpirationTime(minutesActive);
    }

    private void setExpirationTime(int validTime) {
        if (validTime <= 0) {
            throw new IllegalArgumentException("Valid time must be greater than 0");
        }

        long currentTime = System.currentTimeMillis();
        long expirationMillis = currentTime + ((long) validTime * 60 * 1000); // Convert minutes to milliseconds

        this.expirationTime = new Date(expirationMillis);
    }

    public UUID getCode() {
        return code;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }
}

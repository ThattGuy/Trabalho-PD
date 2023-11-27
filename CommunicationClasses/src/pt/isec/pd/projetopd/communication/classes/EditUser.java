package pt.isec.pd.projetopd.communication.classes;

import java.io.Serializable;

public class EditUser implements Serializable {

    User user;

    public EditUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}

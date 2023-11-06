package pt.isec.pd.projetopd.cliente.ui.uistates;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import pt.isec.pd.projetopd.cliente.model.Manager;

import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;

public class LoginUI extends BorderPane {

    Manager manager;
    Button btnExit;
    TextField usernameField, passwordField;

    public LoginUI(Manager manager) {
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    /**
     * Cria os botões e imagens
     */
    private void createViews() {
        btnExit = new Button("Exit");
        btnExit.setMinWidth(200);
        btnExit.setMinHeight(50);

        usernameField = new TextField("Username");
        passwordField = new TextField("Password");

        // Set the number of columns for the TextField to limit their width
        usernameField.setPrefColumnCount(1); // Adjust the number as needed
        passwordField.setPrefColumnCount(1); // Adjust the number as needed

        VBox vbox = new VBox(usernameField, passwordField, btnExit);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setMargin(usernameField, new Insets(10, 0, 0, 0));
        vbox.setMargin(passwordField, new Insets(10, 0, 0, 0));
        vbox.setMargin(btnExit, new Insets(10, 0, 0, 0));

        double buttonHeightPercentage = 0.05;
        btnExit.prefHeightProperty().bind(this.heightProperty().multiply(buttonHeightPercentage));

        double buttonWidthPercentage = 0.25;
        btnExit.minWidthProperty().bind(vbox.widthProperty().multiply(buttonWidthPercentage));

        this.setCenter(vbox);
    }


    /**
     * regista os handlers
     */
    private void registerHandlers() {

        manager.addPropertyChangeListener(evt -> { update(); });

        btnExit.setOnAction(event -> {
            System.exit(0);
        });
    }

    private void update() {
        if (manager.getState() != ClientStates.LOGIN) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);
    }
}

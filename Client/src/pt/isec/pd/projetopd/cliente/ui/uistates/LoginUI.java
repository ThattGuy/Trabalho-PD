package pt.isec.pd.projetopd.cliente.ui.uistates;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pt.isec.pd.projetopd.cliente.model.Manager;

import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;

public class LoginUI extends BorderPane {

    Manager manager;
    Button btnLogin,btnExit;
    TextField usernameField;
    PasswordField passwordField;

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

        btnLogin = new Button("Login");
        btnLogin.setMinWidth(200);
        btnLogin.setMinHeight(50);


        btnExit = new Button("Exit");
        btnExit.setMinWidth(200);
        btnExit.setMinHeight(50);

        usernameField = new TextField("Username");
        usernameField.setPromptText("Enter Username");

        passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");

        HBox hBox = new HBox(btnExit,btnLogin);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);

        VBox vbox = new VBox(usernameField, passwordField,hBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(25);
        vbox.setPadding(new Insets(10));

        double fieldsWidthPercentage = 0.5;
        usernameField.maxWidthProperty().bind(vbox.widthProperty().multiply(fieldsWidthPercentage));
        passwordField.maxWidthProperty().bind(vbox.widthProperty().multiply(fieldsWidthPercentage));

        VBox container = new VBox(vbox);
        container.setAlignment(Pos.CENTER);

        this.setCenter(container);
    }


    /**
     * regista os handlers
     */
    private void registerHandlers() {

        manager.addPropertyChangeListener(evt -> { update(); });

        btnLogin.setOnAction(event -> {
            manager.selectOption(OPTIONS.LOGIN, usernameField.getText() + "\n" + passwordField.getText());
            update();
        });

        this.setFocusTraversable(true);
        this.setOnKeyPressed((key) -> {
            if (key.getCode() == KeyCode.ENTER) {
                manager.selectOption(OPTIONS.LOGIN, usernameField.getText() + "\n" + passwordField.getText());
                update();
            }
        });

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

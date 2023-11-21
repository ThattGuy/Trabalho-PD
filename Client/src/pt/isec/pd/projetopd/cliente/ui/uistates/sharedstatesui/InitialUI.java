package pt.isec.pd.projetopd.cliente.ui.uistates.sharedstatesui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import pt.isec.pd.projetopd.cliente.model.Manager;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;

public class InitialUI extends BorderPane {

    private Manager manager;
    private Button btnLogin, btnRegister, btnExit;

    public InitialUI(Manager manager) {
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

        btnRegister = new Button("Register");
        btnRegister.setMinWidth(200);
        btnRegister.setMinHeight(50);

        btnExit = new Button("Exit");
        btnExit.setMinWidth(200);
        btnExit.setMinHeight(50);

        VBox vbox = new VBox(btnLogin, btnRegister, btnExit);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setMargin(btnLogin, new Insets(10, 0, 0, 0));
        vbox.setMargin(btnRegister, new Insets(10, 0, 0, 0));
        vbox.setMargin(btnExit, new Insets(10, 0, 0, 0));

        double buttonHeightPercentage = 0.05; // Adjust this value to control the button height
        btnLogin.prefHeightProperty().bind(this.heightProperty().multiply(buttonHeightPercentage));
        btnRegister.prefHeightProperty().bind(this.heightProperty().multiply(buttonHeightPercentage));
        btnExit.prefHeightProperty().bind(this.heightProperty().multiply(buttonHeightPercentage));

        double buttonWidthPercentage = 0.25;
        btnLogin.minWidthProperty().bind(vbox.widthProperty().multiply(buttonWidthPercentage));
        btnRegister.minWidthProperty().bind(vbox.widthProperty().multiply(buttonWidthPercentage));
        btnExit.minWidthProperty().bind(vbox.widthProperty().multiply(buttonWidthPercentage));

        this.setCenter(vbox);
    }

    /**
     * regista os handlers
     */
    private void registerHandlers() {

        manager.addPropertyChangeListener(evt -> { update(); });

        btnLogin.setOnAction(event -> {
            manager.selectOption(OPTIONS.LOGIN, null);
            update();
        });
        btnRegister.setOnAction(event -> {
            manager.selectOption(OPTIONS.REG_USER, null);
            update();
        });
        btnExit.setOnAction(event -> {
            System.exit(0);
        });
    }

    private void update() {
        if (manager.getState() != ClientStates.INITIAL) {
            this.setVisible(false);
            return;
        }
        setVisible(true);
    }

}
package pt.isec.pd.projetopd.cliente.ui.uistates;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import pt.isec.pd.projetopd.cliente.model.Manager;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;
import pt.isec.pd.projetopd.cliente.ui.resources.ImageManager;

public class SelectOptUI extends BorderPane {

    Manager manager;
    Button btnLogin, btnRegister, btnExit;

    public SelectOptUI(Manager manager) {
        this.manager = manager;

        createViews();
        registerHandlers();
    }

    /**
     * Cria os botões e imagens
     */
    private void createViews() {

        btnLogin = new Button("Login");
        btnLogin.setMinWidth(200);
        btnLogin.setMinHeight(30);

        ImageView topFV = new ImageView(ImageManager.getImage("topfive.png"));
        topFV.fitWidthProperty().bind(this.widthProperty().multiply(0.1));
        topFV.setPreserveRatio(true);
        btnRegister = new Button("Register");
        btnRegister.setMinWidth(200);
        btnRegister.setMinHeight(30);

        ImageView exitV = new ImageView(ImageManager.getImage("exit.png"));
        exitV.fitWidthProperty().bind(this.widthProperty().multiply(0.05));
        exitV.setPreserveRatio(true);
        btnExit = new Button("Exit");
        btnExit.setMinWidth(200);
        btnExit.setMinHeight(30);

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

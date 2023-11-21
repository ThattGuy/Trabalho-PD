package pt.isec.pd.projetopd.cliente.ui.uistates.userstatesui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pd.projetopd.cliente.model.Manager;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;

public class SelectOptUserUI extends BorderPane {

    Manager manager;
    Button btnRegisterPresence, btnViewPresence, btnEditInfo, btnLogout;
    VBox userInfo;

    public SelectOptUserUI(Manager manager) {
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    /**
     * Cria os botões e imagens
     */
    private void createViews() {

        btnRegisterPresence = new Button("Register Presence");
        btnRegisterPresence.setMinWidth(200);
        btnRegisterPresence.setMinHeight(50);


        btnViewPresence = new Button("View Presences");
        btnViewPresence.setMinWidth(200);
        btnViewPresence.setMinHeight(50);


        btnEditInfo = new Button("Edit My Info");
        btnEditInfo.setMinWidth(200);
        btnEditInfo.setMinHeight(50);

        btnLogout = new Button("Logout");
        btnLogout.setMinWidth(200);
        btnLogout.setMinHeight(50);

        VBox vbox = new VBox(btnRegisterPresence, btnViewPresence, btnEditInfo, btnLogout);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setMargin(btnRegisterPresence, new Insets(10, 0, 0, 0));
        vbox.setMargin(btnEditInfo, new Insets(10, 0, 0, 0));
        vbox.setMargin(btnLogout, new Insets(10, 0, 0, 0));

        double buttonHeightPercentage = 0.05; // Adjust this value to control the button height
        btnRegisterPresence.prefHeightProperty().bind(this.heightProperty().multiply(buttonHeightPercentage));
        btnEditInfo.prefHeightProperty().bind(this.heightProperty().multiply(buttonHeightPercentage));
        btnLogout.prefHeightProperty().bind(this.heightProperty().multiply(buttonHeightPercentage));
        btnViewPresence.prefHeightProperty().bind(this.heightProperty().multiply(buttonHeightPercentage));

        double buttonWidthPercentage = 0.25;
        btnRegisterPresence.minWidthProperty().bind(vbox.widthProperty().multiply(buttonWidthPercentage));
        btnEditInfo.minWidthProperty().bind(vbox.widthProperty().multiply(buttonWidthPercentage));
        btnLogout.minWidthProperty().bind(vbox.widthProperty().multiply(buttonWidthPercentage));
        btnViewPresence.minWidthProperty().bind(vbox.widthProperty().multiply(buttonWidthPercentage));

        userInfo = new VBox();
        userInfo.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setLeft(userInfo);
        this.setCenter(vbox);
    }

    /**
     * regista os handlers
     */
    private void registerHandlers() {

        manager.addPropertyChangeListener(evt -> { update(); });

        btnRegisterPresence.setOnAction(event -> {
            manager.selectOption(OPTIONS.REG_PRESENCE, null);
            update();
        });
        btnViewPresence.setOnAction(event -> {
            manager.selectOption(OPTIONS.VIEW_PRESENCE, null);
            update();
        });
        btnEditInfo.setOnAction(event -> {
            manager.selectOption(OPTIONS.EDIT_DATA, null);
            update();
        });
        btnLogout.setOnAction(event -> {
            System.exit(0);
        });
    }

    private void update() {
        if (manager.getState() != ClientStates.SELECT_OPT) {
            this.setVisible(false);
            return;
        }

       Label userName = new Label(manager.getUserName());

        userName.setStyle("-fx-font-size: 20;");

        userInfo.getChildren().clear();
        userInfo.getChildren().add(userName);

        setVisible(true);
    }

}

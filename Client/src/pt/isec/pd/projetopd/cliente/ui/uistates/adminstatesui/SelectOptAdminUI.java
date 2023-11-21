package pt.isec.pd.projetopd.cliente.ui.uistates.adminstatesui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pd.projetopd.cliente.model.Manager;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;

public class SelectOptAdminUI extends BorderPane {

    Manager manager;
    VBox userInfo;
    Button btnCreateEvent, btnViewEvents, btnEditInfo, btnLogout;

    public SelectOptAdminUI(Manager manager) {
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    /**
     * Cria os botões e imagens
     */
    private void createViews() {

        btnCreateEvent = new Button("Create Event");
        btnCreateEvent.setMinWidth(200);
        btnCreateEvent.setMinHeight(50);


        btnViewEvents = new Button("View Events");
        btnViewEvents.setMinWidth(200);
        btnViewEvents.setMinHeight(50);


        btnEditInfo = new Button("Edit My Info");
        btnEditInfo.setMinWidth(200);
        btnEditInfo.setMinHeight(50);

        btnLogout = new Button("Logout");
        btnLogout.setMinWidth(200);
        btnLogout.setMinHeight(50);

        VBox vbox = new VBox(btnCreateEvent, btnViewEvents, btnEditInfo, btnLogout);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setMargin(btnCreateEvent, new Insets(10, 0, 0, 0));
        vbox.setMargin(btnEditInfo, new Insets(10, 0, 0, 0));
        vbox.setMargin(btnLogout, new Insets(10, 0, 0, 0));

        double buttonHeightPercentage = 0.05; // Adjust this value to control the button height
        btnCreateEvent.prefHeightProperty().bind(this.heightProperty().multiply(buttonHeightPercentage));
        btnEditInfo.prefHeightProperty().bind(this.heightProperty().multiply(buttonHeightPercentage));
        btnLogout.prefHeightProperty().bind(this.heightProperty().multiply(buttonHeightPercentage));
        btnViewEvents.prefHeightProperty().bind(this.heightProperty().multiply(buttonHeightPercentage));

        double buttonWidthPercentage = 0.25;
        btnCreateEvent.minWidthProperty().bind(vbox.widthProperty().multiply(buttonWidthPercentage));
        btnEditInfo.minWidthProperty().bind(vbox.widthProperty().multiply(buttonWidthPercentage));
        btnLogout.minWidthProperty().bind(vbox.widthProperty().multiply(buttonWidthPercentage));
        btnViewEvents.minWidthProperty().bind(vbox.widthProperty().multiply(buttonWidthPercentage));
        setPadding(new Insets(10));

        userInfo = new VBox();
        userInfo.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setLeft(userInfo);
        this.setCenter(vbox);
    }

    /**
     * regista os handlers
     */
    private void registerHandlers() {

        manager.addPropertyChangeListener(evt -> { Platform.runLater(this::update);});

        btnCreateEvent.setOnAction(event -> {
            manager.selectOption(OPTIONS.CREATE_EVENT, null);
            update();
        });
        btnViewEvents.setOnAction(event -> {
            manager.selectOption(OPTIONS.VIEW_EVENTS, null);
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
        if (manager.getState() != ClientStates.SELECT_OPT_ADMIN) {
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

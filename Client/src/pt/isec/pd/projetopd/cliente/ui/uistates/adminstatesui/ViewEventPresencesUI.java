package pt.isec.pd.projetopd.cliente.ui.uistates.adminstatesui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import pt.isec.pd.projetopd.cliente.model.Manager;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;

public class ViewEventPresencesUI extends BorderPane {

    private Manager manager;
    private Label messageLabel;
    private Label presencesLabel, lRemove;
    private VBox centerContainer;
    private HBox hBox;
    private Button btnBack, btnCSV, btnRemove;
    private HBox removePresenceHBox;
    private TextField userName, eventName;
    private ScrollPane scrollPane;

    public ViewEventPresencesUI(Manager manager) {
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    /**
     * Cria os botões e imagens
     */
    private void createViews() {
        btnBack = new Button("Back");
        btnBack.setMinWidth(200);
        btnBack.setMinHeight(50);

        btnRemove = new Button("Remove");
        btnRemove.setMinWidth(50);
        btnRemove.setMinHeight(10);


        btnCSV = new Button("Get CSV File");
        btnCSV.setMinWidth(200);
        btnCSV.setMinHeight(50);

        lRemove = new Label("Remove Presence");

        userName = new TextField();
        userName.setPromptText("Enter User Name");

        eventName = new TextField();
        eventName.setPromptText("Enter event name");

        messageLabel = new Label();
        messageLabel.getStyleClass().add("info");
        messageLabel.setTextFill(Color.RED);
        messageLabel.setStyle("-fx-font-size: 20px;");

        // Creating a VBox to add buttons and labels, setting it as the center of the BorderPane
        centerContainer = new VBox(btnBack);
        centerContainer.setAlignment(Pos.CENTER);
        centerContainer.setSpacing(10);

        scrollPane = new ScrollPane(centerContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        this.setCenter(centerContainer);

    }


    /**
     * regista os handlers
     */
    private void registerHandlers() {
        manager.addPropertyChangeListener(evt -> {
            Platform.runLater(this::update);
        });

        btnBack.setOnAction(event -> {
            manager.selectOption(OPTIONS.BACK, null);
            update();
        });

        btnCSV.setOnAction(event -> {
            manager.selectOption(OPTIONS.CSV, null);
            update();
        });

        btnRemove.setOnAction(event -> {
            String string = userName.getText() + "\n" + eventName.getText();
            manager.selectOption(OPTIONS.SUBMIT, string);
            update();
        });

    }

    private void update() {
        if (manager.getState() != ClientStates.VIEW_EVENT_PRESENCE) {
            userName.setText(null);
            eventName.setText(null);

            this.setVisible(false);
            return;
        }
        this.setVisible(true);

        // Clear existing labels and buttons
        centerContainer.getChildren().clear();
        presencesLabel = new Label(manager.getPresences());

        presencesLabel.setStyle("-fx-background-color: #D3D3D3;");
        presencesLabel.setAlignment(Pos.CENTER);

        centerContainer.getChildren().add(presencesLabel);

        String msg = manager.getPresences();
        if (msg != null) {
            messageLabel.setText(msg);
        }

        removePresenceHBox = new HBox(); // Initialize it here

        if (msg != "No Presences") {
            removePresenceHBox.getChildren().setAll(lRemove, userName, eventName, btnRemove);
            removePresenceHBox.setAlignment(Pos.CENTER);
            hBox = new HBox(removePresenceHBox, btnBack, btnCSV);
            hBox.setAlignment(Pos.CENTER);
        } else {
            hBox = new HBox(btnBack);
            hBox.setAlignment(Pos.CENTER);
            hBox.setSpacing(10);
        }

        centerContainer.getChildren().add(removePresenceHBox);
        centerContainer.getChildren().add(hBox);



        registerHandlers();
        this.setCenter(centerContainer);
    }
}
package pt.isec.pd.projetopd.cliente.ui.uistates.userstatesui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import pt.isec.pd.projetopd.cliente.model.Manager;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;

import java.util.ArrayList;
import java.util.List;

public class ViewPresenceUI extends BorderPane {

    private Manager manager;
    private Label messageLabel;
    private Label presencesLabel;
    private VBox centerContainer;
    private HBox hBox;
    private Button btnBack, btnCSV;

    private ScrollPane scrollPane;

    public ViewPresenceUI(Manager manager) {
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

        btnCSV = new Button("Get CSV File");
        btnCSV.setMinWidth(200);
        btnCSV.setMinHeight(50);

        messageLabel = new Label();
        messageLabel.getStyleClass().add("info");
        messageLabel.setTextFill(Color.RED);
        messageLabel.setStyle("-fx-font-size: 20px;");

        // Creating a VBox to add buttons and labels
        centerContainer = new VBox();
        centerContainer.setAlignment(Pos.CENTER);
        centerContainer.setSpacing(10);

        // Wrap the VBox in a ScrollPane
        scrollPane = new ScrollPane(centerContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        this.setCenter(scrollPane);
    }


    /**
     * regista os handlers
     */
    private void registerHandlers() {
        manager.addPropertyChangeListener(evt -> {
            Platform.runLater(this::update);
        });

        btnCSV.setOnAction(event -> {
            manager.selectOption(OPTIONS.CSV, null);
            update();
        });

        btnBack.setOnAction(event -> {
            manager.selectOption(OPTIONS.BACK, null);
            update();
        });



    }

    private void update() {
        if (manager.getState() != ClientStates.VIEW_PRESENCE) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);

        // Clear existing labels and buttons
        centerContainer.getChildren().clear();
        presencesLabel = new Label(manager.getPresences());

        centerContainer.getChildren().add(presencesLabel);

        String msg = manager.getLastMessage();
        if (msg != null) {
            messageLabel.setText(msg);
        }

       if(msg != null){
           hBox = new HBox(btnBack, btnCSV);
       }else {
           hBox = new HBox(btnBack);
       }
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);

        centerContainer.getChildren().add(hBox);

        registerHandlers();
        this.setCenter(centerContainer);
    }
}
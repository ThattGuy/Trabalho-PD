package pt.isec.pd.projetopd.cliente.ui.uistates.adminstatesui;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import pt.isec.pd.projetopd.cliente.model.Manager;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;

import java.util.ArrayList;
import java.util.List;

public class ViewEventPresencesUI extends BorderPane {

    private Manager manager;
    private Label messageLabel;
    private List<Label> presencesLabel;
    private VBox centerContainer;
    private HBox hBox;
    private Button btnBack, btnCSV;

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

        btnCSV = new Button("Get CSV File");
        btnCSV.setMinWidth(200);
        btnCSV.setMinHeight(50);

        messageLabel = new Label();
        messageLabel.getStyleClass().add("info");
        messageLabel.setTextFill(Color.RED);
        messageLabel.setStyle("-fx-font-size: 20px;");

        // Creating a VBox to add buttons and labels, setting it as the center of the BorderPane
        centerContainer = new VBox();
        centerContainer.setAlignment(Pos.CENTER);
        centerContainer.setSpacing(10);

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

    }

    private void update() {
        if (manager.getState() != ClientStates.VIEW_EVENT_PRESENCE) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);

        // Clear existing labels and buttons
        centerContainer.getChildren().clear();
        presencesLabel = new ArrayList<>();

        //todo
        List<String> presences = manager.getEvents();
        /*if (events != null) {
            for (int i = 0; i < events.size(); i++) {
                presencesLabel.add(new Label(events.get(i)));
            }
        }*/

        for (int i = 0; i < presencesLabel.size(); i++) {
            presencesLabel.get(i).setStyle("-fx-font-size: 16px;");
            presencesLabel.get(i).setMinSize(200, 50);
            centerContainer.getChildren().add(presencesLabel.get(i));
        }

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
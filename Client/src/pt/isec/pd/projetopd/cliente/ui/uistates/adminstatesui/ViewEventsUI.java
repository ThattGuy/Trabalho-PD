package pt.isec.pd.projetopd.cliente.ui.uistates.adminstatesui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import pt.isec.pd.projetopd.cliente.model.Manager;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;

public class ViewEventsUI extends BorderPane {

    private Manager manager;
    private Label messageLabel;
    private Label eventsLabel;

    public ViewEventsUI(Manager manager) {
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    /**
     * Cria os botões e imagens
     */
    private void createViews() {
        messageLabel = new Label();
        messageLabel.getStyleClass().add("info");
        messageLabel.setTextFill(Color.RED);
        messageLabel.setStyle("-fx-font-size: 20px;");

        eventsLabel = new Label();

        // Creating a VBox to add eventsLabel and setting it as the center of the BorderPane
        VBox centerContainer = new VBox(eventsLabel);
        centerContainer.setAlignment(Pos.CENTER);
        centerContainer.setSpacing(10); // Adjust the spacing as needed
        this.setCenter(centerContainer);
    }


    /**
     * regista os handlers
     */
    private void registerHandlers() {
        manager.addPropertyChangeListener(evt -> { Platform.runLater(this::update);});

    }

    private void update() {
        if (manager.getState() != ClientStates.VIEW_EVENTS) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);

        String events = manager.getEvents();
        if (events != null) {
            eventsLabel.setText(events);
        }

        String msg = manager.getLastMessage();
        if (msg != null) {
            messageLabel.setText(msg);
        }
    }

}

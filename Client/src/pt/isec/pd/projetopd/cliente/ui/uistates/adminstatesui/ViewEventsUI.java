package pt.isec.pd.projetopd.cliente.ui.uistates.adminstatesui;

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

public class ViewEventsUI extends BorderPane {

    private Manager manager;
    private Label messageLabel;
    private List<Label> eventsLabel;
    private VBox centerContainer;
    private HBox hBox;
    private Button btnBack;
    private List<Button> eventEditButtons;
    private List<Button> eventPresenceButtons;
    private ScrollPane scrollPane;

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
        btnBack = new Button("Back");
        btnBack.setMinWidth(200);
        btnBack.setMinHeight(50);

        messageLabel = new Label();
        messageLabel.getStyleClass().add("info");
        messageLabel.setTextFill(Color.RED);
        messageLabel.setStyle("-fx-font-size: 20px;");


        centerContainer = new VBox();
        centerContainer.setAlignment(Pos.CENTER);
        centerContainer.setSpacing(10);

        scrollPane = new ScrollPane(centerContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setMinHeight(400); // Adjust as needed
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Vertical scroll bar always shown

        this.setCenter(centerContainer);
    }

    /**
     * regista os handlers
     */
    private void registerHandlers() {
        manager.addPropertyChangeListener(evt -> { Platform.runLater(this::update);});

        btnBack.setOnAction(event -> {
            manager.selectOption(OPTIONS.BACK, null);
            update();
        });

        if(eventEditButtons != null){
            for (int i = 0; i < eventEditButtons.size(); i++) {
                int finalI = i;
                eventEditButtons.get(i).setOnAction(event ->
                        manager.selectOption(OPTIONS.EDIT_EVENT, String.valueOf(finalI))
                );
            }
        }
        if(eventPresenceButtons != null){
            for (int i = 0; i < eventPresenceButtons.size(); i++) {
                int finalI = i;
                eventPresenceButtons.get(i).setOnAction(event ->
                        manager.selectOption(OPTIONS.VIEW_PRESENCE, String.valueOf(finalI))
                );
            }
        }


    }

    private void update() {
        if (manager.getState() != ClientStates.VIEW_EVENTS) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);

        // Clear existing labels and buttons
        centerContainer.getChildren().clear();
        eventEditButtons = new ArrayList<>();
        eventPresenceButtons = new ArrayList<>();
        eventsLabel = new ArrayList<>();

        List<String> events = manager.getEvents();
        if (events != null) {
            for (int i = 0; i < events.size(); i++) {
                eventsLabel.add(new Label(events.get(i)));
            }
        }

        for (int i = 0; i < eventsLabel.size(); i++) {
            eventEditButtons.add(new Button("Edit"));
            eventPresenceButtons.add(new Button("Presences"));

            eventsLabel.get(i).setStyle("-fx-font-size: 16px;");

            eventsLabel.get(i).setMinSize(200, 50);
            eventEditButtons.get(i).setMinSize(25, 25);
            eventPresenceButtons.get(i).setMinSize(25, 25);

            HBox hEvent = new HBox(eventsLabel.get(i), eventPresenceButtons.get(i),eventEditButtons.get(i));

            hEvent.setSpacing(20);

            hEvent.setStyle("-fx-background-color: #D3D3D3;");
            hEvent.setAlignment(Pos.CENTER);
            centerContainer.getChildren().add(hEvent);
        }

        String msg = manager.getPresences();
        if (msg != null) {
            messageLabel.setText(msg);
        }

        hBox = new HBox(btnBack);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);

        centerContainer.getChildren().add(hBox);

        scrollPane = new ScrollPane(centerContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setMinHeight(10); // Adjust as needed

        registerHandlers();
        this.setCenter(centerContainer);

        //todo fix scroll
        //todo fix date picker

    }
}
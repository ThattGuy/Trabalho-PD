package pt.isec.pd.projetopd.cliente.ui.uistates.userstatesui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import pt.isec.pd.projetopd.cliente.model.Manager;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;

public class RegisterPresenceUI extends BorderPane {

    Manager manager;
    Button btnSubmit, btnBack;
    TextField presenceCode;
    private VBox vbox;
    private Label messageLabel;

    public RegisterPresenceUI(Manager manager) {
        this.manager = manager;
        createViews();
        registerHandlers();
        update();
    }

    /**
     * Cria os botões e imagens
     */
    private void createViews() {

        btnSubmit = new Button("Submit");
        btnSubmit.setMinWidth(200);
        btnSubmit.setMinHeight(50);


        btnBack = new Button("Back");
        btnBack.setMinWidth(200);
        btnBack.setMinHeight(50);

        presenceCode = new TextField();
        presenceCode.setPromptText("Enter Code");

        messageLabel = new Label();
        messageLabel.getStyleClass().add("info");
        messageLabel.setTextFill(Color.RED);
        messageLabel.setStyle("-fx-font-size: 20px;");

        HBox hBox = new HBox(btnBack, btnSubmit);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);

        vbox = new VBox(presenceCode, messageLabel, hBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(25);
        vbox.setPadding(new Insets(10));

        double fieldsWidthPercentage = 0.5;
        presenceCode.maxWidthProperty().bind(vbox.widthProperty().multiply(fieldsWidthPercentage));

        VBox container = new VBox(vbox);
        container.setAlignment(Pos.CENTER);

        this.setCenter(container);
    }


    /**
     * regista os handlers
     */
    private void registerHandlers() {

        manager.addPropertyChangeListener(evt -> { Platform.runLater(this::update);});

        btnSubmit.setOnAction(event -> {
            manager.selectOption(OPTIONS.SUBMIT, presenceCode.getText() );
            update();
        });

        this.setFocusTraversable(true);
        this.setOnKeyPressed((key) -> {
            if (key.getCode() == KeyCode.ENTER) {
                manager.selectOption(OPTIONS.SUBMIT, presenceCode.getText());
                update();
            }
        });

        btnBack.setOnAction(event -> {
            manager.selectOption(OPTIONS.BACK, null);
            update();
        });
    }

    private void update() {
        if (manager.getState() != ClientStates.REG_PRESENCE) {
            this.setVisible(false);
            return;
        }
        this.setVisible(true);

        String msg = manager.getLastMessage();
        if (msg != null) {
            messageLabel.setText(msg);
        }
    }
}


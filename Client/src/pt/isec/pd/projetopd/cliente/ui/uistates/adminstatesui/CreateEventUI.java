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

public class CreateEventUI extends BorderPane {

    private Manager manager;
    private Button btnSubmit, btnBack;
    private TextField eventName, local, date, beginning, endTime, codeExpirationTime;

    private VBox vbox;
    private GridPane grid;
    private HBox hBox;
    private Label messageLabel;

    public CreateEventUI(Manager manager) {
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

        Label lName = new Label("Name:");
        eventName = new TextField();
        eventName.setPromptText("Enter Name");

        Label lLocal = new Label("Local:");
        local = new TextField();
        local.setPromptText("Enter Local");

        Label lDate = new Label("Date:");
        date = new TextField();
        date.setPromptText("Enter Date");

        Label lBeginning = new Label("Beginning");
        beginning = new TextField();
        beginning.setPromptText("Enter Beginning");

        Label lEndTime = new Label("EndTime");
        endTime = new TextField();
        endTime.setPromptText("Enter EndTime");

        Label lCodeExpirationTime = new Label("Code Expiration Time");
        codeExpirationTime = new TextField();
        codeExpirationTime.setPromptText("Enter Code Expiration Time");

        messageLabel = new Label();
        messageLabel.getStyleClass().add("info");
        messageLabel.setTextFill(Color.RED);
        messageLabel.setStyle("-fx-font-size: 20px;");

        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        int rowIndex = 0;
        grid.add(lName, 0, rowIndex);
        grid.add(eventName, 1, rowIndex, 25, 1); // Make the text field span 2 columns

        rowIndex++;
        grid.add(lLocal, 0, rowIndex);
        grid.add(local, 1, rowIndex, 25, 1);

        rowIndex++;
        grid.add(lDate, 0, rowIndex);
        grid.add(date, 1, rowIndex, 25, 1);

        rowIndex++;
        grid.add(lBeginning, 0, rowIndex);
        grid.add(beginning, 1, rowIndex, 25, 1);

        rowIndex++;
        grid.add(lEndTime, 0, rowIndex);
        grid.add(endTime, 1, rowIndex, 20, 1);

        rowIndex++;
        grid.add(lCodeExpirationTime, 0, rowIndex);
        grid.add(codeExpirationTime, 1, rowIndex, 20, 1);

        hBox = new HBox(btnBack, btnSubmit);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);


        vbox = new VBox( grid, hBox, messageLabel);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(25);
        vbox.setPadding(new Insets(10));


        double fieldsWidthPercentage = 0.5;
        eventName.maxWidthProperty().bind(vbox.widthProperty().multiply(fieldsWidthPercentage));
        local.maxWidthProperty().bind(vbox.widthProperty().multiply(fieldsWidthPercentage));
        date.maxWidthProperty().bind(vbox.widthProperty().multiply(fieldsWidthPercentage));
        beginning.maxWidthProperty().bind(vbox.widthProperty().multiply(fieldsWidthPercentage));
        endTime.maxWidthProperty().bind(vbox.widthProperty().multiply(fieldsWidthPercentage));
        codeExpirationTime.maxWidthProperty().bind(vbox.widthProperty().multiply(fieldsWidthPercentage));

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
            String string = eventName.getText() + "\n"
                    + local.getText() + "\n"
                    + date.getText() + "\n"
                    + beginning.getText() + "\n"
                    + endTime.getText() + "\n"
                    + codeExpirationTime.getText();
            manager.selectOption(OPTIONS.SUBMIT, string);
            update();
        });

        this.setFocusTraversable(true);
        this.setOnKeyPressed((key) -> {
            if (key.getCode() == KeyCode.ENTER) {
                manager.selectOption(OPTIONS.SUBMIT, eventName.getText() + "\n" + local.getText());
                update();
            }
        });

        btnBack.setOnAction(event -> {
            manager.selectOption(OPTIONS.BACK, null);
            update();
        });
    }

    private void update() {
        if (manager.getState() != ClientStates.CREATE_EVENT) {
            eventName.setText(null);
            local.setText(null);
            date.setText(null);
            beginning.setText(null);
            endTime.setText(null);
            codeExpirationTime.setText(null);

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

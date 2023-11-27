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

import java.util.List;

public class EditEventUI extends BorderPane {

    private Manager manager;
    private Button btnSubmit, btnBack, btnNewCode, btnDelete;
    private TextField eventName, local, date, beginning, endTime, codeExpTime;
    private VBox vbox;
    private GridPane grid;
    private HBox hBox;
    private Label messageLabel, codesLabel;
    private List<String> eventVariables;
    private String nameS, locationS, dateS, beginningS, endTimeS;

    public EditEventUI(Manager manager) {
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

        btnNewCode = new Button("Generate New Presence Code");
        btnNewCode.setMinWidth(200);
        btnNewCode.setMinHeight(50);

        btnBack = new Button("Back");
        btnBack.setMinWidth(200);
        btnBack.setMinHeight(50);

        btnDelete = new Button("Delete");
        btnDelete.setMinWidth(200);
        btnDelete.setMinHeight(50);

        Label lName = new Label("Name:");
        eventName = new TextField();
        eventName.setPromptText("Enter new Name");

        Label lLocal = new Label("Location:");
        local = new TextField();
        local.setPromptText("Enter new Location");

        Label lDate = new Label("Date:");
        date = new TextField();
        date.setPromptText("Enter new Date");

        Label lBeginning = new Label("Beginning");
        beginning = new TextField();
        beginning.setPromptText("Enter new Beginning");

        Label lEndTime = new Label("EndTime");
        endTime = new TextField();
        endTime.setPromptText("Enter new EndTime");

        Label lCodeExpTime = new Label("Code Expiration Time");
        codeExpTime = new TextField();
        codeExpTime.setPromptText("Enter Code Expiration Time");

        codesLabel = new Label();

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
        grid.add(lCodeExpTime, 0, rowIndex);
        grid.add(codeExpTime, 1, rowIndex, 20, 1);

        hBox = new HBox(btnBack, btnSubmit,btnNewCode, btnDelete);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);


        vbox = new VBox(codesLabel, grid, hBox, messageLabel);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(25);
        vbox.setPadding(new Insets(10));


        double fieldsWidthPercentage = 0.5;
        eventName.maxWidthProperty().bind(vbox.widthProperty().multiply(fieldsWidthPercentage));
        local.maxWidthProperty().bind(vbox.widthProperty().multiply(fieldsWidthPercentage));
        date.maxWidthProperty().bind(vbox.widthProperty().multiply(fieldsWidthPercentage));
        beginning.maxWidthProperty().bind(vbox.widthProperty().multiply(fieldsWidthPercentage));
        endTime.maxWidthProperty().bind(vbox.widthProperty().multiply(fieldsWidthPercentage));
        codeExpTime.maxWidthProperty().bind(vbox.widthProperty().multiply(fieldsWidthPercentage));

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
                    + endTime.getText() + "\n";
            manager.selectOption(OPTIONS.SUBMIT, string);
            update();
        });

        this.setFocusTraversable(true);
        this.setOnKeyPressed((key) -> {
            String string = eventName.getText() + "\n"
                    + local.getText() + "\n"
                    + date.getText() + "\n"
                    + beginning.getText() + "\n"
                    + endTime.getText() + "\n";
            if (key.getCode() == KeyCode.ENTER) {
                manager.selectOption(OPTIONS.SUBMIT, string);
                update();
            }
        });

        btnBack.setOnAction(event -> {
            manager.selectOption(OPTIONS.BACK, null);
            update();
        });

        btnNewCode.setOnAction(event -> {
            manager.selectOption(OPTIONS.NEW_CODE, codeExpTime.getText());
            update();
        });

        btnDelete.setOnAction(event -> {
            manager.selectOption(OPTIONS.DELETE, null);
            update();
        });
    }

    private void update() {
        if (manager.getState() != ClientStates.EDIT_EVENT) {
            eventName.setText(null);
            local.setText(null);
            date.setText(null);
            beginning.setText(null);
            endTime.setText(null);
            codeExpTime.setText(null);


            this.setVisible(false);
            return;
        }
        this.setVisible(true);

        String msg = manager.getLastMessage();
        if (msg != null) {
            messageLabel.setText(msg);
        }

        eventVariables = manager.getEventVariables();
        nameS = eventVariables.get(0);
        locationS = eventVariables.get(1);
        dateS = eventVariables.get(2);
        beginningS = eventVariables.get(3);
        endTimeS = eventVariables.get(4);


        codesLabel.setText(manager.getEventCodes());

        eventName.setText(nameS);
        local.setText(locationS);
        date.setText(dateS);
        beginning.setText(beginningS);
        endTime.setText(endTimeS);

    }
}
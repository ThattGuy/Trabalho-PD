package pt.isec.pd.projetopd.cliente.ui.uistates;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import pt.isec.pd.projetopd.cliente.model.Manager;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;

public class UserInfoUI extends VBox {

    Manager manager;
    VBox vb;
    public UserInfoUI(Manager manager) {
        this.manager = manager;

        createViews();
        registerHandlers();
        update();
    }

    private void createViews() {
        setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        setPadding(new Insets(10));

        vb = new VBox();
        vb.setAlignment(Pos.CENTER);

        this.getChildren().addAll(vb);
    }

    private void registerHandlers() {
        manager.addPropertyChangeListener(evt -> { Platform.runLater(this::update);});
    }

    private void update() {
        if (manager.getState() == ClientStates.SELECT_OPT || manager.getState() == ClientStates.SELECT_OPT_ADMIN) {
            setVisible(true);

            Label userName = new Label(manager.getUserName());

            // Set a larger font size for the username label
            userName.setStyle("-fx-font-size: 20;"); // Change 16 to the desired font size

            vb.getChildren().clear();  // Clear any existing children
            vb.getChildren().add(userName);
        } else {
            setVisible(false);
        }
    }


}

package pt.isec.pd.projetopd.cliente.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pt.isec.pd.projetopd.cliente.Main;
import pt.isec.pd.projetopd.cliente.model.Manager;

import java.util.Optional;

public class MainJFX extends Application {
    Manager manager;


    @Override
    public void init() throws Exception {
        super.init();
        manager = Main.manager;
    }

    @Override
    public void start(Stage stage) {
        newStage(stage);
    }


    /**
     * Cria a cena principal
     * @param stage recebe o stage da cena
     * ajusta o tamanho da janela
     */
    private void newStage(Stage stage) {
        RootPane root = new RootPane(manager);

        Scene scene = new Scene(root,1100,500);

        stage.setOnCloseRequest(e -> {
            e.consume();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Exit");
            alert.setHeaderText("Are you sure you want to exit?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                System.exit(0);
            }
        });

        stage.setScene(scene);
        stage.setTitle("TP PD");
        stage.setMinWidth(800);
        stage.setMinHeight(670);
        stage.show();
    }
}

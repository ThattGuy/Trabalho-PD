package pt.isec.pd.projetopd.cliente.ui;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import pt.isec.pd.projetopd.cliente.model.Manager;
import pt.isec.pd.projetopd.cliente.ui.resources.ImageManager;
import pt.isec.pd.projetopd.cliente.ui.uistates.admin.CreateEventUI;
import pt.isec.pd.projetopd.cliente.ui.uistates.shared.InitialUI;
import pt.isec.pd.projetopd.cliente.ui.uistates.shared.LoginUI;
import pt.isec.pd.projetopd.cliente.ui.uistates.shared.RegisterUI;
import pt.isec.pd.projetopd.cliente.ui.uistates.shared.SelectOptUI;

public class RootPane extends BorderPane {
    Manager manager;

    public RootPane(Manager manager) {
        this.manager = manager;

        createViews();
        //registerHandlers();
    }

    /**
     * cria todos os panes necessários
     * carrega o ficheiro css
     * cria o backgroud
     */
    private void createViews() {
        //CSSManager.applyCSS(this,"styles.css");

        StackPane stackPane = new StackPane(
                new InitialUI(manager),
                new LoginUI(manager),
                new RegisterUI(manager),
                new SelectOptUI(manager),
                new CreateEventUI(manager)
        );
        Label watermark = new Label("Developed by: Tiago Garcia Quintas, 2019128044");
        watermark.getStyleClass().add("watermark");
        StackPane.setAlignment(watermark, Pos.BOTTOM_RIGHT);
        stackPane.getChildren().add(watermark);

        ImageView watermarkImage = new ImageView(ImageManager.getImage("isec.png"));
        watermarkImage.setPreserveRatio(true);
        watermarkImage.setFitWidth(25);
        StackPane.setAlignment(watermarkImage, Pos.BOTTOM_LEFT);
        stackPane.getChildren().add(watermarkImage);

        this.setCenter(stackPane);
    }

    /**
     * regista o handler START
     */
    /*private void registerHandlers() {
        manager.addPropertyChangeListener(Manager.START, evt -> {
            update();
        });
    }*/

    /**
     * assim que o evento START acontece é criado a InfoUI
     */

}


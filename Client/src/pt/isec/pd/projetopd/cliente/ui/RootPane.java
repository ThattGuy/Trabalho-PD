package pt.isec.pd.projetopd.cliente.ui;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import pt.isec.pd.projetopd.cliente.model.Manager;
import pt.isec.pd.projetopd.cliente.ui.resources.ImageManager;
import pt.isec.pd.projetopd.cliente.ui.uistates.adminstatesui.CreateEventUI;
import pt.isec.pd.projetopd.cliente.ui.uistates.adminstatesui.SelectOptAdminUI;
import pt.isec.pd.projetopd.cliente.ui.uistates.adminstatesui.ViewEventsUI;
import pt.isec.pd.projetopd.cliente.ui.uistates.sharedstatesui.*;
import pt.isec.pd.projetopd.cliente.ui.uistates.userstatesui.RegisterPresenceUI;
import pt.isec.pd.projetopd.cliente.ui.uistates.userstatesui.RegisterUserUI;
import pt.isec.pd.projetopd.cliente.ui.uistates.userstatesui.SelectOptUserUI;
import pt.isec.pd.projetopd.cliente.ui.uistates.userstatesui.ViewPresenceUI;

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
                new RegisterUserUI(manager),
                new SelectOptUserUI(manager),
                new SelectOptAdminUI(manager),
                new CreateEventUI(manager),
                new RegisterPresenceUI(manager),
                new EditInfoUI(manager),
                new ViewEventsUI(manager),
                new ViewPresenceUI(manager)
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

}


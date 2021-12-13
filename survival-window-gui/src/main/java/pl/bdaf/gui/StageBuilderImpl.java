package pl.bdaf.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class StageBuilderImpl implements StageBuilder{
    private Controller controller = new MenuController(null);;
    private String viewName = "menu.fxml";
    private String iconName = "icon.jpg";
    private String title = "Survival";
    private boolean isResizable;
    private Window owner;


    @Override
    public StageBuilder iconName(String aIconName) {
        iconName = aIconName;
        return this;
    }

    @Override
    public StageBuilder controller(Controller aController) {
        controller = aController;
        return this;
    }

    @Override
    public StageBuilder viewName(String aViewName) {
        viewName = aViewName;
        return this;
    }

    @Override
    public StageBuilder title(String aTitle) {
        title = aTitle;
        return this;
    }

    @Override
    public StageBuilder isResizable(boolean aIsResizable) {
        isResizable = aIsResizable;
        return this;
    }

    @Override
    public StageBuilder owner(Window aOwner) {
        owner = aOwner;
        return this;
    }

    @Override
    public Stage build() {

        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();

        // if set
        if(owner != null) stage.initOwner(owner);

        loader.setLocation(DiaryController.class.getClassLoader().getResource("fxml/"+viewName));
        stage.getIcons().add(new Image("graphics/"+iconName));
        stage.setTitle(title);
        loader.setController(controller);

        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException aE) {
            aE.printStackTrace();
        }
        stage.setResizable(isResizable);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        return stage;
    }
}

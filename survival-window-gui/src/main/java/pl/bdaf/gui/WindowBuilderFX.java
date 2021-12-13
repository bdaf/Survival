package pl.bdaf.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class WindowBuilderFX implements WindowBuilder {
    private Controller controller;
    private String viewName;
    private String iconName = "icon.jpg";
    private String title = "Survival";
    private boolean isResizable;
    private Window owner;
    private Stage stage;
    private Modality modality;


    @Override
    public WindowBuilder iconName(String aIconName) {
        iconName = aIconName;
        return this;
    }

    @Override
    public WindowBuilder controller(Controller aController) {
        controller = aController;
        return this;
    }

    @Override
    public WindowBuilder viewName(String aViewName) {
        viewName = aViewName;
        return this;
    }

    @Override
    public WindowBuilder stage(Stage aWindow) {
        stage = aWindow;
        return this;
    }

    @Override
    public WindowBuilder title(String aTitle) {
        title = aTitle;
        return this;
    }

    @Override
    public WindowBuilder isResizable(boolean aIsResizable) {
        isResizable = aIsResizable;
        return this;
    }

    @Override
    public WindowBuilder modality(Modality aApplicationModal) {
        modality = aApplicationModal;
        return this;
    }

    @Override
    public WindowBuilder owner(Window aOwner) {
        owner = aOwner;
        return this;
    }

    public Stage build() {
        if (controller == null) throw new IllegalStateException("Controller has not been specified");
        if (viewName == null) throw new IllegalStateException("Name of view (fxml file) has not been specified");
        if (stage == null) stage = new Stage();
        if (owner != null) stage.initOwner(owner);
        if (modality != null) stage.initModality(modality);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DiaryController.class.getClassLoader().getResource("fxml/" + viewName));
        stage.getIcons().add(new Image("graphics/" + iconName));
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

        return stage;
    }
}

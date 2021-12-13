package pl.bdaf.gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class StageBuilderImpl implements StageBuilder{
    private static final String NOT_DEFINED = "NOT_DEFINED";
    private String iconName = NOT_DEFINED;
    private String viewName = NOT_DEFINED;
    private String title = NOT_DEFINED;
    private boolean isResizable;
    private Window owner;
    private Controller controller;


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
        if(!viewName.equals(NOT_DEFINED)) loader.setLocation(DiaryController.class.getClassLoader().getResource("fxml/"+viewName));
        if(!iconName.equals(NOT_DEFINED)) stage.getIcons().add(new Image("graphics/"+iconName));
        if(!title.equalsIgnoreCase(NOT_DEFINED)) stage.setTitle(title);
        if(controller != null) loader.setController(controller);
        if(owner != null) stage.initOwner(owner);

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

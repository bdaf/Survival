package pl.bdaf.gui;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public interface WindowBuilder {

    WindowBuilder controller(Controller aController);
    WindowBuilder isResizable(boolean aIsResizable);
    WindowBuilder modality(Modality aApplicationModal);
    WindowBuilder iconName(String aIconName);
    WindowBuilder viewName(String aViewName);
    WindowBuilder stage(Stage aWindow);
    WindowBuilder title(String aTitle);
    WindowBuilder owner(Window aOwner);
}

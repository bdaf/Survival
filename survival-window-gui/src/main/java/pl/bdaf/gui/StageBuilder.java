package pl.bdaf.gui;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public interface StageBuilder {

    StageBuilder controller(Controller aController);
    StageBuilder isResizable(boolean aIsResizable);
    StageBuilder modality(Modality aApplicationModal);
    StageBuilder iconName(String aIconName);
    StageBuilder viewName(String aViewName);
    StageBuilder stage(Stage aWindow);
    StageBuilder title(String aTitle);

    StageBuilder owner(Window aOwner);

    Stage build();
}

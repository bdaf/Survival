package pl.bdaf.gui;

import javafx.stage.Stage;
import javafx.stage.Window;

public interface StageBuilder {

    StageBuilder iconName(String aIconName);
    StageBuilder controller(Controller aController);
    StageBuilder viewName(String aViewName);
    StageBuilder title(String aTitle);
    StageBuilder isResizable(boolean aIsResizable);
    StageBuilder owner(Window aOwner);
    Stage build();
}

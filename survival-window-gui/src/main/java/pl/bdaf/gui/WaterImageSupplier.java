package pl.bdaf.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import pl.bdaf.person.GameEngine;

import static pl.bdaf.person.Backpack.WATER_BOTTLE;

public class WaterImageSupplier {

    public VBox addSupplyImageToVBox(VBox aLeftVBox, GameEngine aGameEngine){
        int amountOfSupply = aGameEngine.getAmountOf(getNameOfSupply());
        for (int i = 0; i < amountOfSupply; i+=4) {
            if(i>12) break;
            ImageView imageView = new ImageView();
            imageView.setFitWidth(19);
            imageView.setFitHeight(30);
            Image image = new Image(getClass().getResourceAsStream("/graphics/"+ getNameOfSupply() +".png"));
            imageView.setImage(image);
            aLeftVBox.getChildren().add(imageView);
        }
        return aLeftVBox;
    }

    protected String getNameOfSupply() {
        return WATER_BOTTLE;
    }

}

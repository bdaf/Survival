package pl.bdaf.gui;

import static pl.bdaf.person.Backpack.TOMATO_SOUP;

public class SoupImageSupplier extends WaterImageSupplier {

    @Override
    protected String getNameOfSupply() {
        return TOMATO_SOUP;
    }
}

package pl.bdaf.person;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Backpack {
    public static final String TOMATO_SOUP = "Tomato soup";
    public static final String WATER_BOTTLE = "Water bottle";
    public static final int MAX_OF_WATER = 20;
    public static final int MIN_OF_WATER = 10;
    public static final int MAX_OF_SOUP = 6;
    public static final int MIN_OF_SOUP = 12;
    private final List<String> content;

    Backpack(List<String> aContent) {
        content = new LinkedList<>(aContent);
    }

    Backpack() {
        content = new LinkedList<>();
        addRandomAmountOf(WATER_BOTTLE,MIN_OF_WATER,MAX_OF_WATER);
        addRandomAmountOf(TOMATO_SOUP,MIN_OF_SOUP,MAX_OF_SOUP);
    }

    List<String> getContent() {
        return content;
    }

    int getAmountOf(String aResource){
        return (int) content.stream().filter(r -> r.equalsIgnoreCase(aResource)).count();
    }

    private void addRandomAmountOf(String aResource, int aMinBorder, int aMaxBorder) {
        for (int i = 0; i < new Random().nextInt(MAX_OF_WATER-MIN_OF_WATER) + MIN_OF_WATER; i++) {
            content.add(aResource);
        }
    }

}

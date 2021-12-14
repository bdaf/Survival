package pl.bdaf.person;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Backpack {
    public static final String TOMATO_SOUP = "tomato_soup";
    public static final String WATER_BOTTLE = "water_bottle";
    public static final int MAX_OF_WATER = 20;
    public static final int MIN_OF_WATER = 10;
    public static final int MAX_OF_SOUP = 12;
    public static final int MIN_OF_SOUP = 6;
    // content contains only tomato soups and water bottles - 2 things in different amounts
    private final List<String> content;
    private Random rand = new Random();

    Backpack(List<String> aContent) {
        content = new ArrayList<>(aContent);
    }

    Backpack() {
        this(new Random());
    }

    Backpack(Random aRand) {
        rand = aRand;
        content = new LinkedList<>();
        addRandomAmountOf(WATER_BOTTLE,MIN_OF_WATER,MAX_OF_WATER);
        addRandomAmountOf(TOMATO_SOUP,MIN_OF_SOUP,MAX_OF_SOUP);
    }

    public Backpack(Backpack aBackpack) {
        content = new ArrayList<>(aBackpack.getContent());
    }

    List<String> getContent() {
        return List.copyOf(content);
    }

    private void addRandomAmountOf(String aResource, int aMinBorder, int aMaxBorder) {
        for (int i = 0; i < rand.nextInt(aMaxBorder-aMinBorder) + aMinBorder; i++) {
            content.add(aResource);
        }
    }

    int getAmountOf(String aResource){
        throwExceptionIfStringIsNotProperty(aResource);
        return (int) content.stream().filter(r -> r.equalsIgnoreCase(aResource)).count();
    }

    void removeSupply(String aResource, int aAmountOfSupply) {
        throwExceptionIfStringIsNotProperty(aResource);
        for (int i = 0; i < aAmountOfSupply; i++) {
            content.remove(aResource);
        }
    }

    private void throwExceptionIfStringIsNotProperty(String aResource) {
        if(!aResource.equalsIgnoreCase(TOMATO_SOUP) && !aResource.equalsIgnoreCase(WATER_BOTTLE))
            throw new IllegalArgumentException("Argument should be \"Tomato soup\" or \"Water bottle\"!");
    }

    void addSupply(String sResource) {
        throwExceptionIfStringIsNotProperty(sResource);
        content.add(sResource);
    }
}

package pl.bdaf.person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.bdaf.person.State.HEALTHY;

public class BackpackTest {

    private Random randomize;
    private Person person;
    private State state;
    private Backpack backpack;

    @BeforeEach
    void init() {
        randomize = new Random();
        randomize = mock(Random.class);
        when(randomize.nextInt(anyInt())).thenReturn(0);
        state = new State.Healthy(randomize);
        person = new Person(PersonStatistic.TO_TEST);
        person.setState(state);
        backpack = new Backpack(randomize);
    }

    @Test
    void backpackShouldContain10WaterBottlesAnd6SoupsAndAfterItPlus3() {
        assertEquals(10, backpack.getAmountOf(Backpack.WATER_BOTTLE));
        assertEquals(6, backpack.getAmountOf(Backpack.TOMATO_SOUP));
        when(randomize.nextInt(anyInt())).thenReturn(3);
        backpack = new Backpack(randomize);
        assertEquals(13, backpack.getAmountOf(Backpack.WATER_BOTTLE));
        assertEquals(9, backpack.getAmountOf(Backpack.TOMATO_SOUP));
    }
}

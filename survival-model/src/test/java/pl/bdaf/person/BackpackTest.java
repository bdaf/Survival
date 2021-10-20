package pl.bdaf.person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BackpackTest {

    private Random randomize;
    private Backpack backpack;

    @BeforeEach
    void init() {
        randomize = new Random();
        randomize = mock(Random.class);
        when(randomize.nextInt(anyInt())).thenReturn(0);
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

    @Test
    void shouldThrowIllegalArgumentExceptionWhenYouGetAmountOfNoWaterAndNoSoup() {
        assertThrows(IllegalArgumentException.class, () -> backpack.getAmountOf("Tomato_soup"));
    }
}

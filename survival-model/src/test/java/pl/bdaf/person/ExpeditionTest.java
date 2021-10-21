package pl.bdaf.person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.bdaf.person.Backpack.TOMATO_SOUP;
import static pl.bdaf.person.Backpack.WATER_BOTTLE;
import static pl.bdaf.person.PersonStatistic.*;
import static pl.bdaf.person.State.HEALTHY;

public class ExpeditionTest {

    private Random randomize;
    private Backpack backpack;
    private GameEngine engine;
    private Person ted;

    @BeforeEach
    void init() {
        randomize = new Random();
        randomize = mock(Random.class);
        backpack = new Backpack(List.of(WATER_BOTTLE, WATER_BOTTLE, WATER_BOTTLE ,TOMATO_SOUP, TOMATO_SOUP, TOMATO_SOUP));
        assertEquals(3, backpack.getAmountOf(Backpack.WATER_BOTTLE));
        assertEquals(3, backpack.getAmountOf(Backpack.TOMATO_SOUP));
        ted = new Person(TED);
        ted.setState(new State.Unhealthy(randomize));
        List<Person> people = new LinkedList<>();
        people.addAll(List.of(ted, new Person(DOLORES),new Person(TIMMY),new Person(BERTA)));
        engine = new GameEngine(people,backpack);
    }

    @Test
    void tedShouldGoForExpeditionFor2DaysAndGain3WaterBottlesAnd3TomatoSoupsAndReturnHealthy(){
        assertEquals(1, engine.getCurrentDay());
        assertEquals(TED.getName(), engine.getActivePerson().getName());
        when(randomize.nextInt(anyInt())).thenReturn(1);
        engine.goForExpedition();
        engine.pass();
        assertEquals(DOLORES.getName(), engine.getActivePerson().getName());
        engine.pass();
        assertEquals(TIMMY.getName(), engine.getActivePerson().getName());
        engine.pass();
        assertEquals(BERTA.getName(), engine.getActivePerson().getName());
        engine.pass();
        // 2th day starts
        assertEquals(DOLORES.getName(), engine.getActivePerson().getName());
        engine.drink();
        engine.eat();
        engine.pass();
        assertEquals(TIMMY.getName(), engine.getActivePerson().getName());
        engine.drink();
        engine.eat();
        engine.pass();
        assertEquals(BERTA.getName(), engine.getActivePerson().getName());
        engine.drink();
        engine.eat();
        assertEquals(0, engine.getAmountOf(WATER_BOTTLE));
        assertEquals(0, engine.getAmountOf(TOMATO_SOUP));
        assertThrows(IllegalStateException.class, () -> engine.drink());
        assertThrows(IllegalStateException.class, () -> engine.eat());
        assertEquals(30, ted.getStrength());
        when(randomize.nextDouble()).thenReturn(0.7);
        when(randomize.nextBoolean()).thenReturn(true);
        when(randomize.nextInt(anyInt())).thenReturn(1);
        when(randomize.nextInt(anyInt())).thenReturn(1);
        engine.pass();
        // 3th day starts, ted returns
        assertEquals(TED.getName(), engine.getActivePerson().getName());
        assertEquals(3, engine.getAmountOf(WATER_BOTTLE));
        assertEquals(3, engine.getAmountOf(TOMATO_SOUP));
        assertEquals(HEALTHY, ted.getState().toString());
        engine.pass();
        assertEquals(DOLORES.getName(), engine.getActivePerson().getName());
        engine.pass();
        assertEquals(TIMMY.getName(), engine.getActivePerson().getName());
        engine.pass();
        assertEquals(BERTA.getName(), engine.getActivePerson().getName());
    }

    @Test
    void tedShouldGoForExpeditionFor1DayAndGain1WaterBottleAnd1TomatoSoupAndReturnUnhealthy(){
        assertEquals(1, engine.getCurrentDay());
        assertEquals(TED.getName(), engine.getActivePerson().getName());
        when(randomize.nextInt(anyInt())).thenReturn(1);
        engine.goForExpedition();
        engine.pass();
        assertEquals(DOLORES.getName(), engine.getActivePerson().getName());
        engine.pass();
        assertEquals(TIMMY.getName(), engine.getActivePerson().getName());
        engine.pass();
        assertEquals(BERTA.getName(), engine.getActivePerson().getName());
        engine.pass();
        // 2th day starts
        assertEquals(DOLORES.getName(), engine.getActivePerson().getName());
        engine.drink();
        engine.eat();
        engine.pass();
        assertEquals(TIMMY.getName(), engine.getActivePerson().getName());
        engine.drink();
        engine.eat();
        engine.pass();
        assertEquals(BERTA.getName(), engine.getActivePerson().getName());
        engine.drink();
        engine.eat();
        assertEquals(0, engine.getAmountOf(WATER_BOTTLE));
        assertEquals(0, engine.getAmountOf(TOMATO_SOUP));
        assertThrows(IllegalStateException.class, () -> engine.drink());
        assertThrows(IllegalStateException.class, () -> engine.eat());
        assertEquals(30, ted.getStrength());
        when(randomize.nextDouble()).thenReturn(0.7);
        when(randomize.nextBoolean()).thenReturn(true);
        when(randomize.nextInt(anyInt())).thenReturn(1);
        when(randomize.nextInt(anyInt())).thenReturn(1);
        engine.pass();
        // 3th day starts, ted returns
        assertEquals(TED.getName(), engine.getActivePerson().getName());
        assertEquals(3, engine.getAmountOf(WATER_BOTTLE));
        assertEquals(3, engine.getAmountOf(TOMATO_SOUP));
        assertEquals(HEALTHY, ted.getState().toString());
        engine.pass();
        assertEquals(DOLORES.getName(), engine.getActivePerson().getName());
        engine.pass();
        assertEquals(TIMMY.getName(), engine.getActivePerson().getName());
        engine.pass();
        assertEquals(BERTA.getName(), engine.getActivePerson().getName());
    }
}

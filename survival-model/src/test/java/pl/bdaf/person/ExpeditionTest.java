package pl.bdaf.person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.bdaf.person.GameEngine.TOMATO_SOUP;
import static pl.bdaf.person.GameEngine.WATER_BOTTLE;
import static pl.bdaf.person.PersonStatistic.*;
import static pl.bdaf.person.State.*;

public class ExpeditionTest {

    public static final int BEGINNING_TED_STRENGTH = 30;
    private Random randomize;
    private Backpack backpack;
    private GameEngine engine;
    private Person ted;

    @BeforeEach
    void init() {
        randomize = new Random();
        randomize = mock(Random.class);
        backpack = new Backpack(List.of(WATER_BOTTLE, WATER_BOTTLE, WATER_BOTTLE ,TOMATO_SOUP, TOMATO_SOUP, TOMATO_SOUP));
        assertEquals(3, backpack.getAmountOf(WATER_BOTTLE));
        assertEquals(3, backpack.getAmountOf(TOMATO_SOUP));
        ted = new Person(TED);
        ted.setState(new State.Healthy(randomize));
        List<Person> people = new LinkedList<>();
        people.addAll(List.of(ted, new Person(DOLORES),new Person(TIMMY),new Person(BERTA)));
        engine = new GameEngine(people,backpack);
    }

    @Test
    void tedShouldCannotGoForExpeditionBecauseHeIsTheOnlyPersonInShelter(){
        assertEquals(1, engine.getCurrentDay());
        assertEquals(TED.getName(), engine.getActivePerson().getName());
        engine.passToNextPerson();
        assertEquals(DOLORES.getName(), engine.getActivePerson().getName());
        engine.getActivePerson().setWorseState();
        engine.getActivePerson().setWorseState();
        engine.getActivePerson().setWorseState();
        assertEquals(DEAD, engine.getActivePerson().getState().getName());
        engine.passToNextPerson();
        assertEquals(TIMMY.getName(), engine.getActivePerson().getName());
        engine.getActivePerson().setWorseState();
        engine.getActivePerson().setWorseState();
        engine.getActivePerson().setWorseState();
        assertEquals(DEAD, engine.getActivePerson().getState().getName());
        engine.passToNextPerson();
        assertEquals(BERTA.getName(), engine.getActivePerson().getName());
        engine.getActivePerson().setWorseState();
        engine.getActivePerson().setWorseState();
        engine.getActivePerson().setWorseState();
        assertEquals(DEAD, engine.getActivePerson().getState().getName());
        engine.passToNextPerson();
        assertEquals(TED.getName(), engine.getActivePerson().getName());
        // 2 th day starts
        when(randomize.nextInt(anyInt())).thenReturn(1);
        engine.goForExpeditionAndPass();
        // Ted is still active creature, nothing happened
        assertEquals(TED.getName(), engine.getActivePerson().getName());
        assertEquals(0, engine.getActivePerson().getExpeditionDaysLeft());
    }

    @Test
    void tedShouldGoForExpeditionFor2DaysAndGain3WaterBottlesAnd3TomatoSoupsAndReturnHealthy(){
        assertEquals(1, engine.getCurrentDay());
        assertEquals(TED.getName(), engine.getActivePerson().getName());
        assertEquals(BEGINNING_TED_STRENGTH, engine.getActivePerson().getStrength());
        when(randomize.nextInt(anyInt())).thenReturn(1);
        engine.goForExpeditionAndPass();
        assertEquals(DOLORES.getName(), engine.getActivePerson().getName());
        engine.passToNextPerson();
        assertEquals(TIMMY.getName(), engine.getActivePerson().getName());
        engine.passToNextPerson();
        assertEquals(BERTA.getName(), engine.getActivePerson().getName());
        engine.passToNextPerson();
        // 2th day starts
        assertEquals(DOLORES.getName(), engine.getActivePerson().getName());
        engine.drink();
        engine.eat();
        engine.passToNextPerson();
        assertEquals(TIMMY.getName(), engine.getActivePerson().getName());
        engine.drink();
        engine.eat();
        engine.passToNextPerson();
        assertEquals(BERTA.getName(), engine.getActivePerson().getName());
        engine.drink();
        engine.eat();
        assertEquals(0, engine.getAmountOf(WATER_BOTTLE));
        assertEquals(0, engine.getAmountOf(TOMATO_SOUP));
        assertEquals(30, ted.getStrength());
        when(randomize.nextDouble()).thenReturn(0.7);
        when(randomize.nextBoolean()).thenReturn(true);
        engine.passToNextPerson();
        // 3th day starts, Ted returns
        assertEquals(TED.getName(), engine.getActivePerson().getName()); // +1 because of regeneration
        assertEquals(BEGINNING_TED_STRENGTH *3/10-2+1, engine.getActivePerson().getStrength());
        assertEquals(3, engine.getAmountOf(WATER_BOTTLE));
        assertEquals(3, engine.getAmountOf(TOMATO_SOUP));
        assertEquals(HEALTHY, ted.getState().getName());
        engine.passToNextPerson();
        assertEquals(DOLORES.getName(), engine.getActivePerson().getName());
        engine.passToNextPerson();
        assertEquals(TIMMY.getName(), engine.getActivePerson().getName());
        engine.passToNextPerson();
        assertEquals(BERTA.getName(), engine.getActivePerson().getName());
    }

    @Test
    void tedShouldGoForExpeditionFor1DayAndGain1WaterBottleAnd1TomatoSoupAndReturnUnhealthy(){
        assertEquals(1, engine.getCurrentDay());
        assertEquals(TED.getName(), engine.getActivePerson().getName());
        when(randomize.nextInt(anyInt())).thenReturn(0);
        engine.goForExpeditionAndPass();
        assertEquals(DOLORES.getName(), engine.getActivePerson().getName());
        engine.passToNextPerson();
        assertEquals(TIMMY.getName(), engine.getActivePerson().getName());
        engine.passToNextPerson();
        assertEquals(BERTA.getName(), engine.getActivePerson().getName());
        assertEquals(HEALTHY, ted.getState().getName());
        assertEquals(3, engine.getAmountOf(WATER_BOTTLE));
        assertEquals(3, engine.getAmountOf(TOMATO_SOUP));
        assertEquals(30, ted.getStrength());
        when(randomize.nextDouble()).thenReturn(0.90);
        when(randomize.nextBoolean()).thenReturn(true);
        when(randomize.nextInt(anyInt())).thenReturn(89);
        engine.passToNextPerson();
        // 2th day starts
        assertEquals(UNHEALTHY, ted.getState().getName());
        assertEquals(TED.getName(), engine.getActivePerson().getName());
        assertEquals(4, engine.getAmountOf(WATER_BOTTLE));
        assertEquals(4, engine.getAmountOf(TOMATO_SOUP));
        engine.passToNextPerson();
        assertEquals(DOLORES.getName(), engine.getActivePerson().getName());
        engine.passToNextPerson();
        assertEquals(TIMMY.getName(), engine.getActivePerson().getName());
        engine.passToNextPerson();
        assertEquals(BERTA.getName(), engine.getActivePerson().getName());
    }

    @Test
    void tedShouldGoForExpeditionFor1DayAndGainNothingAndReturnSickBecauseHeWasAlreadyUnhealthy(){
        assertEquals(1, engine.getCurrentDay());
        ted.setWorseState();
        assertEquals(UNHEALTHY, ted.getState().getName());
        assertEquals(TED.getName(), engine.getActivePerson().getName());
        when(randomize.nextInt(anyInt())).thenReturn(0);
        engine.goForExpeditionAndPass();
        assertEquals(DOLORES.getName(), engine.getActivePerson().getName());
        engine.passToNextPerson();
        assertEquals(TIMMY.getName(), engine.getActivePerson().getName());
        engine.passToNextPerson();
        assertEquals(BERTA.getName(), engine.getActivePerson().getName());
        assertEquals(UNHEALTHY, ted.getState().getName());
        assertEquals(3, engine.getAmountOf(WATER_BOTTLE));
        assertEquals(3, engine.getAmountOf(TOMATO_SOUP));
        assertEquals(30, ted.getStrength());
        when(randomize.nextDouble()).thenReturn(0.91);
        when(randomize.nextBoolean()).thenReturn(true);
        when(randomize.nextInt(anyInt())).thenReturn(0);
        engine.passToNextPerson();
        // 2th day starts
        assertEquals(SICK, ted.getState().getName());
        assertEquals(TED.getName(), engine.getActivePerson().getName());
        assertEquals(3, engine.getAmountOf(WATER_BOTTLE));
        assertEquals(3, engine.getAmountOf(TOMATO_SOUP));
        engine.passToNextPerson();
        assertEquals(DOLORES.getName(), engine.getActivePerson().getName());
        engine.passToNextPerson();
        assertEquals(TIMMY.getName(), engine.getActivePerson().getName());
        engine.passToNextPerson();
        assertEquals(BERTA.getName(), engine.getActivePerson().getName());
    }
}

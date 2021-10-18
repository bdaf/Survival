package pl.bdaf.person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.bdaf.person.State.HEALTHY;
import static pl.bdaf.person.State.MEDIUM;

public class StateTest {

    private Random randomizer;
    private Person person;
    private State state;

    @BeforeEach
    void init() {
        randomizer = new Random();
        randomizer = mock(Random.class);
        state = new State.Healthy(randomizer);
        person = new Person(10, state);
    }

    @Test
    void personShouldGet2StrengthPointByDrinkingTwiceAnd3PointsByEatingBeingHealthy() {
        person.drink();
        assertEquals(11, person.getStrength());
        person.drink();
        assertEquals(12, person.getStrength());
        person.eat();
        assertEquals(15, person.getStrength());
    }

    @Test
    void personShouldGet9StrengthPointByEating3TimesBeingHealthy() {
        person.eat();
        assertEquals(13, person.getStrength());
        person.eat();
        assertEquals(16, person.getStrength());
        person.eat();
        assertEquals(19, person.getStrength());
    }

    @Test
    void personInMediumStateShouldBecameHealthyByDrinking() {
        person.setWorseState();
        assertEquals(MEDIUM, person.getState().toString());
        when(randomizer.nextDouble()).thenReturn(0.8);
        person.drink();
        assertEquals(MEDIUM, person.getState().toString());

        when(randomizer.nextDouble()).thenReturn(0.79);
        person.drink();
        assertEquals(HEALTHY, person.getState().toString());
    }

}

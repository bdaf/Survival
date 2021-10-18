package pl.bdaf.person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StateTest {

    private Random randomizer;

    @BeforeEach
    void init(){
        randomizer = new Random();
        randomizer = mock(Random.class);
        when(randomizer.nextDouble()).thenReturn(0.1);
    }

    @Test
    void personShouldGet1StrengthPointByDrinkingAnd3PointsByEating(){
        Person person = new Person(10);
        State state = new State.HealthyState(person);
        state.drink();
        assertEquals(11,person.getStrength());
        state.eat();
        assertEquals(14,person.getStrength());
    }
}

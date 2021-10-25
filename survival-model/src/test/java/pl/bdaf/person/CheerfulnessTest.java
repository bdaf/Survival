package pl.bdaf.person;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheerfulnessTest {

    @Test
    void personShouldBeMoreDelightedAbout5PointsAfterExpedition(){
        Person person = new Person(PersonStatistic.TO_TEST);
        assertEquals(10,person.getCheerfulness());
        person.goForExpedition(new Backpack());
        assertEquals(15,person.getCheerfulness());
    }
}

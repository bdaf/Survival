package pl.bdaf.person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.bdaf.person.PersonStatistic.*;

public class GameTest {

    private Person ted;
    private Person dolores;
    private Person timmy;
    private Person berta;
    private GameQueue gameQueue;

    @BeforeEach
    void init() {
        ted = new Person(TED);
        dolores = new Person(DOLORES);
        timmy = new Person(TIMMY);
        berta = new Person(BERTA);
        gameQueue = new GameQueue(List.of(ted, dolores, timmy, berta));
    }

    @Test
    void queueShouldWorkProperly() {
        assertEquals(ted, gameQueue.getActivePerson());
        gameQueue.next();
        assertEquals(dolores, gameQueue.getActivePerson());
        gameQueue.next();
        assertEquals(timmy, gameQueue.getActivePerson());
        gameQueue.next();
        assertEquals(berta, gameQueue.getActivePerson());
        gameQueue.next();
        assertEquals(ted, gameQueue.getActivePerson());
    }
}

package pl.bdaf.person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.bdaf.person.GameEngine.END_OF_THE_GAME;
import static pl.bdaf.person.PersonStatistic.*;

public class GameTest {

    private Person ted;
    private Person dolores;
    private Person timmy;
    private Person berta;
    private GameQueue gameQueue;
    private GameEngine engine;

    @BeforeEach
    void init() {
        ted = new Person(TED);
        dolores = new Person(DOLORES);
        timmy = new Person(TIMMY);
        berta = new Person(BERTA);
        List<Person> list = new LinkedList<>(List.of(ted, dolores, timmy, berta));
        gameQueue = new GameQueue(list);
        engine = new GameEngine(list, new Backpack());
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

    @Test
    void afterAllQueueEngineShouldMakeDayHigher() {
        assertEquals(1, engine.getCurrentDay());
        assertEquals(ted, engine.getActivePerson());
        engine.passToNextPerson();
        assertEquals(dolores, engine.getActivePerson());
        engine.passToNextPerson();
        assertEquals(timmy, engine.getActivePerson());
        engine.passToNextPerson();
        assertEquals(1, engine.getCurrentDay());
        assertEquals(berta, engine.getActivePerson());
        engine.passToNextPerson();
        assertEquals(2, engine.getCurrentDay());
        assertEquals(ted, engine.getActivePerson());
    }

    @Test
    void QueueInEngineShouldBeSmallerAfterSomeDeaths() {
        assertEquals(1, engine.getCurrentDay());
        assertEquals(ted, engine.getActivePerson());
        engine.passToNextPerson();
        ted.setState(new State.Dead());
        assertEquals(dolores, engine.getActivePerson());
        engine.passToNextPerson();
        assertEquals(timmy, engine.getActivePerson());
        engine.passToNextPerson();
        assertEquals(berta, engine.getActivePerson());
        engine.passToNextPerson();
        assertEquals(2, engine.getCurrentDay());
        assertEquals(dolores, engine.getActivePerson());
        engine.drink();
        engine.eat();
        engine.passToNextPerson();
        assertEquals(timmy, engine.getActivePerson());
        timmy.setState(new State.Dead());
        engine.passToNextPerson();
        assertEquals(berta, engine.getActivePerson());
        engine.drink();
        engine.eat();
        engine.passToNextPerson();
        assertEquals(3, engine.getCurrentDay());
        assertEquals(dolores, engine.getActivePerson());
        engine.passToNextPerson();
        assertEquals(berta, engine.getActivePerson());
        engine.passToNextPerson();
        assertEquals(4, engine.getCurrentDay());
    }

    @Test
    void gameEngineShouldSetWhenIsEndOfGame() {
        assertEquals(1, engine.getCurrentDay());
        assertEquals(ted, engine.getActivePerson());
        engine.passToNextPerson();
        ted.setState(new State.Dead());
        dolores.setState(new State.Dead());
        berta.setState(new State.Dead());
        assertEquals(dolores, engine.getActivePerson());
        engine.passToNextPerson();
        assertEquals(timmy, engine.getActivePerson());
        engine.passToNextPerson();
        assertEquals(berta, engine.getActivePerson());
        engine.passToNextPerson();
        assertEquals(2, engine.getCurrentDay());
        assertEquals(timmy, engine.getActivePerson());
        timmy.setState(new State.Dead());
        engine.passToNextPerson();
        assertEquals(3, engine.getCurrentDay());
        assertTrue(engine.getDailyDescribe().contains(END_OF_THE_GAME));
    }
}

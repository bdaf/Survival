package pl.bdaf.gui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import pl.bdaf.person.GameEngine;
import pl.bdaf.person.Person;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.List;

import static com.googlecode.lanterna.TextColor.ANSI.*;
import static pl.bdaf.person.Backpack.TOMATO_SOUP;
import static pl.bdaf.person.Backpack.WATER_BOTTLE;
import static pl.bdaf.person.GameEngine.*;
import static pl.bdaf.person.PersonStatistic.*;

public class GameController implements PropertyChangeListener {

    private final GameEngine engine;
    private final MultiWindowTextGUI gui;
    private Panel containerPanel;
    private Panel mainPanel;
    private Panel rightPanel;
    private Panel rightMidPanel;
    private Panel midPanel;
    private Panel leftPanel;
    private Panel midLeftPanel;
    private Panel bottomLeftPanel;
    private BasicWindow window;

    public GameController(MultiWindowTextGUI aGui) {
        engine = new GameEngine(List.of(new Person(TED), new Person(DOLORES), new Person(TIMMY), new Person(BERTA)));
        gui = aGui;
        init();
    }

    private void init() {
        engine.addObserver(WATER_BOTTLE + EATEN, this);
        engine.addObserver(TOMATO_SOUP + EATEN, this);
        engine.addObserver(RETURN_FROM_EXPEDITION, this);
        engine.addObserver(SEND_MESSAGE, this);
        engine.addObserver(END_OF_THE_GAME, this);
        engine.addObserver(PERSON_PASSES, this);
        engine.addObserver(UPDATE_DIARY, this);
        makingPanels();
    }

    private void makingPanels() {
        window = new BasicWindow();
        window.setTitle("Survival");
        containerPanel = new Panel();
        mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        preparingPanels();
        gui.addWindowAndWait(window);
    }

    private void preparingPanels() {
        preparingLeftPanel();
        mainPanel.addComponent(leftPanel);
        preparingMidPanel();
        mainPanel.addComponent(midPanel.withBorder(Borders.singleLine("Daily diary")));
        preparingRightPanel();
        mainPanel.addComponent(rightPanel.withBorder(Borders.singleLine("Current person")));
        containerPanel.addComponent(mainPanel.withBorder(Borders.singleLine("Day " + engine.getCurrentDay() + " - " + engine.getActivePerson().getName())));
        window.setComponent(containerPanel);
        window.setHints(Collections.singletonList(Window.Hint.CENTERED));
    }

    private void preparingRightPanel() {
        rightPanel = new Panel();
        rightPanel.setPreferredSize(new TerminalSize(35, 15));
        updateAnimatedPerson(engine.getActivePerson().getName());
    }

    private void updateAnimatedPerson(String aName) {
        if (rightMidPanel == null) rightMidPanel = new Panel();
        rightMidPanel.removeAllComponents();
        rightMidPanel.addComponent(getAnimation(engine.getActivePerson().getName()));
        rightMidPanel.addTo(rightPanel);
    }

    private void setActionsTo(ActionListBox aActionListBox) {
        aActionListBox.addItem("Drink", () -> engine.drink());
        aActionListBox.addItem("Eat", () -> engine.eat());
        aActionListBox.addItem("Expedition!", () -> engine.goForExpeditionAndPass());
        aActionListBox.addItem("Next person", () -> engine.pass());
        aActionListBox.addItem("Next day", () -> engine.passWholeDay());
        aActionListBox.addItem("Clear diary", () -> engine.clearDiaryDescribe());
        aActionListBox.addItem("Back to menu", () -> goBackToMenu());
    }

    private void setDiaryDescribe(String aDailyDescribe) {
        midPanel.removeAllComponents();
        new Label(aDailyDescribe).addTo(midPanel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent aEvent) {
        if (aEvent.getPropertyName().equals(END_OF_THE_GAME)) {
            MessageDialog.showMessageDialog(gui, "End Of The Game!", "Your score is " + engine.getCurrentDay() + " days!");
            goBackToMenu();
        } else if (aEvent.getPropertyName().equals(PERSON_PASSES)) {
            containerPanel.removeAllComponents();
            containerPanel.addComponent(mainPanel.withBorder(Borders.singleLine("Day " + engine.getCurrentDay() + " - " + engine.getActivePerson().getName())));
            updateCurrentPersonAndDay();
            updateAnimatedPerson(engine.getActivePerson().getName());
        } else if (aEvent.getPropertyName().equals(UPDATE_DIARY)) {
            setDiaryDescribe(engine.getDailyDescribe());
        } else if (aEvent.getPropertyName().contains(EATEN) || aEvent.getPropertyName().equals(RETURN_FROM_EXPEDITION)) {
            updateAmountsOfSupplies();
        } else if (aEvent.getPropertyName().equals(SEND_MESSAGE)) {
            MessageDialog.showMessageDialog(gui, aEvent.getOldValue().toString(), aEvent.getNewValue().toString());
        }
    }

    private void goBackToMenu() {
        window.close();
    }

    private void updateAmountsOfSupplies() {
        if (midLeftPanel == null) midLeftPanel = new Panel();
        midLeftPanel.removeAllComponents();
        midLeftPanel.addComponent(new Label("Water: " + engine.getAmountOf(WATER_BOTTLE)));
        midLeftPanel.addComponent(new Label("Soups: " + engine.getAmountOf(TOMATO_SOUP)));
    }

    private void preparingLeftPanel() {
        leftPanel = new Panel();
        leftPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        ActionListBox actionListBox = new ActionListBox(new TerminalSize(14, 7));
        setActionsTo(actionListBox);
        Panel topLeftPanel = new Panel().addComponent(actionListBox);
        updateAmountsOfSupplies();
        updateCurrentPersonAndDay();
        midLeftPanel.setPreferredSize(new TerminalSize(15, 2));
        bottomLeftPanel.setPreferredSize(new TerminalSize(15, 2));
        leftPanel.addComponent(topLeftPanel.withBorder(Borders.singleLine("Actions")));
        leftPanel.addComponent(midLeftPanel.withBorder(Borders.singleLine("Supplies")));
        leftPanel.addComponent(bottomLeftPanel.withBorder(Borders.singleLine("Currently")));
    }

    private void updateCurrentPersonAndDay() {
        if (bottomLeftPanel == null) bottomLeftPanel = new Panel();
        bottomLeftPanel.removeAllComponents();
        bottomLeftPanel.addComponent(new Label("Person: " + engine.getActivePerson().getName()));
        bottomLeftPanel.addComponent(new Label("Day: " + engine.getCurrentDay()));

    }

    private void preparingMidPanel() {
        midPanel = new Panel();
        new Label(engine.getDailyDescribe()).addTo(midPanel);
        midPanel.setPreferredSize(new TerminalSize(50, 15));
    }

    private Label getAnimation(String aName) {
        if (aName.equalsIgnoreCase(TED.getName())) {
            return getAnimatedLabel(aName, WHITE_BRIGHT," \n" +
                    "          TED\n\n" +
                    "         /:\"\"|\n" +
                    "        |: -6|_ \n" +
                    "        C     _)  \n" +
                    "         \\ ._|\n" +
                    "          ) /\n" +
                    "         /`\\\\\n" +
                    "        || |Y|\n" +
                    "        || |#|\n" +
                    "        || |#|\n" +
                    "        || |#|");
        }
        if (aName.equalsIgnoreCase(DOLORES.getName())) {
            return getAnimatedLabel(aName,CYAN_BRIGHT,"\n" +
                    "         DOLORES\n\n" +
                    "          .@@@@@,\n" +
                    "        @@@@@@@@,\n" +
                    "         oo`@@@@@@\n" +
                    "       ( -   ?@@@@\n" +
                    "         =' @@@@\"\n" +
                    "           \\(```\n" +
                    "          //`\\    \n" +
                    "        / | ||    \n" +
                    "         \\ | ||  \n" +
                    "        / | ||  \n");
        }
        if (aName.equalsIgnoreCase(TIMMY.getName())) {
            return getAnimatedLabel(aName, GREEN_BRIGHT, "\n" +
                    "           TIMMY\n\n" +
                    "        .\"~~~~~\".\n" +
                    "         |  .:.  |\n" +
                    "      A  | /= =\\ |\n" +
                    "     |~|_|_\\ e /_|_   \n" +
                    "    |_|)___`\"`___(8\n" +
                    "        |~~~~~~~~~|\n" +
                    "       \\_________/\n" +
                    "         |/ /_\\ \\|\n" +
                    "        ()/___\\()\n");
        }
        if (aName.equalsIgnoreCase(BERTA.getName())) {
            return getAnimatedLabel(aName, BLUE_BRIGHT, "\n" +
                    "           BERTA\n\n" +
                    "         .@@@@,\n" +
                    "          -0`@@@,\n" +
                    "          =  `@@@\n" +
                    "            )_/`@'\n" +
                    "           / || @\n" +
                    "           | || @\n" +
                    "           /~|| \"`\n" +
                    "          /__W_\\\n" +
                    "            |||\n" +
                    "           _|||\n" +
                    "          ((___)\n");
        }
        return null;
    }

    private Label getAnimatedLabel(String aName, ANSI aForegroundColor, String aExtraFrameString) {
        AnimatedLabel person = (AnimatedLabel) new AnimatedLabel(getDefaultFrameTextOfAnimate("", "", aName)).setForegroundColor(aForegroundColor);
        makeFramesOfAnimate(person, aName);
        person.addFrame(aExtraFrameString);
        person.startAnimation(300);
        return person;
    }

    private void makeFramesOfAnimate(AnimatedLabel label, String aName) {
        animateLabelInLoop(label, 1, " ", "", aName);
        animateLabelInLoop(label, 5, "", "", aName);
        animateLabelInLoop(label, 1, "", " ", aName);
        animateLabelInLoop(label, 5, "", "", aName);
        animateLabelInLoop(label, 1, " ", " ", aName);
        animateLabelInLoop(label, 5, "", "", aName);
    }

    private void animateLabelInLoop(AnimatedLabel aLabel, int aAmount, String aMoveEvenPoints, String aMoveOddPoints, String aName) {
        for (int i = 0; i < aAmount; i++) aLabel.addFrame(getDefaultFrameTextOfAnimate(aMoveEvenPoints, aMoveOddPoints, aName));
    }

    private String getDefaultFrameTextOfAnimate(String aMoveEvenPoints, String aMoveOddPoints, String aName) {
        if (aName.equalsIgnoreCase(TED.getName()))
            return " \n" +
                    aMoveEvenPoints + "          TED\n\n" +
                    aMoveOddPoints + "         /:\"\"|\n" +
                    aMoveEvenPoints + "        |: 66|_ \n" +
                    aMoveOddPoints + "        C     _)  \n" +
                    aMoveEvenPoints + "         \\ ._|\n" +
                    aMoveOddPoints + "          ) /\n" +
                    aMoveEvenPoints + "         /`\\\\\n" +
                    aMoveOddPoints + "        || |Y|\n" +
                    aMoveEvenPoints + "        || |#|\n" +
                    aMoveOddPoints + "        || |#|\n" +
                    aMoveEvenPoints + "        || |#|";
        if (aName.equalsIgnoreCase(DOLORES.getName()))
            return "\n" +
                    aMoveOddPoints + "         DOLORES\n\n" +
                    aMoveOddPoints + "          .@@@@@,\n" +
                    aMoveEvenPoints + "        @@@@@@@@,\n" +
                    aMoveOddPoints + "         aa`@@@@@@\n" +
                    aMoveEvenPoints + "        (_   ?@@@@\n" +
                    aMoveOddPoints + "         =' @@@@\"\n" +
                    aMoveEvenPoints + "           \\(```\n" +
                    aMoveOddPoints + "          //`\\    \n" +
                    aMoveEvenPoints + "        / | ||    \n" +
                    aMoveOddPoints + "         \\ | ||  \n" +
                    aMoveEvenPoints + "        / | ||  \n";
        if (aName.equalsIgnoreCase(TIMMY.getName()))
            return "\n" +
                    aMoveOddPoints + "           TIMMY\n\n" +
                    aMoveEvenPoints + "        .\"~~~~~\".\n" +
                    aMoveOddPoints + "         |  .:.  |\n" +
                    aMoveEvenPoints + "      A  | /6 6\\ |\n" +
                    aMoveOddPoints + "     |~|_|_\\ e /_|_   \n" +
                    aMoveEvenPoints + "    |_|)___`\"`___(8\n" +
                    aMoveOddPoints + "        |~~~~~~~~~|\n" +
                    aMoveEvenPoints + "       \\_________/\n" +
                    aMoveOddPoints + "         |/ /_\\ \\|\n" +
                    aMoveEvenPoints + "        ()/___\\()\n";

        if (aName.equalsIgnoreCase(BERTA.getName()))
            return "\n" +
                    aMoveOddPoints + "           BERTA\n\n" +
                    aMoveEvenPoints +"         .@@@@,\n" +
                    aMoveOddPoints + "          aa`@@@,\n" +
                    aMoveEvenPoints +"          =  `@@@\n" +
                    aMoveOddPoints + "            )_/`@'\n" +
                    aMoveEvenPoints +"           / || @\n" +
                    aMoveOddPoints + "           | || @\n" +
                    aMoveEvenPoints +"           /~|| \"`\n" +
                    aMoveOddPoints + "          /__W_\\\n" +
                    aMoveEvenPoints +"            |||\n" +
                    aMoveOddPoints + "           _|||\n" +
                    aMoveEvenPoints +"         ((___)\n";
        else return null;
    }
}

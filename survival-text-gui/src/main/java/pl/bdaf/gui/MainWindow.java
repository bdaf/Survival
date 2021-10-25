package pl.bdaf.gui;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.regex.Pattern;

public class MainWindow {
    private static MultiWindowTextGUI gui;

    public static void main(String[] args) throws IOException {

        // Setup terminal and screen layers
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();

        // Create gui and start gui
        gui = new MultiWindowTextGUI(screen);

        // Dialogs appear
        MessageDialog.showMessageDialog(gui, "Survival Game", "Incredible challenge waits for you!\nUse arrows and \"enter\" button to move through menu.");
        String nameOfUser = getNameFromTextInputDialog(gui);
        if(nameOfUser==null) nameOfUser = "Anonymous" ;
        //getMenu(screen, nameOfUser).showDialog(gui);
        gui.addWindowAndWait(getMenu(screen, nameOfUser));
    }

    private static DialogWindow getMenu(Screen screen, String aNameOfUser) {
        return new ActionListDialogBuilder()
                .setTitle("Menu")
                .setDescription(aNameOfUser+", choose action!")
                .setCanCancel(false)
                .addAction("Play Game", () -> {
                    new GameController(gui);
                    getMenu(screen,aNameOfUser).showDialog(gui);
                })
                .addAction("Instruction", () -> {
                    MessageDialog.showMessageDialog(gui, "Instruction", getInstructionString());
                    getMenu(screen,aNameOfUser).showDialog(gui);
                })
                .addAction("Quit game", () -> closeScreen(screen))
                .build();
    }

    private static String getInstructionString() {
        return "In game you have 4 people (Ted, Dolores, Timmy and Berta)\n" +
                "in shelter after atomic bomb explosion outside it. Your\n" +
                "mission is to send them on expedition, feed and water them\n" +
                "in a way that will make them alive as long as possible.\n" +
                "Diary will be your source of information. Have many days!";
    }

    private static String getNameFromTextInputDialog(MultiWindowTextGUI gui) {
        return new TextInputDialogBuilder()
                .setTitle("Hello")
                .setDescription("Welcome in Survival Game.\nType your name and let's go!")
                .setValidationPattern(Pattern.compile("[a-zA-Z0-9_]{2,10}+"), "Name shall contain from 2 to 10 letters and numbers!")
                .build()
                .showDialog(gui);
    }

    private static void closeScreen(Screen screen) {
        try {
            screen.stopScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

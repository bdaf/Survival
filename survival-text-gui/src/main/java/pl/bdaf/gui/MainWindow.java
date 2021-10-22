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
    public static void main(String[] args) throws IOException {

        // Setup terminal and screen layers
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();

        // Create gui and start gui
        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen);
        MessageDialog.showMessageDialog(gui, "Survival Game", "Incredible challenge waits for you!");

        String nameOfUser = getNameFromTextInputDialog(gui);

        while (true){
            getMenu(screen, nameOfUser).showDialog(gui);
        }
    }

    private static String getNameFromTextInputDialog(MultiWindowTextGUI gui) {
        return new TextInputDialogBuilder()
                .setTitle("Hello")
                .setDescription("Welcome in Survival Game.\nType your name and let's go!")
                .setValidationPattern(Pattern.compile("[a-zA-Z0-9_]{2,10}+"), "Name shall contain from 2 to 10 letters and numbers!")
                .build()
                .showDialog(gui);
    }

    private static DialogWindow getMenu(Screen screen, String aNameOfUser) {
        return new ActionListDialogBuilder()
              .setTitle("Menu")
              .setDescription(aNameOfUser+", choose action!")
                .setCanCancel(false)
              .addAction("Play Game", () -> {
                  // Do 1st thing...
              })
              .addAction("Instruction", () -> {
                  // Do 2nd thing...
              })
              .addAction("Quit game", () -> closeScreen(screen))
              .build();
    }

    private static void closeScreen(Screen screen) {
        try {
            screen.stopScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

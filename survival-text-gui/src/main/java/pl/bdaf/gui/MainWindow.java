package pl.bdaf.gui;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;
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
        new TextInputDialogBuilder()
                .setTitle("Hello")
                .setDescription("Welcome in Survival Game.\nType your name and let's go!")
                .setValidationPattern(Pattern.compile("[a-zA-Z0-9_]+"), "You have to type name, but without special characters!")
                .build()
                .showDialog(gui);
        screen.stopScreen();
    }
}

package pl.bdaf.gui;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

public class Test {
    public static void main(String[] args) {
        try {
            DefaultTerminalFactory factory = new DefaultTerminalFactory();
            Terminal terminal = factory.createTerminal();
            Screen screen = new TerminalScreen(terminal);
            screen.startScreen();
            Panel panel = new Panel();
            panel.setLayoutManager(new GridLayout(3));
            panel.addComponent(new Label("Forename"));
            panel.addComponent(new TextBox());
            panel.addComponent(new Label("Surname"));
            panel.addComponent(new TextBox());
            panel.addComponent(new EmptySpace(new TerminalSize(0, 0)));
            final Window window = new BasicWindow();
            panel.addComponent(new Button("Submit", () -> window.close()));
            window.setComponent(panel);
            MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
            gui.addWindowAndWait(window);
            screen.stopScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

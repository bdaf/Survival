package pl.bdaf;

import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class LaunchApp {
    public static void main(String[] args) throws InterruptedException {

        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();

        Terminal terminal;
        try {
            terminal = defaultTerminalFactory.createTerminal();
            terminal.putCharacter('H');
            terminal.putCharacter('e');
            terminal.putCharacter('l');
            terminal.putCharacter('l');
            terminal.putCharacter('o');

            terminal.putCharacter('W');
            terminal.putCharacter('o');
            terminal.putCharacter('r');
            terminal.putCharacter('l');
            terminal.putCharacter('d');
            terminal.putCharacter('!');
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}

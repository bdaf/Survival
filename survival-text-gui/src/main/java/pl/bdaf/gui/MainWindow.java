package pl.bdaf.gui;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.*;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import javazoom.jl.decoder.JavaLayerException;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.regex.Pattern;

public class MainWindow {
    private static MultiWindowTextGUI gui;
    private static String nameOfUser;
    private static TerminalScreen screen;
    private static MusicPlayer musicPlayer;

    public static void main(String[] args) throws IOException {
        //Music
        playMusic("st.mp3");

        // Setup terminal and screen layers
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        screen = new TerminalScreen(terminal);
        screen.startScreen();

        // Create gui and start gui
        gui = new MultiWindowTextGUI(screen);

        // Dialogs appear
        MessageDialog.showMessageDialog(gui, "Survival Game", "Incredible challenge waits for you!\nUse arrows and \"enter\" button to move through menu.");
        nameOfUser = getNameFromTextInputDialog(gui);
        if (nameOfUser == null) nameOfUser = "Anonymous";
        //
        //
        BasicWindow window = new BasicWindow();
        Panel mainPanel = new Panel();
        Panel panel1 = new Panel();
        Panel panel2 = new Panel();

        window.setComponent(mainPanel);
        window.setHints(Collections.singletonList(Window.Hint.CENTERED));

        mainPanel.addComponent(panel1);
        mainPanel.addComponent(panel2);
        mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        panel1.addComponent(new Label(getAsciArtString()));
        panel2.addComponent(new Button("Play Game", () -> new GameController(gui)));
        panel2.addComponent(new Button("Instruction", () -> MessageDialog.showMessageDialog(gui, "Instruction", getInstructionString())));
        panel2.addComponent(new Button("Quit game", () -> closeScreen()));

        gui.addWindowAndWait(window);
    }


    static DialogWindow getMenu() {
        return new ActionListDialogBuilder()
                .setTitle("Menu")
                .setDescription(nameOfUser + ", choose action!")
                .setCanCancel(false)
                .addAction(getAsciArtString(), () -> new GameController(gui))
                .addAction("Play Game", () -> {
                    new GameController(gui);
                    getMenu().showDialog(gui);
                })
                .addAction("Instruction", () -> {
                    MessageDialog.showMessageDialog(gui, "Instruction", getInstructionString());
                    getMenu().showDialog(gui);
                })
                .addAction("Quit game", () -> closeScreen())
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

    static TerminalScreen getScreen() {
        return screen;
    }

    private static void closeScreen() {
        try {
            screen.close();
            musicPlayer.stop();
        } catch (IOException aE) {
            aE.printStackTrace();
        }
    }

    private static String getAsciArtString() {
        return "\r" +
                "  _________                  .__              .__   \n" +
                " /   _____/__ ____________  _|__|__  _______  |  |  \n" +
                " \\_____  \\|  |  \\_  __ \\  \\/ /  \\  \\/ /\\__  \\ |  |  \n" +
                " /        \\  |  /|  | \\/\\   /|  |\\   /  / __ \\|  |__\n" +
                "/_______  /____/ |__|    \\_/ |__| \\_/  (____  /____/\n" +
                "        \\/                                  \\/      ";
    }

    private static void playMusic(String aNameOfMusic) {
        try {
            musicPlayer = new MusicPlayer();
            musicPlayer.setRepeat(true);
            musicPlayer.play("/mp3/"+ aNameOfMusic);
        } catch (JavaLayerException aE) {
            System.out.println(aE);
        } catch (IOException aE) {
            System.out.println(aE);
        } catch (URISyntaxException aE) {
            System.out.println(aE);
        }
    }
}

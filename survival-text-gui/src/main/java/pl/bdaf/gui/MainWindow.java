package pl.bdaf.gui;

import com.googlecode.lanterna.TerminalSize;
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
    private static AnimatedLabel survivalAnimatedLabel;

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

        BasicWindow window = new BasicWindow();
        window.setHints(Collections.singletonList(Window.Hint.CENTERED));
        Panel mainPanel = new Panel();
        Panel panelForSurvivalLabel = new Panel();
        Panel panelForMenu = new Panel();
        Panel menuPanel = new Panel();

        window.setComponent(mainPanel);
        window.setTitle("Hello "+nameOfUser);
        mainPanel.addComponent(panelForSurvivalLabel);
        mainPanel.addComponent(panelForMenu);
        mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        survivalAnimatedLabel = new AnimatedLabel(getAsciArtString("",""));
        survivalAnimatedLabel.addFrame(getAsciArtString(" ",""));
        survivalAnimatedLabel.addFrame(getAsciArtString("",""));
        survivalAnimatedLabel.addFrame(getAsciArtString(""," "));
        survivalAnimatedLabel.startAnimation(100);

        panelForSurvivalLabel.addComponent(survivalAnimatedLabel);
        menuPanel.addComponent(new Button("Play Game", () -> new GameController(gui)));
        menuPanel.addComponent(new Button("Instruction", () -> MessageDialog.showMessageDialog(gui, "Instruction", getInstructionString())));
        menuPanel.addComponent(new Button("Quit game", () -> closeScreen()));

        panelForMenu.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));
        panelForMenu.addComponent(new EmptySpace(new TerminalSize(19,0)));
        panelForMenu.addComponent(menuPanel);
        panelForMenu.addComponent(new EmptySpace(new TerminalSize(19,0)));

        gui.addWindowAndWait(window);
    }

    private static String getInstructionString() {
        return "  _    _  ______          __   _______ ____      _____  _           __     __\n" +
                " | |  | |/ __ \\ \\        / /  |__   __/ __ \\    |  __ \\| |        /\\\\ \\   / /\n" +
                " | |__| | |  | \\ \\  /\\  / /      | | | |  | |   | |__) | |       /  \\\\ \\_/ / \n" +
                " |  __  | |  | |\\ \\/  \\/ /       | | | |  | |   |  ___/| |      / /\\ \\\\   /  \n" +
                " | |  | | |__| | \\  /\\  /        | | | |__| |   | |    | |____ / ____ \\| |   \n" +
                " |_|  |_|\\____/   \\/  \\/         |_|  \\____/    |_|    |______/_/    \\_\\_|   \n" +
                "                                                                           \n\n" +
                "In game you have 4 people (Ted, Dolores, Timmy and Berta)\n" +
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

    private static void closeScreen() {
        try {
            survivalAnimatedLabel.stopAnimation();
            musicPlayer.stop();
            screen.close();
        } catch (IOException aE) {
            aE.printStackTrace();
        }
    }

    private static String getAsciArtString(String aAddOddRow, String aAddToEvenRow) {
        return "\r" +
                aAddOddRow + "  _________                  .__              .__   \n" +
                aAddToEvenRow + " /   _____/__ ____________  _|__|__  _______  |  |  \n" +
                aAddOddRow + " \\_____  \\|  |  \\_  __ \\  \\/ /  \\  \\/ /\\__  \\ |  |  \n" +
                aAddToEvenRow + " /        \\  |  /|  | \\/\\   /|  |\\   /  / __ \\|  |__\n" +
                aAddOddRow + "/_______  /____/ |__|    \\_/ |__| \\_/  (____  /____/\n" +
                aAddToEvenRow + "        \\/                                  \\/      ";
    }


    static void playMusic(String aNameOfMusic) {
        try {
            MusicPlayer.play("/mp3/"+ aNameOfMusic);
        } catch (JavaLayerException aE) {
            System.out.println(aE);
        } catch (IOException aE) {
            System.out.println(aE);
        } catch (URISyntaxException aE) {
            System.out.println(aE);
        }
    }
}

package pl.bdaf.gui;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.AnimatedLabel;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;

import static com.googlecode.lanterna.TextColor.ANSI.*;
import static pl.bdaf.person.PersonStatistic.*;

public class AnimateGenerator {

    public static Label getPersonAnimation(String aName) {
        if (aName.equalsIgnoreCase(TED.getName())) {
            return getAnimatedLabel(aName, WHITE_BRIGHT, " \n" +
                    "           TED\n\n" +
                    "          /:\"\"|\n" +
                    "         |: -6|_ \n" +
                    "         C     _)  \n" +
                    "          \\ ._|\n" +
                    "           ) /\n" +
                    "          /`\\\\\n" +
                    "         || |Y|\n" +
                    "         || |#|\n" +
                    "         || |#|\n" +
                    "         || |#|");
        }
        if (aName.equalsIgnoreCase(DOLORES.getName())) {
            return getAnimatedLabel(aName, CYAN_BRIGHT, "\n" +
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
                    "          TIMMY\n\n" +
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
                    "          BERTA\n\n" +
                    "        .@@@@,\n" +
                    "         -0`@@@,\n" +
                    "         =  `@@@\n" +
                    "           )_/`@'\n" +
                    "          / || @\n" +
                    "          | || @\n" +
                    "          /~|| \"`\n" +
                    "         /__W_\\\n" +
                    "           |||\n" +
                    "          _|||\n" +
                    "         ((___)\n");
        }
        return null;
    }

    private static Label getAnimatedLabel(String aName, ANSI aForegroundColor, String aExtraFrameString) {
        AnimatedLabel person = (AnimatedLabel) new AnimatedLabel(getDefaultFrameTextOfAnimate("", "", aName)).setForegroundColor(aForegroundColor);
        makeFramesOfAnimate(person, aName);
        person.addFrame(aExtraFrameString);
        person.startAnimation(300);
        return person;
    }

    private static void makeFramesOfAnimate(AnimatedLabel label, String aName) {
        animateLabelInLoop(label, 1, " ", "", aName);
        animateLabelInLoop(label, 5, "", "", aName);
        animateLabelInLoop(label, 1, "", " ", aName);
        animateLabelInLoop(label, 5, "", "", aName);
        animateLabelInLoop(label, 1, " ", " ", aName);
        animateLabelInLoop(label, 5, "", "", aName);
    }

    private static void animateLabelInLoop(AnimatedLabel aLabel, int aAmount, String aMoveEvenPoints, String aMoveOddPoints, String aName) {
        for (int i = 0; i < aAmount; i++)
            aLabel.addFrame(getDefaultFrameTextOfAnimate(aMoveEvenPoints, aMoveOddPoints, aName));
    }

    private static String getDefaultFrameTextOfAnimate(String aMoveEvenPoints, String aMoveOddPoints, String aName) {
        if (aName.equalsIgnoreCase(TED.getName()))
            return " \n" +
                    aMoveEvenPoints + "           TED\n\n" +
                    aMoveOddPoints + "          /:\"\"|\n" +
                    aMoveEvenPoints + "         |: 66|_ \n" +
                    aMoveOddPoints + "         C     _)  \n" +
                    aMoveEvenPoints + "          \\ ._|\n" +
                    aMoveOddPoints + "           ) /\n" +
                    aMoveEvenPoints + "          /`\\\\\n" +
                    aMoveOddPoints + "         || |Y|\n" +
                    aMoveEvenPoints + "         || |#|\n" +
                    aMoveOddPoints + "         || |#|\n" +
                    aMoveEvenPoints + "         || |#|";
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
                    aMoveOddPoints + "          BERTA\n\n" +
                    aMoveEvenPoints + "        .@@@@,\n" +
                    aMoveOddPoints + "         aa`@@@,\n" +
                    aMoveEvenPoints + "         =  `@@@\n" +
                    aMoveOddPoints + "           )_/`@'\n" +
                    aMoveEvenPoints + "          / || @\n" +
                    aMoveOddPoints + "          | || @\n" +
                    aMoveEvenPoints + "          /~|| \"`\n" +
                    aMoveOddPoints + "         /__W_\\\n" +
                    aMoveEvenPoints + "           |||\n" +
                    aMoveOddPoints + "          _|||\n" +
                    aMoveEvenPoints + "        ((___)\n";
        else return null;
    }

    public static Label getEndOfGameLabel(MultiWindowTextGUI aGui, int aCurrentDay) {
        MessageDialog.showMessageDialog(aGui, "End Of The Game!", "(✖╭╮✖) (✖╭╮✖) (✖╭╮✖) (✖╭╮✖) (✖╭╮✖) (✖╭╮✖) (✖╭╮✖) (✖╭╮✖) (✖╭╮✖)\n\n" +
                "(✖╭╮✖) (✖╭╮✖)              _.-'''''-._           (✖╭╮✖) (✖╭╮✖) \n " +
                "                        .'   _   _   '.              \n" +
                "(✖╭╮✖) (✖╭╮✖)           /   (*)   (*)   \\        (✖╭╮✖) (✖╭╮✖) \n" +
                "                       |                |            \n" +
                "(✖╭╮✖) (✖╭╮✖)          |  \\           / |        (✖╭╮✖) (✖╭╮✖) \n" +
                "                        \\  '.       .'  /            \n" +
                "(✖╭╮✖) (✖╭╮✖)            '.  `'---'`  .'         (✖╭╮✖) (✖╭╮✖) \n" +
                "                           '-._____.-'                 \n" +
                "(✖╭╮✖) (✖╭╮✖)               Mr. Death            (✖╭╮✖) (✖╭╮✖) \n" +
                "\n(✖╭╮✖) (✖╭╮✖)        Your score is " + aCurrentDay + " days!       (✖╭╮✖) (✖╭╮✖)" +
                "\n\n(✖╭╮✖) (✖╭╮✖) (✖╭╮✖) (✖╭╮✖) (✖╭╮✖) (✖╭╮✖) (✖╭╮✖) (✖╭╮✖) (✖╭╮✖)");
        return null;
    }

    public static TextColor getColorAccordingToNumber(int aAmount) {
        if(aAmount < 5) return RED;
        if(aAmount < 10) return YELLOW_BRIGHT;
        return GREEN;
    }

    public static TextColor getColorAccordingPersonName(String aName) {
        if(aName.equalsIgnoreCase(TED.getName())) return WHITE_BRIGHT;
        if(aName.equalsIgnoreCase(DOLORES.getName())) return CYAN_BRIGHT;
        if(aName.equalsIgnoreCase(TIMMY.getName())) return GREEN_BRIGHT;
        if(aName.equalsIgnoreCase(BERTA.getName())) return BLUE_BRIGHT;
        return null;
    }

    public static TextColor getColorAccordingToDay(int aDay) {
        if(aDay % 2 == 0) return BLUE_BRIGHT;
        return WHITE_BRIGHT;
    }
    public static String getLogoString() {
        return "              ██████  █    ██  ██▀███   ██▒   █▓ ██▓ ██▒   █▓ ▄▄▄       ██▓    \n" +
                "           ▒██    ▒  ██  ▓██▒▓██ ▒ ██▒▓██░   █▒▓██▒▓██░   █▒▒████▄    ▓██▒    \n" +
                "           ░ ▓██▄   ▓██  ▒██░▓██ ░▄█ ▒ ▓██  █▒░▒██▒ ▓██  █▒░▒██  ▀█▄  ▒██░    \n" +
                "             ▒   ██▒▓▓█  ░██░▒██▀▀█▄    ▒██ █░░░██░  ▒██ █░░░██▄▄▄▄██ ▒██░    \n" +
                "           ▒██████▒▒▒▒█████▓ ░██▓ ▒██▒   ▒▀█░  ░██░   ▒▀█░   ▓█   ▓██▒░██████▒\n" +
                "           ▒ ▒▓▒ ▒ ░░▒▓▒ ▒ ▒ ░ ▒▓ ░▒▓░   ░ ▐░  ░▓     ░ ▐░   ▒▒   ▓▒█░░ ▒░▓  ░\n" +
                "           ░ ░▒  ░ ░░░▒░ ░ ░   ░▒ ░ ▒░   ░ ░░   ▒ ░   ░ ░░    ▒   ▒▒ ░░ ░ ▒  ░\n" +
                "           ░  ░  ░   ░░░ ░ ░   ░░   ░      ░░   ▒ ░     ░░    ░   ▒     ░ ░   \n" +
                "                 ░     ░        ░           ░   ░        ░        ░  ░    ░  ░";
    }
}

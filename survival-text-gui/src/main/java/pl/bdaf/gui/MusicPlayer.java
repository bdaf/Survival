package pl.bdaf.gui;


import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MusicPlayer
{
    private static InputStream is;

    private static Player player;
    private static Thread thread;
    private static boolean repeat = true;

    public static void play( String musicFilePath ) throws JavaLayerException, IOException, URISyntaxException {
        stop();
        is = MusicPlayer.class.getResourceAsStream( musicFilePath );
        player = new Player( is );

        thread = new Thread(() -> {
            try {
                player.play();
                if( player.isComplete() && repeat ) {
                    play( musicFilePath );
                }
            }
            catch ( JavaLayerException | IOException ex) {
                System.err.println("::: there was an error to play " + musicFilePath );
            } catch (URISyntaxException ex) {
                Logger.getLogger(MusicPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        thread.start();
    }

    static void stop() {
        if( null != player) {
            player.close();
            thread.stop();
        }
    }
}


package pl.bdaf.gui;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class MusicPlayer
{

    private InputStream is;

    private Player player;
    private boolean repeat;
    private Thread thread;

    public void play( String musicFilePath ) throws JavaLayerException, IOException, URISyntaxException
    {
        is = this.getClass().getResourceAsStream( musicFilePath );
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

    public void stop() {
        if( null != player) {
            player.close();
            thread.stop();
        }
    }

    public Thread getThread() {
        return thread;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

}


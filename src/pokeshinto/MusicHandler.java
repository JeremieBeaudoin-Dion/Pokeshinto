package pokeshinto;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Plays sound files
 *
 * @author Jérémie Beaudoin-Dion
 */
class MusicHandler {

    private AudioInputStream mainMenu;

    private Clip currentMusic;

    MusicHandler() throws IOException, UnsupportedAudioFileException {
        loadAllSounds();

        // how to rewind
        // clip.setFramePosition(0); // rewind to the beginning
    }

    private void loadAllSounds() throws IOException, UnsupportedAudioFileException {
        mainMenu = AudioSystem.getAudioInputStream(new File("MainTheme.wav"));
    }

    void play() {
        try {
            currentMusic = AudioSystem.getClip();
            currentMusic.open(mainMenu);
            currentMusic.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    void stop() {
        if (currentMusic.isRunning()) {
            currentMusic.stop();
        }
    }


}

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
    private AudioInputStream fishingGame;
    private AudioInputStream startGame;

    private Clip mainMenuClip;
    private Clip fishingGameClip;
    private Clip startGameClip;

    MusicHandler() throws IOException, UnsupportedAudioFileException {
        loadAllSounds();
        openAllClips();
    }

    private void loadAllSounds() throws IOException, UnsupportedAudioFileException {
        mainMenu = AudioSystem.getAudioInputStream(new File("Resources/Music/MainTheme.wav"));
        fishingGame = AudioSystem.getAudioInputStream(new File("Resources/Music/GoneFishing.wav"));
        startGame = AudioSystem.getAudioInputStream(new File("Resources/Music/StartOfAnAdventure.wav"));
    }

    private void openAllClips() {
        try {

            mainMenuClip = AudioSystem.getClip();
            fishingGameClip = AudioSystem.getClip();
            startGameClip = AudioSystem.getClip();

            mainMenuClip.open(mainMenu);
            fishingGameClip.open(fishingGame);
            startGameClip.open(startGame);

        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }

    }

    void play() {
        stop();

        // mainMenuClip.setFramePosition(0);
        mainMenuClip.loop(Clip.LOOP_CONTINUOUSLY);

    }

    void play(String id) {
        stop();

        if (id.equals("Fishing")) {
            // fishingGameClip.setFramePosition(0);
            fishingGameClip.loop(Clip.LOOP_CONTINUOUSLY);

        } else if (id.equals("World")) {
            startGameClip.setFramePosition(0);
            startGameClip.loop(1);
        }
    }

    void stop() {
        if (mainMenuClip.isRunning()) {
            mainMenuClip.stop();
        }

        if (fishingGameClip.isRunning()) {
            fishingGameClip.stop();
        }

        if (startGameClip.isRunning()) {
            startGameClip.stop();
        }
    }

}

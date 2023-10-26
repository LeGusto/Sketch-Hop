import java.io.InputStream;
import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.SourceDataLine;
import java.io.IOException;

public class audioPlayer implements LineListener {


    boolean isPlaybackCompleted;
    
    @Override
    public void update(LineEvent event) {
        if (LineEvent.Type.START == event.getType()) {
            System.out.println("Playback started.");
        } else if (LineEvent.Type.STOP == event.getType()) {
            isPlaybackCompleted = true;
            System.out.println("Playback completed.");
        }
    }

    File musicPath = new File("Breaking-Bob.wav");
    AudioInputStream audioStream = AudioSystem.getAudioInputStream(musicPath);

    AudioFormat audioFormat = audioStream.getFormat();
    DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);

    Clip audioClip = (Clip) AudioSystem.getLine(info);
    //audioClip.addLineListener(this);
    //audioClip.open(audioStream);
    //audioClip.start();*/

}

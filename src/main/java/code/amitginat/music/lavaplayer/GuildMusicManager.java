package code.amitginat.music.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

public class GuildMusicManager{
    public final AudioPlayer audioPlayer;
    public final TrackScheduler trackScheduler;

    private final AudioPlayerSendHandler sendHandler;
    public GuildMusicManager(AudioPlayerManager manager){
        audioPlayer = manager.createPlayer();
        trackScheduler = new TrackScheduler(audioPlayer);
        audioPlayer.addListener(this.trackScheduler);
        sendHandler = new AudioPlayerSendHandler(audioPlayer);
    }

    public AudioPlayerSendHandler getSendHandler() {
        return sendHandler;
    }
}

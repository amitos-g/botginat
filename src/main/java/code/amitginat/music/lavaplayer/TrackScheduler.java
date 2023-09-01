package code.amitginat.music.lavaplayer;

import code.amitginat.events.ReadEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackScheduler extends AudioEventAdapter {

    public final AudioPlayer player;
    public AudioTrack PlayingTrack;
    public final BlockingQueue<AudioTrack> queue;

    public TrackScheduler(AudioPlayer player){
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
    }

    public void queue(AudioTrack track) {
        if(!this.player.startTrack(track, true)){
            queue.offer(track);
        }
    }

    public void nextTrack() {
        AudioTrack currentTrack = this.queue.poll();
        this.player.startTrack(currentTrack, false);
        try {
            ReadEvent.thisEvent.getChannel().sendMessageFormat("Playing %s by %s", currentTrack.getInfo().title, currentTrack.getInfo().author).queue();
        }
        catch (Throwable ignored){}
        PlayingTrack = currentTrack;

    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if(endReason.mayStartNext){
            nextTrack();
        }
    }
}

package code.amitginat.music.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerManager {
    private static PlayerManager INSTANCE;
    private final Map<Long, GuildMusicManager> musicManagers;
    private final AudioPlayerManager audioPlayerManager;

    public static boolean isPlaylist;


    public PlayerManager() {
        this.musicManagers = new HashMap<>();
        this.audioPlayerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
        AudioSourceManagers.registerLocalSource(audioPlayerManager);
    }

    public GuildMusicManager getMusicManager(Guild guild) {
        return musicManagers.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            final GuildMusicManager guildMusicManager = new GuildMusicManager(this.audioPlayerManager);

            guild.getAudioManager().setSendingHandler(guildMusicManager.getSendHandler());

            return guildMusicManager;
        });
    }

    public void loadAndPlay(MessageChannelUnion channel, String trackUrl) {
        GuildMusicManager musicManager = this.getMusicManager(channel.asTextChannel().getGuild());
        if(isPlaylist){
            this.audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
                @Override
                public void trackLoaded(AudioTrack track) {

                    musicManager.trackScheduler.queue(track);
                }

                @Override
                public void playlistLoaded(AudioPlaylist playlist) {
                    List<AudioTrack> tracks = playlist.getTracks();
                    AudioTrack track = tracks.get(0);
                        musicManager.trackScheduler.queue(track);
                    }

                @Override
                public void noMatches() {

                }

                @Override
                public void loadFailed(FriendlyException exception) {
                    channel.sendMessage("Failed To Load " + trackUrl.replace("ytsearch:", "")).queue();
                }
            });
        }
        else {
            this.audioPlayerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
                @Override
                public void trackLoaded(AudioTrack track) {

                    musicManager.trackScheduler.queue(track);
                    channel.sendMessage(String.format("Mosif La Tor Et: %s by %s",
                            track.getInfo().title,
                            track.getInfo().author)).queue();
                }

                @Override
                public void playlistLoaded(AudioPlaylist playlist) {
                    List<AudioTrack> tracks = playlist.getTracks();
                    if (isPlaylist) {
                        channel.sendMessage(String.format("Mosif La Tor: %s tracks from playlist %s",
                                tracks.size(),
                                playlist.getName())).queue();
                        for (AudioTrack track : tracks) {
                            musicManager.trackScheduler.queue(track);

                        }
                    }
                    else{
                        AudioTrack track = tracks.get(0);
                        channel.sendMessage(String.format("Mosif La Tor Et: %s by %s",
                                track.getInfo().title,
                                track.getInfo().author)).queue();
                        musicManager.trackScheduler.queue(track);
                    }
                }

                @Override
                public void noMatches() {

                }

                @Override
                public void loadFailed(FriendlyException exception) {

                }
            });
        }

    }

    public static PlayerManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }


        return INSTANCE;
    }
}
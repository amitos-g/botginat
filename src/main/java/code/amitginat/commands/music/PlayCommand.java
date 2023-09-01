package code.amitginat.commands.music;

import code.amitginat.commands.AbstractCommand;
import code.amitginat.events.ReadEvent;
import code.amitginat.music.lavaplayer.PlayerManager;
import code.amitginat.music.spotify.InitiateSpotifyPlaylistRequest;
import code.amitginat.music.spotify.InitiateSpotifyTrack;

import java.net.URI;
import java.net.URL;

public class PlayCommand extends AbstractCommand {




    @Override
    public void run() {
        try {
            if(message.contains("spotify")){
                playSpotify();
            }
            else{
                playYoutube();
            }
        }
        catch (Throwable throwable){
            channel.sendMessage("לא מצליח לנגן.").queue();
            throwable.printStackTrace();
        }

    }
    @Override
    public String prefix() {
        return "play";
    }

    @Override
    public String explain() {
        return "עובד עם יוטיוב + ספוטיפיי! מקבל לינקים וגם שמות של שירים! מקבל פלייליסטים!";
    }








    public void playYoutube()
    {
        ReadEvent.ISPLAYLIST = false;
        if (!memberVoiceState.inAudioChannel()) {
            channel.sendMessage("אתה לא בשיחה").queue();
            return;
        }
        if (!selfVoiceState.inAudioChannel()) {
            channel.sendMessage("אני לא בשיחה").queue();
            return;
        }
        String toPlay = message.replace("ginat play", "").strip();
        if (!isUrl(toPlay)) {
            toPlay = "ytsearch:" + toPlay;
        }
        PlayerManager.getInstance().loadAndPlay(channel, toPlay);

    }

    public void playSpotify()
    {
        if (!memberVoiceState.inAudioChannel()) {
            channel.sendMessage("אתה לא בשיחה").queue();
            return;
        }
        if (!selfVoiceState.inAudioChannel()) {
            channel.sendMessage("אני לא בשיחה").queue();
            return;
        }
        String toPlay = message.replace("ginat play", "").strip();
        if (!isUrl(toPlay)) {
            playYoutube();
            return;
        }
        if (toPlay.contains("playlist")) {
            ReadEvent.ISPLAYLIST = true;
            String id = toPlay.replace("https://open.spotify.com/playlist/", "").strip();
            if (id.contains("?") || id.contains("=")) {
                id = id.substring(0, id.indexOf('?'));
            }
            try{
                channel.sendMessage("רק רגע, מתחבר לספוטיפיי...").queue();

                var spotify = new InitiateSpotifyPlaylistRequest(id);

                var songs = spotify.getSongs();

                for (var song : songs) {
                    ReadEvent.ISPLAYLIST = true;
                    PlayerManager.getInstance().loadAndPlay(channel, "ytsearch:" + song);
                }
                ReadEvent.ISPLAYLIST = false;
                channel.sendMessage("הועלה!").queue();


            }
            catch (Exception e){
                channel.sendMessage("בעיה בחיבור לסופטיפיי... נסה יוטיוב").queue();
            }

        }

        else if (toPlay.contains("track"))
        {
            String id = toPlay.replace("https://open.spotify.com/track/", "").strip();
            if (id.contains("?") || id.contains("=")) {
                id = id.substring(0, id.indexOf('?'));
            }
            try {
                var spotify = new InitiateSpotifyTrack(id);
                var track = spotify.getThisTrack();
                PlayerManager.getInstance().loadAndPlay(channel, "ytsearch:" + track);
            } catch (Exception e) {
                channel.sendMessage("בעיה בחיבור לספוטיפיי... נסה יוטיוב!").queue();

            }
        }
    }








    @Override
    public String getType() {
        return "music";
    }

    private boolean isUrl(String url) {
        try {

            new URI(url);
            new URL(url).toURI();

            return true;
        } catch (Throwable e){
            return false;
        }
    }

}

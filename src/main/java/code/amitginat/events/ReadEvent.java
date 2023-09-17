package code.amitginat.events;

import code.amitginat.Bot;
import code.amitginat.commands.AbstractCommand;
import code.amitginat.other.IsraelTime;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ReadEvent extends ListenerAdapter {

    public static boolean ISPLAYLIST;
    public static MessageReceivedEvent thisEvent;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        try {
            thisEvent = event;
            if(event.getMember().getUser().isBot()){return;}
            String message = event.getMessage().getContentRaw();
            if (message.contains("ginat")) {
                String prefix = message.split(" ")[1].toLowerCase().strip();
                Bot.commandManager.setEvents(event);
                for (AbstractCommand command : Bot.commandManager.getCommands()) {
                    if(command.prefix().equals(prefix)){
                        command.run();
                        return;
                    }
                }
                event.getChannel().sendMessage("מתרוצה").queue();

            }
        }
        catch(Throwable t){
            event.getChannel().sendMessage("בעיה..").queue();
            System.out.println(IsraelTime.get() + " " + t);
            t.printStackTrace();
        }


//
//        if(event.isFromGuild()){
//            try {
//                MessageChannelUnion channel = event.getChannel();
//                Guild guild = event.getGuild();
//                String message = event.getMessage().getContentRaw();
//                Member member = event.getMember();
//                Member self = guild.getMember(Bot.getBot().getSelfUser());
//                GuildVoiceState selfVoiceState = self.getVoiceState();
//                GuildVoiceState memberVoiceState = member.getVoiceState();
//                AudioManager audioManager = guild.getAudioManager();
//                AudioChannelUnion memberChannel = memberVoiceState.getChannel();
//                GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(event.getGuild());
//
//                if (event.getMessage().getMember().getUser().isBot()) {
//                    return;
//                }
//                if (message.contains("ginat help")) {
//                    String toSend = """
//
//                        הנה העזרה שחיכית לה
//
//                        **MUSIC:**
//                        -----------------------------------------------------------------
//                        *ginat join - מכניס אותי לצאנל שלך (נחוץ כדי לנגן מוזיקה)*
//                        *ginat leave - תבין לבד*
//                        *ginat play (שיר כלשהו) - מחפש ביוטיוב שיר ומנגן את הראשון שמוצא*
//                        *ginat play (לינק כלשהו) - מנגן את הלינק*
//                        *ginat clearnext - מוחק את כל התור של השירים של אחרי*
//                        *ginat stop - עוצר את השיר שמנגן כרגע*
//                        *ginat resume - ממשיך את השיר שעצרת*
//                        *ginat nowplaying - מראה את השיר שכרגע מתנגן*
//                        *ginat whatsnext - מראה את הרשימה של השירים שבתור*
//                        *ginat spotify (לינק כלשהו) - מנגן את הפלייליסט ספוטיפיי שבלינק*
//                        -----------------------------------------------------------------
//                        **UTILITY**
//                        -----------------------------------------------------------------
//                        *ginat help - בשביל לראות את ההודעה הזאת*
//                        *ginat msg clear (מספר) - מוחק את מספר ההודעות שצוין בצאנל*
//                        *ginat msg send (msg) - פשוט שולח מה שאמרת לו לשלוח. יוזלס רצח*
//                        -----------------------------------------------------------------
//                        """;
//                    channel.sendMessage(toSend).queue();
//                }
//
//
//                if (message.contains("ginat spotify")) {
//                    if (!memberVoiceState.inAudioChannel()) {
//                        channel.sendMessage("Ata Lo Be Channel ya gay").queue();
//                        return;
//                    }
//                    if (!selfVoiceState.inAudioChannel()) {
//                        channel.sendMessage("Ani Lo Be Channel ya gay").queue();
//                        return;
//                    }
//                    String Beforelink = message.replace("ginat spotify", "").strip();
//                    if (!isUrl(Beforelink)) {
//                        channel.sendMessage("Not A Link!").queue();
//                    }
//                    if (message.contains("playlist")) {
//                        ISPLAYLIST = true;
//                        String link = message.replace("ginat spotify", "").strip();
//                        if (!isUrl(link)) {
//                            channel.sendMessage("Not A Link!").queue();
//                        }
//                        String id = message.replace("ginat spotify https://open.spotify.com/playlist/", "").strip();
//                        if (id.contains("?") || id.contains("=")) {
//                            id = id.substring(0, id.indexOf('?'));
//                        }
//
//                        try {
//                            var spotify = new InitiateSpotifyPlaylistRequest(id);
//                            var songs = spotify.getSongs();
//                            channel.sendMessageFormat("Loading %s items from %s...", spotify.getPlaylistCount(), spotify.getPlaylistName()).queue();
//
//                            for (var song : songs) {
//                                ISPLAYLIST = true;
//                                PlayerManager.getInstance().loadAndPlay(channel, "ytsearch:" + song);
//                            }
//
//                            ISPLAYLIST = false;
//                            channel.sendMessage("Loaded Successfully, type {ginat whatsnext} to see the queue").queue();
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//
//                        }
//                    } else if (message.contains("track")) {
//                        String link = message.replace("ginat spotify", "").strip();
//                        if (!isUrl(link)) {
//                            channel.sendMessage("Not A Link!").queue();
//                        }
//                        String id = message.replace("ginat spotify https://open.spotify.com/track/", "").strip();
//                        if (id.contains("?") || id.contains("=")) {
//                            id = id.substring(0, id.indexOf('?'));
//                        }
//                        try {
//                            var spotify = new InitiateSpotifyTrack(id);
//                            var track = spotify.getThisTrack();
//                            PlayerManager.getInstance().loadAndPlay(channel, "ytsearch:" + track);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//
//                        }
//                    }
//                }
//
//
//                if (message.contains("ginat msg send")) {
//                    String toSend = message.replace("ginat msg send", "");
//                    channel.sendMessage(toSend).queue();
//                }
//                if (message.contains("ginat msg clear")) {
//
//                    String amount = message.replace("ginat msg clear", "").strip();
//
//                    if (!isNumber(amount)) {
//                        channel.sendMessage("Amount Is Not A Number!").queue();
//                    }
//                    int amountInt = Integer.parseInt(amount);
//                    if (amountInt < 1 || amountInt > 100) {
//                        channel.sendMessage("Needs To Be Between 1 and 100").queue();
//                        return;
//                    }
//                    List<Message> messageList = channel.getHistory().retrievePast(amountInt + 1).complete();
//                    channel.asTextChannel().deleteMessages(messageList).queue();
//                    channel.sendMessage("Cleared " + amountInt + " Messages.").queue();
//
//                }
//
//                //COMPLETE
//                if (message.contains("ginat join")) {
//                    if (selfVoiceState.inAudioChannel()) {
//                        channel.sendMessage("Ani Be Channel Ya Goofy").queue();
//                        return;
//                    }
//                    if (!memberVoiceState.inAudioChannel()) {
//                        channel.sendMessage("Ata Lo Be Channel Ya Goofy").queue();
//                        return;
//                    }
//                    if (!self.hasPermission(memberVoiceState.getChannel())) {
//                        channel.sendMessage("No Gisha").queue();
//                    }
//
//                    audioManager.openAudioConnection(memberChannel);
//                    channel.sendMessage("Ani Po").queue();
//
//                }
//                if (message.contains("ginat leave")) {
//                    audioManager.closeAudioConnection();
//                    channel.sendMessage("bye").queue();
//                }
//                if (message.contains("ginat play")) {
//                    if (ISPLAYLIST) {
//                        ISPLAYLIST = false;
//                    }
//
//                    if (!memberVoiceState.inAudioChannel()) {
//                        channel.sendMessage("Ata Lo Be Channel ya gay").queue();
//                        return;
//                    }
//                    if (!selfVoiceState.inAudioChannel()) {
//                        channel.sendMessage("Ani Lo Be Channel ya gay").queue();
//                        return;
//                    }
//                    String link = message.replace("ginat play", "").strip();
//
//                    if (!isUrl(link)) {
//                        System.out.println("Not A link");
//                        link = "ytsearch:" + link;
//                    }
//                    System.out.println(link);
//
//                    PlayerManager.getInstance().loadAndPlay(channel, link);
//
//
//                }
//                //
//
//
//
//                if (message.contains("ginat stop")) {
//                    if (!memberVoiceState.inAudioChannel()) {
//                        channel.sendMessage("Ata Lo Be Channel ya gay").queue();
//                        return;
//                    }
//                    if (!selfVoiceState.inAudioChannel()) {
//                        channel.sendMessage("Ani Lo Be Channel ya gay").queue();
//                        return;
//                    }
//
//                    musicManager.trackScheduler.player.setPaused(true);
//                    channel.sendMessage("Stopped").queue();
//                }
//                if (message.contains("ginat clearnext")) {
//                    musicManager.trackScheduler.queue.clear();
//                    channel.sendMessage("Cleared Music Queue").queue();
//                }
//                if (message.contains("ginat resume")) {
//                    if (!memberVoiceState.inAudioChannel()) {
//                        channel.sendMessage("Ata Lo Be Channel ya gay").queue();
//                        return;
//                    }
//                    if (!selfVoiceState.inAudioChannel()) {
//                        channel.sendMessage("Ani Lo Be Channel ya gay").queue();
//                        return;
//                    }
//                    AudioPlayer player = musicManager.trackScheduler.player;
//                    if (!player.isPaused()) {
//                        channel.sendMessage("Nothing To Resume!").queue();
//                        return;
//                    }
//                    player.setPaused(false);
//                    channel.sendMessage("Resumed!").queue();
//                }
//                if (message.contains("ginat skip")) {
//                    if (!memberVoiceState.inAudioChannel()) {
//                        channel.sendMessage("Ata Lo Be Channel ya gay").queue();
//                        return;
//                    }
//                    if (!selfVoiceState.inAudioChannel()) {
//                        channel.sendMessage("Ani Lo Be Channel ya gay").queue();
//                        return;
//                    }
//                    AudioPlayer audioPlayer = musicManager.audioPlayer;
//                    if (audioPlayer.getPlayingTrack() == null) {
//                        channel.sendMessage("Nothing To Skip").queue();
//                        return;
//                    }
//                    musicManager.trackScheduler.nextTrack();
//                    channel.sendMessage("Skipped!").queue();
//                }
//                if (message.contains("ginat nowplaying")) {
//                    if (!memberVoiceState.inAudioChannel()) {
//                        channel.sendMessage("Ata Lo Be Channel ya gay").queue();
//                        return;
//                    }
//                    if (!selfVoiceState.inAudioChannel()) {
//                        channel.sendMessage("Ani Lo Be Channel ya gay").queue();
//                        return;
//                    }
//
//                    AudioPlayer audioPlayer = musicManager.audioPlayer;
//                    AudioTrack track = audioPlayer.getPlayingTrack();
//
//                    if (track == null) {
//                        channel.sendMessage("Nothing is playing").queue();
//                        return;
//                    }
//                    AudioTrackInfo info = track.getInfo();
//                    channel.sendMessageFormat("Currently Playing %s by %s",
//                            info.title,
//                            info.author).queue();
//                }
//                if (message.contains("ginat whatsnext")) {
//                    BlockingQueue<AudioTrack> queue = musicManager.trackScheduler.queue;
//                    if (queue.isEmpty()) {
//                        channel.sendMessage("Music List Is Empty").queue();
//                        return;
//                    }
//                    StringBuilder stringBuilder = new StringBuilder();
//                    int i = 0;
//                    for (AudioTrack track : queue) {
//                        i++;
//                        String formatted = String.format("%n%s. %s by %s",
//                                i,
//                                track.getInfo().title,
//                                track.getInfo().author);
//                        stringBuilder.append(formatted);
//                    }
//                    channel.sendMessage(stringBuilder).queue();
//                }
//            } catch (Exception e) {
//                var channel = event.getChannel();
//                channel.sendMessage("Something Went Wrong... Lemme Try Me Again.").queue();
//                e.printStackTrace();
//            } catch (Throwable e) {
//                var channel = event.getChannel();
//                channel.sendMessage("Something Went Wrong... Try Again Maybe?").queue();
//                e.printStackTrace();
//            }
//        }
//        else{
//            event.getChannel().sendMessage("I Accept Only Server Messages.").queue();
//        }
//    }
//
//
//
//    private boolean isUrl(String url) {
//        try {
//
//            new URI(url);
//            new URL(url).toURI();
//
//            return true;
//        } catch (Throwable e){
//            return false;
//        }
//    }
//
//    private boolean isNumber(String str) {
//        try {
//            Integer.parseInt(str);
//            return true;
//        } catch (Throwable e){return false;}
//
//    }
    }

    public void displayPlaying(AudioTrack track)
    {
        thisEvent.getChannel().sendMessageFormat("Playing %s by %s", track.getInfo().title, track.getInfo().author);

    }
}

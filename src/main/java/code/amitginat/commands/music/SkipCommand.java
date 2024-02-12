package code.amitginat.commands.music;

import code.amitginat.commands.AbstractCommand;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

public class SkipCommand extends AbstractCommand {




    @Override
    public void run() {
        if (!memberVoiceState.inAudioChannel()) {
            channel.sendMessage("אתה לא נמצא בשיחה").queue();
            return;
        }

        if(!selfVoiceState.inAudioChannel()){
            channel.sendMessage("אני לא נמצא בשיחה").queue();
            return;
        }
        AudioPlayer audioPlayer = musicManager.audioPlayer;
        if (audioPlayer.getPlayingTrack() == null) {
            channel.sendMessage("אין על מה לדלג").queue();
            return;
        }
        channel.sendMessage("דולג!").queue();
        musicManager.trackScheduler.nextTrack();
    }
    @Override
    public String getType() {
        return "music";
    }
    @Override
    public String name() {
        return "skip";
    }

    @Override
    public String explain() {
        return "מדלג על השיר הנוכחי";
    }
}

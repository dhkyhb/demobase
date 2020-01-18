package com.wangdh.utilslibrary.utils;

import android.content.Context;
import android.media.AudioManager;

/**
 * @author  wangdh 
 * @date 2020/1/18 14:05
 * 描述:多媒体工具
 */
public class MultimediaUtils {
    private static AudioManager audioManager;
    private static int max;

    private static Context application;

    public static void init(Context context) {
        application = context;
    }

    public static void initAudio() {
        if (audioManager == null) {
            audioManager = (AudioManager) application.getSystemService(Context.AUDIO_SERVICE);
            max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        }
    }

    public static int upVolume() {
        initAudio();
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
        int current = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        return current;
    }

    public static int downVolume() {
        initAudio();
        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
        int current = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        return current;
    }

    public static void setVolume(int i) {
        initAudio();
        if (i < 0) {
            i = 0;
        }
        if (i > max) {
            i = max;
        }
        int current = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        if (current > i) {
            downVolume();
            setVolume(i);
        } else if (current < i) {
            upVolume();
            setVolume(i);
        } else {
            //停止
        }
    }
}

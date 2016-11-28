package com.cui.video.manager;

import android.net.Uri;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

/**
 * Created by cuiyang on 2016/11/20.
 */

public interface MediaPlayerApi {
    /**
     * Determines if media is currently playing on the
     * implementing object
     *
     * @return True if the media is currently playing
     */
    boolean isPlaying();

    void play();

    void pause();

    void stop();

    void reset();

    void release();

    void setVolume(@FloatRange(from = 0.0, to = 1.0) float left, @FloatRange(from = 0.0, to = 1.0) float right);

    void seekTo(@IntRange(from = 0) long milliseconds);

    @IntRange(from = 0)
    long getCurrentPosition();

    @IntRange(from = 0)
    long getDuration();

    void setDataSource(@NonNull Uri uri);
    /**
     * Retrieves the current buffer percent of the audio item.  If an audio item is not currently
     * prepared or buffering the value will be 0.  This should only be called after the audio item is
     *
     * @return The integer percent that is buffered [0, {@value MediaProgress#MAX_BUFFER_PERCENT}] inclusive
     */
    @IntRange(from = 0, to = MediaProgress.MAX_BUFFER_PERCENT)
    int getBufferedPercent();


}

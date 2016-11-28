package com.cui.video.manager;


/**
 * An event to be used to inform listeners of media (e.g. audio, video) progress
 * changes.  This event will be re-used internally to avoid over-creating objects,
 * if you need to store the current values use {@link #obtain(MediaProgress)} to
 * create a duplicate of the current progress
 */
public class MediaProgress {
    public static final int MAX_BUFFER_PERCENT = 100;

    private long position;
    private long duration;
    private int bufferPercent;
    private float bufferPercentFloat;

    public MediaProgress(long position, int bufferPercent, long duration) {
        update(position, bufferPercent, duration);
    }

    public void update(long position, int bufferPercent, long duration) {
        setPosition(position);
        setBufferPercent(bufferPercent);
        setDuration(duration);
    }

    public long getPosition() {
        if (position < 0) {
            position = 0;
        }

        return position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        if (duration < 0) {
            duration = 0;
        }

        this.duration = duration;
    }

    public int getBufferPercent() {
        return bufferPercent;
    }

    public void setBufferPercent(int bufferPercent) {
        //Makes sure the bufferPercent is between 0 and 100 inclusive
        if (bufferPercent < 0) {
            bufferPercent = 0;
        }

        if (bufferPercent > MAX_BUFFER_PERCENT) {
            bufferPercent = MAX_BUFFER_PERCENT;
        }

        this.bufferPercent = bufferPercent;
        this.bufferPercentFloat = bufferPercent == MAX_BUFFER_PERCENT ? (float)bufferPercent : (float) bufferPercent / (float) MAX_BUFFER_PERCENT;
    }

    public float getBufferPercentFloat() {
        return bufferPercentFloat;
    }

    /**
     * Obtains a copy of the passed MediaProgress
     *
     * @param event The MediaProgress to copy
     * @return A copy of the event
     */
    public static MediaProgress obtain(MediaProgress event) {
        return new MediaProgress(event.position, event.bufferPercent, event.duration);
    }
}

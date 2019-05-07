package com.autils.framework.ui.utils.version;

/**
 * Created by fengyulong on 2018/10/24.
 */
public class VersionEvent {
    public interface Event {
        int update = 1;
    }

    public VersionEvent(int event) {
        this.event = event;
    }

    private int event;

    public int getEvent() {
        return event;
    }
}

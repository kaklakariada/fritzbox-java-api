package com.github.kaklakariada.fritzbox.model.homeautomation;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "switch")
public class SwitchState {

    @Element(name = "state", required = false)
    private int state;

    @Element(name = "mode", required = false)
    private String mode;

    @Element(name = "lock", required = false)
    private int lock;

    public int isState() {
        return state;
    }

    public String getMode() {
        return mode;
    }

    public int isLock() {
        return lock;
    }

    @Override
    public String toString() {
        return "SwitchState [state=" + state + ", mode=" + mode + ", lock=" + lock + "]";
    }
}

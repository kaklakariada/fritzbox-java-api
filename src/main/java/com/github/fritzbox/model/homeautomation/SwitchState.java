package com.github.fritzbox.model.homeautomation;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "switch")
public class SwitchState {

    @Element(name = "state")
    private boolean state;

    @Element(name = "mode")
    private String mode;

    @Element(name = "lock")
    private boolean lock;

    public boolean isState() {
        return state;
    }

    public String getMode() {
        return mode;
    }

    public boolean isLock() {
        return lock;
    }

    @Override
    public String toString() {
        return "SwitchState [state=" + state + ", mode=" + mode + ", lock=" + lock + "]";
    }
}

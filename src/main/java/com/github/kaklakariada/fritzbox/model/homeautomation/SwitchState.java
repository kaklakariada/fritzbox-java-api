package com.github.kaklakariada.fritzbox.model.homeautomation;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "switch")
public class SwitchState {

    public enum SwitchMode {
        AUTO, MANUAL
    }

    @Element(name = "state", required = false)
    private String state;

    @Element(name = "mode", required = false)
    private String mode;

    @Element(name = "lock", required = false)
    private String lock;

    public boolean isOn() {
        return "1".equals(state);
    }

    public boolean isLocked() {
        return "1".equals(lock);
    }

    public SwitchMode getMode() {
        switch (mode) {
        case "auto":
            return SwitchMode.AUTO;
        case "manuell":
            return SwitchMode.MANUAL;
        default:
            return null;
        }
    }

    @Override
    public String toString() {
        return "SwitchState [state=" + state + ", mode=" + mode + ", lock=" + lock + "]";
    }
}

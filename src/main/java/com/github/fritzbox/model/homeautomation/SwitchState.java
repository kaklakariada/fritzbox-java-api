package com.github.fritzbox.model.homeautomation;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "switch")
public class SwitchState {

    @XmlElement(name = "state")
    private boolean state;

    @XmlElement(name = "mode")
    private String mode;

    @XmlElement(name = "lock")
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

package com.github.kaklakariada.fritzbox.model.homeautomation;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "simpleonoff")
public class SimpleOnOffState {

    @Element(name = "state")
    private int state;

    public int getState() {
        return state;
    }
}

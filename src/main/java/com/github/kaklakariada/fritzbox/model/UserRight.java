package com.github.kaklakariada.fritzbox.model;

import org.simpleframework.xml.Element;

public class UserRight {

    @Element(name = "Name", required = false)
    private String name;
    @Element(name = "Access", required = false)
    private int access;

    @Override
    public String toString() {
        return "UserRight [name=" + name + ", access=" + access + "]";
    }
}

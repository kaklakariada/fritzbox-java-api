package com.github.kaklakariada.fritzbox.model;

import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "Rights")
public class Rights {

    @ElementList(inline = true, required = false, type = UserRight.class)
    private List<UserRight> rights;

    public List<UserRight> getRights() {
        return rights;
    }

    @Override
    public String toString() {
        return "Rights [rights=" + rights + "]";
    }
}

package com.github.fritzbox.model;

import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "SessionInfo")
public class SessionInfo {

    @Element(name = "SID")
    private String sid;

    @Element(name = "Challenge")
    private String challenge;

    @Element(name = "BlockTime")
    private String blockTime;

    @ElementList(name = "Rights", inline = false, required = false)
    private List<UserRight> rights;
    // @Element(name = "Rights")
    // private Rights rights;

    public String getSid() {
        return sid;
    }

    public String getChallenge() {
        return challenge;
    }

    public String getBlockTime() {
        return blockTime;
    }

    // public List<UserRight> getRights() {
    // return rights.getRights();
    // }

    @Override
    public String toString() {
        return "SessionInfo [sid=" + sid + ", challenge=" + challenge + ", blockTime=" + blockTime + ", rights="
                + rights + "]";
    }
}

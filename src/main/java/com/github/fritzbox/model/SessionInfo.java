package com.github.fritzbox.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SessionInfo")
public class SessionInfo {

    private String sid;
    private String challenge;
    private String blockTime;
    private String rights;

    @XmlElement(name = "SID")
    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    @XmlElement(name = "Challenge")
    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    @XmlElement(name = "BlockTime")
    public String getBlockTime() {
        return blockTime;
    }

    public void setBlockTime(String blockTime) {
        this.blockTime = blockTime;
    }

    @XmlElement(name = "Rights")
    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    @Override
    public String toString() {
        return "SessionInfo [sid=" + sid + ", challenge=" + challenge + ", blockTime=" + blockTime + ", rights="
                + rights + "]";
    }
}

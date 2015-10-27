package com.github.fritzbox.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "SessionInfo")
public class SessionInfo {

    @XmlElement(name = "SID")
    private String sid;

    @XmlElement(name = "Challenge")
    private String challenge;

    @XmlElement(name = "BlockTime")
    private String blockTime;

    @XmlElement(name = "Rights")
    private String rights;

    public String getSid() {
        return sid;
    }

    public String getChallenge() {
        return challenge;
    }

    public String getBlockTime() {
        return blockTime;
    }

    public String getRights() {
        return rights;
    }

    @Override
    public String toString() {
        return "SessionInfo [sid=" + sid + ", challenge=" + challenge + ", blockTime=" + blockTime + ", rights="
                + rights + "]";
    }
}

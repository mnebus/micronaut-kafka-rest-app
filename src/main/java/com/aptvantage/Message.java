package com.aptvantage;

import io.micronaut.core.annotation.Introspected;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.LinkedHashMap;

@EqualsAndHashCode
@ToString
@Introspected
public class Message {
    private String key;
    private LinkedHashMap otherStuff;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LinkedHashMap getOtherStuff() {
        return otherStuff;
    }

    public void setOtherStuff(LinkedHashMap otherStuff) {
        this.otherStuff = otherStuff;
    }
}

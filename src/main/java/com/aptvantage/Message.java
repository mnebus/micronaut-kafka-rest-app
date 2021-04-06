package com.aptvantage;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;

@EqualsAndHashCode
@ToString
@Getter
@Setter
public class Message {
    private String key;
    private LinkedHashMap otherStuff;
}

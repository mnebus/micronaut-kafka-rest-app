package com.aptvantage;

import io.micronaut.core.annotation.Introspected;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;

@EqualsAndHashCode
@ToString
@Getter
@Setter
@Introspected
public class Message {
    private String key;
    private LinkedHashMap otherStuff;
}

package com.aptvantage

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
class Message {
    String key
    LinkedHashMap otherStuff
}

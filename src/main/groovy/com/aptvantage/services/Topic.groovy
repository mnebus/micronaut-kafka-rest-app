package com.aptvantage.services

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
class Topic {
    String name
    int partitions
}

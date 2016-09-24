package com.kolyadko.likeit.memorycontainer.impl;

import com.kolyadko.likeit.type.MemoryContainerType;

/**
 * Created by DaryaKolyadko on 04.08.2016.
 */

/**
 * Error container (is used when transfer data from action command)
 */
public class ErrorMemoryContainer extends TextMemoryContainer {
    public ErrorMemoryContainer(String text) {
        super(text, MemoryContainerType.ONE_OFF);
    }
}
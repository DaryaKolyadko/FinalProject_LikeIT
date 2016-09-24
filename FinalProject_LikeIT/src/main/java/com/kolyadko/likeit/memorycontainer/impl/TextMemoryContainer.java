package com.kolyadko.likeit.memorycontainer.impl;

import com.kolyadko.likeit.memorycontainer.MemoryContainer;
import com.kolyadko.likeit.type.MemoryContainerType;

/**
 * Created by DaryaKolyadko on 03.08.2016.
 */

/**
 * Text container (is used when transfer data from action command)
 */
public class TextMemoryContainer extends MemoryContainer {
    private final String text;

    public TextMemoryContainer(String text, MemoryContainerType containerType) {
        this.containerType = containerType;
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
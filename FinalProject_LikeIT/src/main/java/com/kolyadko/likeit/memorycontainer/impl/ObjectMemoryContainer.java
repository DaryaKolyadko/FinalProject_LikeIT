package com.kolyadko.likeit.memorycontainer.impl;

import com.kolyadko.likeit.memorycontainer.MemoryContainer;
import com.kolyadko.likeit.type.MemoryContainerType;

/**
 * Created by DaryaKolyadko on 03.08.2016.
 */

/**
 * Object container (is used when transfer data from action command)
 */
public class ObjectMemoryContainer extends MemoryContainer {
    private final Object object;

    public ObjectMemoryContainer(Object object, MemoryContainerType containerType) {
        this.object = object;
        this.containerType = containerType;
    }

    public Object getObject() {
        return object;
    }
}
package com.kolyadko.likeit.memorycontainer;

import com.kolyadko.likeit.type.MemoryContainerType;

/**
 * Created by DaryaKolyadko on 04.08.2016.
 */
public abstract class MemoryContainer {
    protected MemoryContainerType containerType;

    public MemoryContainerType getContainerType() {
        return containerType;
    }

    public boolean isOneOff() {
        return containerType == MemoryContainerType.ONE_OFF;
    }
}
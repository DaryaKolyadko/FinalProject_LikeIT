package com.kolyadko.likeit.memorycontainer.impl.uncompleteddata;

import com.kolyadko.likeit.memorycontainer.MemoryContainer;
import com.kolyadko.likeit.type.MemoryContainerType;

/**
 * Created by DaryaKolyadko on 29.08.2016.
 */
public class UncompletedSectionMemoryContainer extends MemoryContainer {
    private String name;
    private String labelColor;
    private String majorSectionId;

    public UncompletedSectionMemoryContainer() {
        containerType = MemoryContainerType.ONE_OFF;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabelColor() {
        return labelColor;
    }

    public void setLabelColor(String labelColor) {
        this.labelColor = labelColor;
    }

    public String getMajorSectionId() {
        return majorSectionId;
    }

    public void setMajorSectionId(String majorSectionId) {
        this.majorSectionId = majorSectionId;
    }
}
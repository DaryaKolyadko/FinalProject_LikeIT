package com.kolyadko.likeit.memorycontainer.impl.uncompleteddata;

import com.kolyadko.likeit.memorycontainer.MemoryContainer;
import com.kolyadko.likeit.type.MemoryContainerType;

/**
 * Created by DaryaKolyadko on 29.08.2016.
 */
public class UncompletedQuestionMemoryContainer extends MemoryContainer {
    private String sectionId;
    private String title;
    private String text;

    public UncompletedQuestionMemoryContainer() {
        containerType = MemoryContainerType.ONE_OFF;
    }

    public UncompletedQuestionMemoryContainer(String sectionId, String title, String text) {
        this();
        this.sectionId = sectionId;
        this.title = title;
        this.text = text;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
package com.kolyadko.likeit.memorycontainer.impl.uncompleteddata;

import com.kolyadko.likeit.memorycontainer.MemoryContainer;
import com.kolyadko.likeit.type.MemoryContainerType;

/**
 * Created by DaryaKolyadko on 29.08.2016.
 */
public class UncompletedCommentMemoryContainer extends MemoryContainer {
    private String questionId;
    private String text;

    public UncompletedCommentMemoryContainer() {
        containerType = MemoryContainerType.ONE_OFF;
    }

    public UncompletedCommentMemoryContainer(String questionId, String text) {
        this();
        this.questionId = questionId;
        this.text = text;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
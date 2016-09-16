package com.kolyadko.likeit.entity;

/**
 * Created by DaryaKolyadko on 22.07.2016.
 */

/**
 * Section entity
 */
public class Section extends Entity<Integer> {
    private Integer majorSectionId;
    private String name;
    private Integer questionNum;
    private Integer answerNum;
    private String labelColor;
    private Boolean archive;

    public Integer getMajorSectionId() {
        return majorSectionId;
    }

    public void setMajorSectionId(Integer majorSectionId) {
        this.majorSectionId = majorSectionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(Integer questionNum) {
        this.questionNum = questionNum;
    }

    public Integer getAnswerNum() {
        return answerNum;
    }

    public void setAnswerNum(Integer answerNum) {
        this.answerNum = answerNum;
    }

    public String getLabelColor() {
        return labelColor;
    }

    public void setLabelColor(String labelColor) {
        this.labelColor = labelColor;
    }

    public Boolean getArchive() {
        return archive;
    }

    public void setArchive(Boolean archive) {
        this.archive = archive;
    }

    public boolean isMajor() {
        return majorSectionId == null || majorSectionId == 0;
    }
}
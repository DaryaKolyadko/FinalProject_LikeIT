package com.kolyadko.likeit.entity;

/**
 * Created by DaryaKolyadko on 22.07.2016.
 */
public class Section extends Entity<Integer> {
    private int majorSectionId;
    private String name;
    private int questionNum;
    private boolean archive;

    public Section() {
        super(0);
    }

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    public int getMajorSectionId() {
        return majorSectionId;
    }

    public void setMajorSectionId(int majorSectionId) {
        this.majorSectionId = majorSectionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuestionNum() {
        return questionNum;
    }

    public void setQuestionNum(int questionNum) {
        this.questionNum = questionNum;
    }
}

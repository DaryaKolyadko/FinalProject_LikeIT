package com.kolyadko.likeit.entity;

/**
 * Created by DaryaKolyadko on 22.07.2016.
 */

/**
 * Section entity
 */
public class Section extends Entity<Long> {
    private Long majorSectionId;
    private String name;
    private Integer questionNum;
    private Integer answerNum;
    private String labelColor;
    private Boolean archive;

    public Long getMajorSectionId() {
        return majorSectionId;
    }

    public void setMajorSectionId(Long majorSectionId) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Section)) return false;
        if (!super.equals(o)) return false;

        Section section = (Section) o;

        if (majorSectionId != null ? !majorSectionId.equals(section.majorSectionId) : section.majorSectionId != null)
            return false;
        if (name != null ? !name.equals(section.name) : section.name != null) return false;
        if (questionNum != null ? !questionNum.equals(section.questionNum) : section.questionNum != null) return false;
        if (answerNum != null ? !answerNum.equals(section.answerNum) : section.answerNum != null) return false;
        if (labelColor != null ? !labelColor.equals(section.labelColor) : section.labelColor != null) return false;
        return archive != null ? archive.equals(section.archive) : section.archive == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (majorSectionId != null ? majorSectionId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (questionNum != null ? questionNum.hashCode() : 0);
        result = 31 * result + (answerNum != null ? answerNum.hashCode() : 0);
        result = 31 * result + (labelColor != null ? labelColor.hashCode() : 0);
        result = 31 * result + (archive != null ? archive.hashCode() : 0);
        return result;
    }
}
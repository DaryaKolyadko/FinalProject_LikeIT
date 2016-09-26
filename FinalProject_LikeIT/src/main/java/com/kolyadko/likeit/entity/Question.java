package com.kolyadko.likeit.entity;

import java.sql.Timestamp;

/**
 * Created by DaryaKolyadko on 22.07.2016.
 */

/**
 * Question entity
 */
public class Question extends Entity<Long> {
    private String authorId;
    private Long sectionId;
    private String title;
    private String text;
    private Timestamp creationDate;
    private Float rating;
    private Boolean archive;

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
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

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Boolean getArchive() {
        return archive;
    }

    public void setArchive(Boolean archive) {
        this.archive = archive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Question)) return false;
        if (!super.equals(o)) return false;

        Question question = (Question) o;

        if (authorId != null ? !authorId.equals(question.authorId) : question.authorId != null) return false;
        if (sectionId != null ? !sectionId.equals(question.sectionId) : question.sectionId != null) return false;
        if (title != null ? !title.equals(question.title) : question.title != null) return false;
        if (text != null ? !text.equals(question.text) : question.text != null) return false;
        if (creationDate != null ? !creationDate.equals(question.creationDate) : question.creationDate != null)
            return false;
        if (rating != null ? !rating.equals(question.rating) : question.rating != null) return false;
        return archive != null ? archive.equals(question.archive) : question.archive == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (authorId != null ? authorId.hashCode() : 0);
        result = 31 * result + (sectionId != null ? sectionId.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (archive != null ? archive.hashCode() : 0);
        return result;
    }
}
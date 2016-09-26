package com.kolyadko.likeit.entity;

import java.sql.Timestamp;

/**
 * Created by DaryaKolyadko on 22.07.2016.
 */

/**
 * Comment entity
 */
public class Comment extends Entity<Long> {
    private String authorId;
    private Long questionId;
    private String text;
    private Timestamp creationDate;
    private Boolean answer;
    private Float rating;
    private Boolean archive;

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
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

    public Boolean getAnswer() {
        return answer;
    }

    public void setAnswer(Boolean answer) {
        this.answer = answer;
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
        if (!(o instanceof Comment)) return false;
        if (!super.equals(o)) return false;

        Comment comment = (Comment) o;

        if (authorId != null ? !authorId.equals(comment.authorId) : comment.authorId != null) return false;
        if (questionId != null ? !questionId.equals(comment.questionId) : comment.questionId != null) return false;
        if (text != null ? !text.equals(comment.text) : comment.text != null) return false;
        if (creationDate != null ? !creationDate.equals(comment.creationDate) : comment.creationDate != null)
            return false;
        if (answer != null ? !answer.equals(comment.answer) : comment.answer != null) return false;
        if (rating != null ? !rating.equals(comment.rating) : comment.rating != null) return false;
        return archive != null ? archive.equals(comment.archive) : comment.archive == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (authorId != null ? authorId.hashCode() : 0);
        result = 31 * result + (questionId != null ? questionId.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (answer != null ? answer.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (archive != null ? archive.hashCode() : 0);
        return result;
    }
}
package com.kolyadko.likeit.entity;

/**
 * Created by DaryaKolyadko on 22.07.2016.
 */
public class Avatar extends Entity<Integer> {
    private String imageUrl;

    public Avatar(String imageUrl) {
        super(0);
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
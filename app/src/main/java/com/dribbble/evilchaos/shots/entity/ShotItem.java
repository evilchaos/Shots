package com.dribbble.evilchaos.shots.entity;

/**
 * Created by liujiachao on 2016/12/12.
 */

public class ShotItem {
    int id;
    String title;
    String description;
    int width;
    int height;
    ShotImage images;
    int views_count;
    int likes_count;
    int comments_count;
    int attachments_count;
    int rebounds_count;
    int buckets_count;
    String created_at;
    String updated_at;
    String html_url;
    String attachments_url;
    String buckets_url;
    String comments_url;
    String likes_url;
    String projects_url;
    String rebounds_url;
    boolean animated;

    //该Shot所属的用户
    User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ShotImage getImages() {
        return images;
    }

    public void setImages(ShotImage images) {
        this.images = images;
    }

    public int getViews_count() {
        return views_count;
    }

    public void setViews_count(int views_count) {
        this.views_count = views_count;
    }

    public int getLikes_count() {
        return likes_count;
    }

    public void setLikes_count(int likes_count) {
        this.likes_count = likes_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getAttachments_count() {
        return attachments_count;
    }

    public void setAttachment_count(int attachment_count) {
        this.attachments_count = attachment_count;
    }

    public int getRebounds_count() {
        return rebounds_count;
    }

    public void setRebounds_count(int rebounds_count) {
        this.rebounds_count = rebounds_count;
    }

    public int getBuckets_count() {
        return buckets_count;
    }

    public void setBuckets_count(int buckets_count) {
        this.buckets_count = buckets_count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getAttachments_url() {
        return attachments_url;
    }

    public void setAttachments_url(String attachments_url) {
        this.attachments_url = attachments_url;
    }

    public String getBuckets_url() {
        return buckets_url;
    }

    public void setBuckets_url(String buckets_url) {
        this.buckets_url = buckets_url;
    }

    public String getComments_url() {
        return comments_url;
    }

    public void setComments_url(String comments_url) {
        this.comments_url = comments_url;
    }

    public String getLikes_url() {
        return likes_url;
    }

    public void setLikes_url(String likes_url) {
        this.likes_url = likes_url;
    }

    public String getProjects_url() {
        return projects_url;
    }

    public void setProjects_url(String projects_url) {
        this.projects_url = projects_url;
    }

    public String getRebounds_url() {
        return rebounds_url;
    }

    public void setRebounds_url(String rebounds_url) {
        this.rebounds_url = rebounds_url;
    }

    public boolean isAnimated() {
        return animated;
    }

    public void setAnimated(boolean animated) {
        this.animated = animated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

package com.jdpublication.news.Parse;

public class NewsListParse {
    String source;
    String author;
    String imageURL;
    String title;
    String description;
    String publishedAt;
    String content;

    public NewsListParse(String source, String author, String imageURL, String title, String description, String publishedAt, String content) {
        this.source = source;
        this.author = author;
        this.imageURL = imageURL;
        this.title = title;
        this.description = description;
        this.publishedAt = publishedAt;
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public String getAuthor() {
        return author;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getContent() {
        return content;
    }
}

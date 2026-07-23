package com.alikaracor.learning.springrestbasics.model;


public class Task {

    public Task() {
    }



    public Task(Long id, String description, String title, boolean completed) {
        this.id = id;
        this.description = description;
        this.title = title;
        this.completed = completed;
    }


    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    private Long id;
    private String description;
    private String title;
    private boolean completed;

}

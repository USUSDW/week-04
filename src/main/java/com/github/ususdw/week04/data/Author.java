package com.github.ususdw.week04.data;

import java.util.Map;

public class Author {
    private String name;
    private Map<String, String> social;
    private String bio;

    public Author(String name, Map<String, String> social, String bio) {
        this.name = name;
        this.social = social;
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getSocial() {
        return social;
    }

    public void setSocial(Map<String, String> social) {
        this.social = social;
    }

    public String getBio() {
        return bio;
    }

    @Override
    public String toString() {
        return "Author{" +
            "name='" + name + '\'' +
            ", social=" + social +
            ", bio='" + bio + '\'' +
            '}';
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}

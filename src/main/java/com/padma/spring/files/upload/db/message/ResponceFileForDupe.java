package com.padma.spring.files.upload.db.message;

public class ResponceFileForDupe {
    private String name;

    private String hasCode;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    private String authorName;


    public ResponceFileForDupe(String name,  String hasCode,String authorName) {
        this.name = name;
        this.hasCode = hasCode;
        this.authorName = authorName;
    }
    public String getHasCode() {
        return hasCode;
    }

    public void setHasCode(String hasCode) {
        this.hasCode = hasCode;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

package com.padma.spring.files.upload.db.message;

public class ResponseFile {
  private String name;
  private String url;
  private String type;
  private long size;
  private String hasCode;

  public String getAuthorName() {
    return authorName;
  }

  public void setAuthorName(String authorName) {
    this.authorName = authorName;
  }

  private String authorName;


  public ResponseFile(String name, String url, String type, long size,String hasCode,String authorName) {
    this.name = name;
    this.url = url;
    this.type = type;
    this.size = size;
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

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }

}

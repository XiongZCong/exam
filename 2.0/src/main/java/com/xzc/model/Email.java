package com.xzc.model;

import org.springframework.core.io.FileSystemResource;

public class Email {
    private String to;
    private String subject;
    private String text;
    private FileSystemResource[] files;

    public Email() {
    }

    public Email(String to, String subject, String text) {
        this.to = to;
        this.subject = String.format("[%s]", subject);
        this.text = text;
    }

    public Email(String to, String subject, String text, FileSystemResource[] files) {
        this.to = to;
        this.subject = String.format("[%s]", subject);
        this.text = text;
        this.files = files;
    }


    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public FileSystemResource[] getFiles() {
        return files;
    }

    public void setFiles(FileSystemResource[] files) {
        this.files = files;
    }
}

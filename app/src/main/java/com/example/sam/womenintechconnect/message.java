package com.example.sam.womenintechconnect;
import java.util.Date;
public class message {
    public message(String text, String to,String from) {
        this.text = text;
        this.Date= new Date().getTime();
        this.To = to;
        this.from=from;
    }

    private String text;
    private Long Date;
    private String To;
    private String from;

    public message() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getDate() {
        return Date;
    }

    public void setDate(Long date) {
        Date = date;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}

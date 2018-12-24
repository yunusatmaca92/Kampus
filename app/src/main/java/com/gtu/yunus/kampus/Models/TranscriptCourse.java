package com.gtu.yunus.kampus.Models;

public class TranscriptCourse {
    private String code;
    private String name;
    private String type;
    private String note;
    private String gtuKredi;
    private String aktsKredi;

    public TranscriptCourse(String code, String name, String type, String note, String gtuKredi, String aktsKredi) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.note = note;
        this.gtuKredi = gtuKredi;
        this.aktsKredi = aktsKredi;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getGtuKredi() {
        return gtuKredi;
    }

    public void setGtuKredi(String gtuKredi) {
        this.gtuKredi = gtuKredi;
    }

    public String getAktsKredi() {
        return aktsKredi;
    }

    public void setAktsKredi(String aktsKredi) {
        this.aktsKredi = aktsKredi;
    }
}

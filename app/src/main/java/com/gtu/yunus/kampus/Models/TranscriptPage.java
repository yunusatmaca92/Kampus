package com.gtu.yunus.kampus.Models;

import java.util.ArrayList;

public class TranscriptPage {
    private ArrayList<TranscriptTerm> terms;

    public TranscriptPage(ArrayList<TranscriptTerm> terms) {
        this.terms = terms;
    }

    public ArrayList<TranscriptTerm> getTerms() {
        return terms;
    }

    public void setTerms(ArrayList<TranscriptTerm> terms) {
        this.terms = terms;
    }
}

package com.gtu.yunus.kampus.Request;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

public class HTTPRequest {

    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.77 Safari/537.36";
    private Connection.Response form = null;
    private Document document = null;

    public HTTPRequest(Document document){
        this.document = document;
    }

    public Document getDocument() {
        return document;
    }

    public Connection.Response GET(String url){

        try {
            form = Jsoup.connect(url)
                    .method(Connection.Method.GET)
                    .userAgent(USER_AGENT)
                    .execute();
            document = form.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return form;
    }
    public Connection.Response GET(String url, Map cookies){

        try {
            form = Jsoup.connect(url)
                    .cookies(cookies)
                    .method(Connection.Method.GET)
                    .userAgent(USER_AGENT)
                    .timeout(10000)
                    .execute();
            document = form.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return form;
    }
    public Connection.Response POST(String url,Map cookies,Map data){

        try {
            form = Jsoup.connect(url)
                    .cookies(cookies)
                    .data(data)
                    .method(Connection.Method.POST)
                    .userAgent(USER_AGENT)
                    .timeout(10000)
                    .execute();
            document = form.parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return form;
    }

}

package com.example.myapplication.model;

public class HistoryItem {
    private final long id;
    private final String expression;
    private final String result;
    private final String timestamp;

    public HistoryItem(long id, String expression, String result, String timestamp) {
        this.id = id;
        this.expression = expression;
        this.result = result;
        this.timestamp = timestamp;
    }

    public long getId() { return id; }
    public String getExpression() { return expression; }
    public String getResult() { return result; }
    public String getTimestamp() { return timestamp; }
}

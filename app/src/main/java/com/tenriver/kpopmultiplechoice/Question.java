package com.tenriver.kpopmultiplechoice;

public class Question {
    public static final int YEAR_TWOTHOUSANDTEN = 2010;
    public static final int YEAR_TWOTHOUSANDFIFTEEN=2015;
    public static final int YEAR_TWOTHOUSANDSIXTEEN=2016;
    public static final int YEAR_TWOTHOUSANDSEVENTEEN=2015;
    public static final int YEAR_TWOTHOUSANDTWENTY = 2020;
    public static final int YEAR_TWOTHOUSANDTWENTYONE = 2021;


    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int answerNr;
    private String singer;
    private int year;

    public Question() {}

    public Question(String question, String option1, String option2, String option3, String option4, int answerNr, String singer,int year) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answerNr = answerNr;
        this.singer = singer;
        this.year = year;
    }

    //getter and setter


    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public int getAnswerNr() {
        return answerNr;
    }

    public void setAnswerNr(int answerNr) {
        this.answerNr = answerNr;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public static int[] getAllYear() {
        return new int[]{
                2015,
                2016,
                2017,
                2018,
                2019,
                2020,
                2021
        };
    }
}

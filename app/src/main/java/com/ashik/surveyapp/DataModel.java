package com.ashik.surveyapp;

public class DataModel {

    private int count;
    private String ques1;
    private String ans1;
    private String ques2;
    private String ans2;
    private String ques3;
    private String ans3;
    private String ques4;
    private String ans4;
    private String ques5;
    private String ans5;
    private String date;

    public DataModel(int count, String ques1, String ans1, String ques2, String ans2, String ques3, String ans3, String ques4, String ans4, String ques5, String ans5, String date) {
        this.count = count;
        this.ques1 = ques1;
        this.ans1 = ans1;
        this.ques2 = ques2;
        this.ans2 = ans2;
        this.ques3 = ques3;
        this.ans3 = ans3;
        this.ques4 = ques4;
        this.ans4 = ans4;
        this.ques5 = ques5;
        this.ans5 = ans5;
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getQues1() {
        return ques1;
    }

    public void setQues1(String ques1) {
        this.ques1 = ques1;
    }

    public String getAns1() {
        return ans1;
    }

    public void setAns1(String ans1) {
        this.ans1 = ans1;
    }

    public String getQues2() {
        return ques2;
    }

    public void setQues2(String ques2) {
        this.ques2 = ques2;
    }

    public String getAns2() {
        return ans2;
    }

    public void setAns2(String ans2) {
        this.ans2 = ans2;
    }

    public String getQues3() {
        return ques3;
    }

    public void setQues3(String ques3) {
        this.ques3 = ques3;
    }

    public String getAns3() {
        return ans3;
    }

    public void setAns3(String ans3) {
        this.ans3 = ans3;
    }

    public String getQues4() {
        return ques4;
    }

    public void setQues4(String ques4) {
        this.ques4 = ques4;
    }

    public String getAns4() {
        return ans4;
    }

    public void setAns4(String ans4) {
        this.ans4 = ans4;
    }

    public String getQues5() {
        return ques5;
    }

    public void setQues5(String ques5) {
        this.ques5 = ques5;
    }

    public String getAns5() {
        return ans5;
    }

    public void setAns5(String ans5) {
        this.ans5 = ans5;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

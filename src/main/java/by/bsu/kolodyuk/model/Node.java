package by.bsu.kolodyuk.model;


public class Node {

    private String question;
    private String option;

    public Node(String question, String option) {
        this.question = question;
        this.option = option;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    @Override
    public String toString() {
        return option;
    }
}

package trivia;

public abstract class Question {
    private final String text;

    protected Question(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}

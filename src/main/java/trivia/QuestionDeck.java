package trivia;

import java.util.Deque;
import java.util.LinkedList;

public class QuestionDeck<T extends Question> {
    private final Deque<T> questions = new LinkedList<>();

    public void add(T question) {
        questions.addLast(question);
    }

    public T draw() {
        return questions.removeFirst();
    }
}

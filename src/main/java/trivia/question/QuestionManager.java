package trivia.question;

public class QuestionManager {
    private final QuestionDeck<PopQuestion> popQuestions = new QuestionDeck<>();
    private final QuestionDeck<ScienceQuestion> scienceQuestions = new QuestionDeck<>();
    private final QuestionDeck<SportsQuestion> sportsQuestions = new QuestionDeck<>();
    private final QuestionDeck<RockQuestion> rockQuestions = new QuestionDeck<>();

    public QuestionManager() {
        initialiseQuestionDecks();
    }

    private void initialiseQuestionDecks() {
        for (int i = 0; i < 50; i++) {
            popQuestions.add(new PopQuestion("Pop Question " + i));
            scienceQuestions.add(new ScienceQuestion("Science Question " + i));
            sportsQuestions.add(new SportsQuestion("Sports Question " + i));
            rockQuestions.add(new RockQuestion("Rock Question " + i));
        }
    }

    public void askQuestion(int currentPlaceOnBoard) {
        Category currentCategory = currentCategory(currentPlaceOnBoard);
        System.out.println("The category is " + currentCategory);

        Question question  = switch (currentCategory) {
            case POP -> popQuestions.draw();
            case SCIENCE -> scienceQuestions.draw();
            case SPORTS -> sportsQuestions.draw();
            case ROCK -> rockQuestions.draw();
        };

        System.out.println(question.getText());
    }

    private Category currentCategory(int currentPlaceOnBoard) {
        return switch (currentPlaceOnBoard) {
            case 0, 4, 8 -> Category.POP;
            case 1, 5, 9 -> Category.SCIENCE;
            case 2, 6, 10 -> Category.SPORTS;
            default -> Category.ROCK;
        };
    }
}
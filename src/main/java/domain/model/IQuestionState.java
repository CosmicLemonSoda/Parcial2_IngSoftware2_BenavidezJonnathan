package domain.model;

public interface IQuestionState {
    void submitForReview(Question context);
    String getStateName();
}

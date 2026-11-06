package domain.model;

public class PendingReviewState implements IQuestionState {
    @Override
    public void submitForReview(Question context) {
        throw new IllegalStateException("La pregunta ya está en estado PENDING_REVIEW.");
    }

    @Override
    public String getStateName() {
        return "PENDING_REVIEW";
    }
}

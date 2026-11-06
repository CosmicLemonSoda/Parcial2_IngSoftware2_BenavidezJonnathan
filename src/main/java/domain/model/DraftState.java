package domain.model;

public class DraftState implements IQuestionState {
    @Override
    public void submitForReview(Question context) {
        context.setState(new PendingReviewState());
    }

    @Override
    public String getStateName() {
        return "DRAFT";
    }
}

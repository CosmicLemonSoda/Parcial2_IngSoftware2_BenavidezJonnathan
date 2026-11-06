package domain.model;

import java.util.List;

public class Question {
    private String id;
    private String statement;
    private String justification;
    private List<AnswerOption> options;
    private IQuestionState state;

    public Question() {}

    public Question(String id, String statement, String justification, List<AnswerOption> options, IQuestionState state) {
        this.id = id;
        this.statement = statement;
        this.justification = justification;
        this.options = options;
        this.state = state;
    }

    public void submitForReview() {
        this.state.submitForReview(this);
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getStatement() { return statement; }
    public void setStatement(String statement) { this.statement = statement; }
    public String getJustification() { return justification; }
    public void setJustification(String justification) { this.justification = justification; }
    public List<AnswerOption> getOptions() { return options; }
    public void setOptions(List<AnswerOption> options) { this.options = options; }
    public IQuestionState getState() { return state; }
    public void setState(IQuestionState state) { this.state = state; }
}

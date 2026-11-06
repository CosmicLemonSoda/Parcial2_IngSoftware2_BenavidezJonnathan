package domain.model;

public interface IQuestionBuilder {
    IQuestionBuilder setId(String id);
    IQuestionBuilder setStatement(String statement);
    IQuestionBuilder setJustification(String justification);
    IQuestionBuilder addOption(String id, String text, boolean correct);
    Question build();
}

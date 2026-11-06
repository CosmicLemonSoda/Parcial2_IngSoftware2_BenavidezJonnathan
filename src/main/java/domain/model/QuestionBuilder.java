package domain.model;

import java.util.ArrayList;
import java.util.List;

public class QuestionBuilder implements IQuestionBuilder {
    private String id;
    private String statement;
    private String justification;
    private final List<AnswerOption> options = new ArrayList<>();

    @Override
    public IQuestionBuilder setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public IQuestionBuilder setStatement(String statement) {
        this.statement = statement;
        return this;
    }

    @Override
    public IQuestionBuilder setJustification(String justification) {
        this.justification = justification;
        return this;
    }

    @Override
    public IQuestionBuilder addOption(String id, String text, boolean correct) {
        this.options.add(new AnswerOption(id, text, correct));
        return this;
    }

    @Override
    public Question build() {
        if (options.size() != 4) {
            throw new IllegalStateException("El reactivo debe incluir exactamente 4 opciones de respuesta.");
        }
        long correctOptions = options.stream().filter(AnswerOption::isCorrect).count();
        if (correctOptions != 1) {
            throw new IllegalStateException("El reactivo debe incluir exactamente 1 opcion correcta.");
        }
        if (justification == null || justification.trim().isEmpty()) {
            throw new IllegalStateException("La justificacion es obligatoria.");
        }
        return new Question(id, statement, justification, options, new DraftState());
    }
}

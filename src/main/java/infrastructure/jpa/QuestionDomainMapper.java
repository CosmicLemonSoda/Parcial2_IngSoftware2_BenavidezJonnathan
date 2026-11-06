package infrastructure.jpa;

import domain.model.*;
import infrastructure.jpa.record.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionDomainMapper {

    public QuestionRecord toRecord(Question domain) {
        QuestionRecord record = new QuestionRecord();
        record.setId(domain.getId());
        record.setStatement(domain.getStatement());
        record.setJustification(domain.getJustification());
        record.setState(domain.getState() != null ? domain.getState().getStateName() : "DRAFT");

        if (domain.getOptions() != null) {
            domain.getOptions().forEach(opt -> {
                AnswerOptionRecord optRecord = new AnswerOptionRecord();
                optRecord.setId(opt.getId());
                optRecord.setText(opt.getText());
                optRecord.setCorrect(opt.isCorrect());
                record.addOption(optRecord);
            });
        }
        return record;
    }

    public Question toDomain(QuestionRecord record) {
        IQuestionState state = "PENDING_REVIEW".equalsIgnoreCase(record.getState())
                ? new PendingReviewState()
                : new DraftState();

        List<AnswerOption> options = record.getOptions().stream()
                .map(opt -> new AnswerOption(opt.getId(), opt.getText(), opt.isCorrect()))
                .collect(Collectors.toList());

        return new Question(
                record.getId(),
                record.getStatement(),
                record.getJustification(),
                options,
                state
        );
    }
}

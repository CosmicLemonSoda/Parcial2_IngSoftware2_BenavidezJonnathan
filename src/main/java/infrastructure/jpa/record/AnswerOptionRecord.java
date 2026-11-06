package infrastructure.jpa.record;

import jakarta.persistence.*;

@Entity
@Table(name = "answer_options")
public class AnswerOptionRecord {

    @Id
    private String id;
    private String text;
    private boolean correct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private QuestionRecord questionRecord;

    public AnswerOptionRecord() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public boolean isCorrect() { return correct; }
    public void setCorrect(boolean correct) { this.correct = correct; }
    public QuestionRecord getQuestionRecord() { return questionRecord; }
    public void setQuestionRecord(QuestionRecord questionRecord) { this.questionRecord = questionRecord; }
}

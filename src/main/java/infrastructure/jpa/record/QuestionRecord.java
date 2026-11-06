package infrastructure.jpa.record;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
public class QuestionRecord {

    @Id
    private String id;
    private String statement;
    private String justification;
    private String state;

    @OneToMany(mappedBy = "questionRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerOptionRecord> options = new ArrayList<>();

    public QuestionRecord() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getStatement() { return statement; }
    public void setStatement(String statement) { this.statement = statement; }
    public String getJustification() { return justification; }
    public void setJustification(String justification) { this.justification = justification; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public List<AnswerOptionRecord> getOptions() { return options; }
    public void setOptions(List<AnswerOptionRecord> options) { this.options = options; }

    public void addOption(AnswerOptionRecord option) {
        options.add(option);
        option.setQuestionRecord(this);
    }
}

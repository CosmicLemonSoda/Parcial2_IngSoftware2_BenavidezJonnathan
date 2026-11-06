package domain.model;

import java.io.Serializable;

public class SubmitQuestionEvent implements Serializable {
    private String operationId;
    private String questionId;

    public SubmitQuestionEvent() {}

    public SubmitQuestionEvent(String operationId, String questionId) {
        this.operationId = operationId;
        this.questionId = questionId;
    }

    public String getOperationId() { return operationId; }
    public void setOperationId(String operationId) { this.operationId = operationId; }
    public String getQuestionId() { return questionId; }
    public void setQuestionId(String questionId) { this.questionId = questionId; }
}

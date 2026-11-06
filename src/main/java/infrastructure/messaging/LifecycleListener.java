package infrastructure.messaging;

import domain.model.Question;
import domain.model.QuestionRepositoryPort;
import domain.model.SubmitQuestionEvent;

// Listener desactivado temporalmente para modo de emergencia sin servidor RabbitMQ
public class LifecycleListener {

    private final QuestionRepositoryPort questionRepository;

    public LifecycleListener(QuestionRepositoryPort questionRepository) {
        this.questionRepository = questionRepository;
    }

    public void handleQuestionSubmit(SubmitQuestionEvent event) {
        Question question = questionRepository.findById(event.getQuestionId())
                .orElseThrow(() -> new IllegalArgumentException("Pregunta no encontrada: " + event.getQuestionId()));

        question.submitForReview();
        questionRepository.save(question);
    }
}

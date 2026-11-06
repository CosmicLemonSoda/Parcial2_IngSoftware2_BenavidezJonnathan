package domain.model;

import java.util.Optional;

public interface QuestionRepositoryPort {
    Question save(Question question);
    Optional<Question> findById(String id);
}

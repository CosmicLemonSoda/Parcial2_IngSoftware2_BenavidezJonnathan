package infrastructure.jpa;

import infrastructure.jpa.record.QuestionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionDataRepository extends JpaRepository<QuestionRecord, String> {
}

package infrastructure.jpa;

import domain.model.Question;
import domain.model.QuestionRepositoryPort;
import infrastructure.jpa.record.QuestionRecord;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class QuestionRepositoryJpaImpl implements QuestionRepositoryPort {

    private final QuestionDataRepository jpaRepository;
    private final QuestionDomainMapper mapper;

    public QuestionRepositoryJpaImpl(QuestionDataRepository jpaRepository, QuestionDomainMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Question save(Question question) {
        QuestionRecord record = mapper.toRecord(question);
        QuestionRecord saved = jpaRepository.save(record);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Question> findById(String id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }
}

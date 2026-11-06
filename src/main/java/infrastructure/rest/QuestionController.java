package infrastructure.rest;

import domain.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final RestTemplate restTemplate;
    private final QuestionRepositoryPort questionRepository;

    public QuestionController(RestTemplate restTemplate, QuestionRepositoryPort questionRepository) {
        this.restTemplate = restTemplate;
        this.questionRepository = questionRepository;
    }

    @PostMapping
    public ResponseEntity<?> createQuestion(@RequestBody CreateQuestionDTO dto) {
        String validationUrl = "http://localhost:8083/validate";
        ValidationResponseDTO response = restTemplate.postForObject(validationUrl, dto.getJustification(), ValidationResponseDTO.class);

        if (response == null || !response.isValid()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validacion fallida: la justificacion debe tener mas de 15 caracteres.");
        }

        Question question = new QuestionBuilder()
                .setId(UUID.randomUUID().toString())
                .setStatement(dto.getStatement())
                .setJustification(dto.getJustification())
                .addOption("1", dto.getOpt1(), dto.isOpt1Correct())
                .addOption("2", dto.getOpt2(), dto.isOpt2Correct())
                .addOption("3", dto.getOpt3(), dto.isOpt3Correct())
                .addOption("4", dto.getOpt4(), dto.isOpt4Correct())
                .build();

        questionRepository.save(question);
        return ResponseEntity.status(HttpStatus.CREATED).body(question);
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<?> submitForReview(@PathVariable String id) {
        String operationId = UUID.randomUUID().toString();
        
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pregunta no encontrada: " + id));

        // Transición de estado sin condicionales (Patrón State)
        question.submitForReview();
        questionRepository.save(question);

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(Map.of("operationId", operationId, "status", "SUBMITTED_FOR_REVIEW"));
    }
}

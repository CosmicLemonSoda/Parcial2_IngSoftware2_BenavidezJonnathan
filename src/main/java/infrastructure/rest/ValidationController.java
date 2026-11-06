package infrastructure.rest;

import domain.model.ValidationResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/validate")
public class ValidationController {

    @PostMapping
    public ResponseEntity<ValidationResponseDTO> validateJustification(@RequestBody String justification) {
        boolean valid = justification != null && justification.trim().length() > 15;
        return ResponseEntity.ok(new ValidationResponseDTO(valid));
    }
}

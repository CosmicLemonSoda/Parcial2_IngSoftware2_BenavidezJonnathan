package domain.model;

public class ValidationResponseDTO {
    private boolean valid;

    public ValidationResponseDTO() {}

    public ValidationResponseDTO(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() { return valid; }
    public void setValid(boolean valid) { this.valid = valid; }
}

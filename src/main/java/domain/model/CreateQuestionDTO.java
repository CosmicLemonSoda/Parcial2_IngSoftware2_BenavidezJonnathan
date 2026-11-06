package domain.model;

public class CreateQuestionDTO {
    private String statement;
    private String justification;
    private String opt1;
    private boolean opt1Correct;
    private String opt2;
    private boolean opt2Correct;
    private String opt3;
    private boolean opt3Correct;
    private String opt4;
    private boolean opt4Correct;

    public CreateQuestionDTO() {}

    public String getStatement() { return statement; }
    public void setStatement(String statement) { this.statement = statement; }
    public String getJustification() { return justification; }
    public void setJustification(String justification) { this.justification = justification; }
    public String getOpt1() { return opt1; }
    public void setOpt1(String opt1) { this.opt1 = opt1; }
    public boolean isOpt1Correct() { return opt1Correct; }
    public void setOpt1Correct(boolean opt1Correct) { this.opt1Correct = opt1Correct; }
    public String getOpt2() { return opt2; }
    public void setOpt2(String opt2) { this.opt2 = opt2; }
    public boolean isOpt2Correct() { return opt2Correct; }
    public void setOpt2Correct(boolean opt2Correct) { this.opt2Correct = opt2Correct; }
    public String getOpt3() { return opt3; }
    public void setOpt3(String opt3) { this.opt3 = opt3; }
    public boolean isOpt3Correct() { return opt3Correct; }
    public void setOpt3Correct(boolean opt3Correct) { this.opt3Correct = opt3Correct; }
    public String getOpt4() { return opt4; }
    public void setOpt4(String opt4) { this.opt4 = opt4; }
    public boolean isOpt4Correct() { return opt4Correct; }
    public void setOpt4Correct(boolean opt4Correct) { this.opt4Correct = opt4Correct; }
}

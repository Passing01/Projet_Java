import java.io.Serializable;

public class Reponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private Question question;
    private String reponse;
    private Participant participant;
    private Questionnaire questionnaire;

    public Reponse(Question question, String reponse, Participant participant, Questionnaire questionnaire) {
        this.question = question;
        this.reponse = reponse;
        this.participant = participant;
        this.questionnaire = questionnaire;
    }

    public Question getQuestion() {
        return question;
    }

    public String getReponse() {
        return reponse;
    }

    public Participant getParticipant() {
        return participant;
    }

    public Questionnaire getQuestionnaire() {
        return questionnaire;
    }
}

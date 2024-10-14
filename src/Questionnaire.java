import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Questionnaire implements Serializable {
    private static final long serialVersionUID = 1L;
    private String titre;
    private String description;
    private List<Question> questions;

    public Questionnaire(String titre, String description) {
        this.titre = titre;
        this.description = description;
        this.questions = new ArrayList<>();
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }
}

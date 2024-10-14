import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Question implements Serializable {
    private static final long serialVersionUID = 1L;
    private String texte;
    private TypeQuestion type;
    private List<String> options;

    public Question(String texte, TypeQuestion type) {
        this.texte = texte;
        this.type = type;
        this.options = new ArrayList<>();
    }

    public String getTexte() {
        return texte;
    }

    public TypeQuestion getType() {
        return type;
    }

    public List<String> getOptions() {
        return options;
    }

    public void addOption(String option) {
        options.add(option);
    }
}

enum TypeQuestion implements Serializable {
    CHOIX_MULTIPLE,
    TEXTE_LIBRE,
    REPONSE_NUMERIQUE
}


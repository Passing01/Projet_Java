import java.io.Serializable;

public class Participant implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nom;
    private String email;

    public Participant(String nom, String email) {
        this.nom = nom;
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }
}

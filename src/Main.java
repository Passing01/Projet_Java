import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Questionnaire> questionnaires = new ArrayList<>();
    private static List<Participant> participants = new ArrayList<>();
    private static List<Reponse> reponses = new ArrayList<>();

    public static void main(String[] args) {
        // Charger les questionnaires depuis le fichier
        loadQuestionnaires();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("1. Créer un questionnaire");
            System.out.println("2. Répondre à un questionnaire");
            System.out.println("3. Afficher les résultats");
            System.out.println("4. Quitter");
            System.out.print("Choisissez une option : ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    creerQuestionnaire(scanner);
                    break;
                case 2:
                    repondreQuestionnaire(scanner);
                    break;
                case 3:
                    afficherResultats();
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Option invalide.");
            }
        }

        // Sauvegarder les questionnaires dans le fichier
        saveQuestionnaires();

        scanner.close();
    }

    private static void creerQuestionnaire(Scanner scanner) {
        System.out.print("Entrez le titre du questionnaire : ");
        String titre = scanner.nextLine();
        System.out.print("Entrez la description du questionnaire : ");
        String description = scanner.nextLine();

        Questionnaire questionnaire = new Questionnaire(titre, description);
        boolean addMoreQuestions = true;

        while (addMoreQuestions) {
            System.out.print("Entrez le texte de la question : ");
            String texte = scanner.nextLine();
            System.out.print("Entrez le type de question (CHOIX_MULTIPLE, TEXTE_LIBRE, REPONSE_NUMERIQUE) : ");
            String typeStr = scanner.nextLine();
            TypeQuestion type = TypeQuestion.valueOf(typeStr);

            Question question = new Question(texte, type);

            if (type == TypeQuestion.CHOIX_MULTIPLE) {
                boolean addMoreOptions = true;
                while (addMoreOptions) {
                    System.out.print("Entrez une option (ou 'fin' pour terminer) : ");
                    String option = scanner.nextLine();
                    if (option.equalsIgnoreCase("fin")) {
                        addMoreOptions = false;
                    } else {
                        question.addOption(option);
                    }
                }
            }

            questionnaire.addQuestion(question);
            System.out.print("Voulez-vous ajouter une autre question ? (oui/non) : ");
            String response = scanner.nextLine();
            addMoreQuestions = response.equalsIgnoreCase("oui");
        }

        questionnaires.add(questionnaire);
        System.out.println("Questionnaire créé avec succès.");
    }

    private static void repondreQuestionnaire(Scanner scanner) {
        System.out.print("Entrez votre nom : ");
        String nom = scanner.nextLine();
        System.out.print("Entrez votre email : ");
        String email = scanner.nextLine();

        Participant participant = new Participant(nom, email);
        participants.add(participant);

        System.out.println("Questionnaires disponibles :");
        for (int i = 0; i < questionnaires.size(); i++) {
            System.out.println((i + 1) + ". " + questionnaires.get(i).getTitre());
        }

        System.out.print("Choisissez un questionnaire (numéro) : ");
        int questionnaireIndex = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Questionnaire questionnaire = questionnaires.get(questionnaireIndex - 1);

        for (Question question : questionnaire.getQuestions()) {
            System.out.println(question.getTexte());
            if (question.getType() == TypeQuestion.CHOIX_MULTIPLE) {
                for (int i = 0; i < question.getOptions().size(); i++) {
                    System.out.println((i + 1) + ". " + question.getOptions().get(i));
                }
                System.out.print("Choisissez une option (numéro) : ");
                int optionIndex = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                String reponse = question.getOptions().get(optionIndex - 1);
                reponses.add(new Reponse(question, reponse, participant, questionnaire));
            } else {
                System.out.print("Entrez votre réponse : ");
                String reponse = scanner.nextLine();
                reponses.add(new Reponse(question, reponse, participant, questionnaire));
            }
        }

        System.out.println("Réponses soumises avec succès.");
    }

    private static void afficherResultats() {
        System.out.println("Résultats des questionnaires :");
        for (Questionnaire questionnaire : questionnaires) {
            System.out.println("Questionnaire : " + questionnaire.getTitre());
            for (Question question : questionnaire.getQuestions()) {
                System.out.println("Question : " + question.getTexte());
                for (Reponse reponse : reponses) {
                    if (reponse.getQuestion().equals(question)) {
                        System.out.println("Réponse de " + reponse.getParticipant().getNom() + " : " + reponse.getReponse());
                    }
                }
            }
        }
    }

    private static void saveQuestionnaires() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("questionnaires.dat"))) {
            oos.writeObject(questionnaires);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadQuestionnaires() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("questionnaires.dat"))) {
            questionnaires = (List<Questionnaire>) ois.readObject();
        } catch (FileNotFoundException e) {
            // Si le fichier n'existe pas, on initialise une nouvelle liste
            questionnaires = new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}


import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class App {

    private static ArrayList<Task> userTaskList = new ArrayList<>();
    public static  Scanner in = new Scanner(System.in);
     
    public static void main(String[] args) throws Exception {
        clearScreen();
        String[] choices = {"1", "2", "3", "4"};
        String userChoice = "";
        
        System.out.println("TO-DO List");
        System.out.println("Entrez le nom de l'utilisateur : ");

        User user = User.builder()
                .firstname("John Doe")
                .username("johndoe123")
                .password("securepassword")
                .build();

        System.out.println(user);

        if(getUserTasks(user) != null) {
            userTaskList = getUserTasks(user);
        } else {
            userTaskList = null;
        }
        
        while (!"4".equals(userChoice)) {
            clearScreen();
            System.out.println("1. Liste tache\n2. Ajouter tache\n3. Supprimer tache\n4. Quitter");
            userChoice = in.nextLine();
            if (!Arrays.asList(choices).contains(userChoice)) {
                System.out.println("Erreur : entrez un chiffre entre 1 et 4");
            } else {
                switch (userChoice) {
                    case "1" -> {showTasks(getUserTasks(user)); System.out.println("Appuyez sur une touche pour continuer..."); in.nextLine();}
                    case "2" -> addTask(user);
                    case "3" -> deleteTask(userTaskList);
                }
            }
            
        }

        clearScreen();
        in.close();

    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }

    public static ArrayList<Task> getUserTasks(User user) {
    
        ArrayList<Task> userTasks = new ArrayList<>();

        for (Task task : userTaskList) {
            if (((Task) task).getUserId().equals(user.getId())) {
                userTasks.add(task);
            }
        }

        return userTasks;
    }

    public static void showTasks(ArrayList<Task> userTaskList) {
        int index = 0;
        App.clearScreen();
        System.out.println("=== TACHES ===\n");

        if (userTaskList.isEmpty()) {
            System.out.println("La liste des taches est vide !\n");
        } else {
            for (Task task : userTaskList) {

                System.out.println((index + 1 ) + ". " + ((Task) task).getTitle());
                System.out.println("Description : " + ((Task) task).getDescription());
                System.out.println("Done : " + ((Task) task).getDone() + "\n");

                index++;
        
            }
        }
        
    }

    public static void addTask(User thisUser) throws Exception {
            App.clearScreen();
            System.out.println("=== AJOUT TACHE ===");
            System.out.println("\nEntrez le titre de la nouvelle tache : ");
            String thisTaskTitle = in.nextLine();
            System.out.println("\nEntrez la description de la nouvelle tache : ");
            String thisTaskDescription = in.nextLine();
    
            Task task = new Task(thisUser, thisTaskTitle, thisTaskDescription);
            userTaskList.add(task); 
            System.out.println("\nTache ajoutée avec succès !");
            TimeUnit.SECONDS.sleep(1);
    }
    
    public static void deleteTask(ArrayList<Task> userTaskList) throws Exception {
        int indexTask = -1;

        // Vérifier si l'utilisateur a des tâches
        if (userTaskList.isEmpty()) {
            System.out.println("\nAucune tâche à supprimer !");
            TimeUnit.SECONDS.sleep(1);
            return;
        }

        while (indexTask < 1 || indexTask > userTaskList.size()) {
            try {
                App.clearScreen();
                showTasks(userTaskList);
                System.out.println("=== SUPPRESSION TACHE ===");
                System.out.println("\nEntrez l'index de la tâche à supprimer : ");
                indexTask = in.nextInt(); // Lire l'entrée

                // Nettoyer le buffer après nextInt()
                in.nextLine(); 

                if (indexTask >= 1 && indexTask <= userTaskList.size()) { // Correction de la condition
                    Task task = userTaskList.get(indexTask - 1);
                    userTaskList.remove(task);
                    System.out.println("\nLa tâche a été supprimée avec succès !");
                    break;
                } else {
                    System.out.println("\nErreur : entrez un nombre entre 1 et " + userTaskList.size());
                    indexTask = -1; // Réinitialisation pour relancer la boucle
                }
            } catch (InputMismatchException e) {
                System.out.println("\nErreur : entrez un nombre entier.");
                in.nextLine(); // Vider le buffer pour éviter une boucle infinie
                indexTask = -1;
            }

            TimeUnit.SECONDS.sleep(1);
        }
    }
    
}

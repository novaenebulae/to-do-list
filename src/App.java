import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class App {

    static DatabaseAccess dba = DatabaseAccess.getInstance();
    public static Scanner in = new Scanner(System.in);

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

        dba.addUser(user);

        System.out.println(user);

        dba.addTask(new TaskBuilder().setUser(user).setTitle("Task 1").setDescription("Description 1").build());
        dba.addTask(new TaskBuilder().setUser(user).setTitle("Task 2").setDescription("Description 2").build());
        dba.addTask(new TaskBuilder().setUser(user).setTitle("Task 3").setDescription("Description 3").build());

        DatedTask task4 = new DatedTask(user, null);
        task4.setTitle("Task 4");
        task4.setDescription("Description 4");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        task4.setDueDate(formatter.parse("01/01/2025"));
        dba.addTask(task4);

        while (!"4".equals(userChoice)) {
            clearScreen();
            System.out.println("=== TO-DO List de " + user.getFirstname() + " ===");
            System.out.println("\n1. Liste des tâches\n2. Ajouter tâche\n3. Supprimer tâche\n4. Quitter");
            userChoice = in.nextLine();

            if (!Arrays.asList(choices).contains(userChoice)) {
                System.out.println("Erreur : entrez un chiffre entre 1 et 4");
            } else {
                clearScreen();
                switch (userChoice) {
                    case "1" -> {
                        showTasks(user);
                        System.out.println("Appuyez sur une touche pour continuer...");
                        in.nextLine();
                    }
                    case "2" -> addTask(user);
                    case "3" -> deleteTask(user);
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
        return new ArrayList<>(dba.getUserTasks(user));
    }

    public static ArrayList<Task> filterUserTasks(ArrayList<Task> userTaskList, String param) {
        return switch (param) {
            case "all" -> userTaskList;
            case "notDone" -> userTaskList.stream()
                    .filter(task -> !task.getDone())
                    .collect(Collectors.toCollection(ArrayList::new));
            case "done" -> userTaskList.stream()
                    .filter(Task::getDone)
                    .collect(Collectors.toCollection(ArrayList::new));
            default -> throw new AssertionError();
        };
    }

    public static void showTasks(User user) {
        ArrayList<Task> userTaskList;
        int index = 0;
        String[] choices = {"1", "2", "3", "4"};

        System.out.println("=== TÂCHES ===");
        System.out.println("\n1. Toutes les tâches\n2. Tâches non réalisées\n3. Tâches réalisées\n4. Retour");
        String userChoice = in.nextLine();

        if (!Arrays.asList(choices).contains(userChoice)) {
            System.out.println("Erreur : entrez un chiffre entre 1 et 4");
            return;
        }

        switch (userChoice) {
            case "1" -> userTaskList = filterUserTasks(getUserTasks(user), "all");
            case "2" -> userTaskList = filterUserTasks(getUserTasks(user), "notDone");
            case "3" -> userTaskList = filterUserTasks(getUserTasks(user), "done");
            default -> {
                return;
            }
        }

        clearScreen();
        System.out.println("=== TÂCHES ===\n");

        if (userTaskList.isEmpty()) {
            System.out.println("La liste des tâches est vide !\n");
        } else {
            for (Task task : userTaskList) {
                System.out.println((index + 1) + ". " + task.getTitle());
                System.out.println("Description : " + task.getDescription());
                System.out.println("Done : " + task.getDone() + "\n");
                index++;
            }
        }
    }

    public static void addTask(User user) throws Exception {
        clearScreen();
        System.out.println("=== AJOUT TÂCHE ===");
        System.out.println("\nEntrez le titre de la nouvelle tâche : ");
        String title = in.nextLine();
        System.out.println("\nEntrez la description de la nouvelle tâche : ");
        String description = in.nextLine();

        Task task = new Task(user, title, description);
        dba.addTask(task);
        System.out.println("\nTâche ajoutée avec succès !");
        TimeUnit.SECONDS.sleep(1);
    }

    public static void deleteTask(User user) throws Exception {
        int indexTask = -1;
        ArrayList<Task> userTaskList = getUserTasks(user);

        if (userTaskList.isEmpty()) {
            System.out.println("\nAucune tâche à supprimer !");
            TimeUnit.SECONDS.sleep(1);
            return;
        }

        while (indexTask < 1 || indexTask > userTaskList.size()) {
            try {
                clearScreen();
                System.out.println("=== SUPPRESSION TÂCHE ===\n");
                System.out.println("Filtre d'affichage : \n");
                showTasks(user);
                System.out.println("\nEntrez l'index de la tâche à supprimer : ");
                indexTask = in.nextInt();
                in.nextLine(); // Nettoyer le buffer

                if (indexTask >= 1 && indexTask <= userTaskList.size()) {
                    Task task = userTaskList.get(indexTask - 1);
                    dba.deleteTask(task);
                    System.out.println("\nLa tâche a été supprimée avec succès !");
                    break;
                } else {
                    System.out.println("\nErreur : entrez un nombre entre 1 et " + userTaskList.size());
                    indexTask = -1;
                }
            } catch (InputMismatchException e) {
                System.out.println("\nErreur : entrez un nombre entier.");
                in.nextLine(); // Vider le buffer
                indexTask = -1;
            }
        }
        TimeUnit.SECONDS.sleep(1);
    }
}

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    
    // Instance unique (Singleton)
    private static DatabaseAccess instance;

    // Listes simulant une base de données
    private final ArrayList<User> users;
    private final ArrayList<Task> tasks;

    // Constructeur privé pour empêcher l'instanciation directe
    private DatabaseAccess() {
        users = new ArrayList<>();
        tasks = new ArrayList<>();
    }

    // Méthode pour obtenir l'instance unique
    public static DatabaseAccess getInstance() {
        if (instance == null) {
            instance = new DatabaseAccess();
        }
        return instance;
    }

    // Gestion des utilisateurs
    public void addUser(User user) {  
        users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }

    // Gestion des tâches
    public void addTask(Task task) {
        tasks.add(task);
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void deleteTask(Task task) {
        tasks.remove(task);
        System.out.println("\nLa tâche a été supprimée avec succès !");
    }
    
    public ArrayList<Task> getUserTasks(User user) {
        ArrayList<Task> userTasks = new ArrayList<>();

        for (Task task : getTasks()) {
            if (((Task) task).getUser().equals(user)) {
                userTasks.add(task);
            }
        }

        return userTasks;
    }

}

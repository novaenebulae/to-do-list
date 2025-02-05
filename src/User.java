import java.util.Objects;
import java.util.UUID;

public class User {
    private final String id;
    private String firstname;
    private String username;
    private String password; // Stockage sécurisé nécessaire en prod

    // Bloc d'initialisation pour générer un UUID automatiquement
    {
        this.id = UUID.randomUUID().toString();
    }

    // Constructeur privé pour forcer l'utilisation du Builder
    User(UserBuilder builder) {
        this.firstname = builder.firstname;
        this.username = builder.username;
        this.password = builder.password;
    }

    // Getters uniquement (évite de modifier l'utilisateur après sa création)
    public String getId() {
        return this.id;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
               Objects.equals(firstname, user.firstname) &&
               Objects.equals(username, user.username) &&
               Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, username, password);
    }

    @Override
    public String toString() {
        return "User{" +
            "id='" + id + '\'' +
            ", firstname='" + firstname + '\'' +
            ", username='" + username + '\'' +
            ", password='********'" + // Masquage du mot de passe
            '}';
    }

    // Méthode statique pour accéder au Builder
    public static UserBuilder builder() {
        return new UserBuilder();
    }
}

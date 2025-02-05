
import java.util.Objects;
import java.util.UUID;

public class Task {

    {
        this.id = UUID.randomUUID().toString(); 
    }
    
    protected final String id;
    protected final String userId;
    protected final User user;

    protected String title;
    protected String description;
    protected boolean done;
    
    public Task(User user) {
        this.user = user;
        this.userId = user.getId();
        this.done = false;
    }

    public Task(User user, String title) {
        this(user);
        this.title = title;
    }

    public Task(User user, String title, String description) {
        this(user, title);
        this.description = description;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getDone() {
        return this.done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getUserId() {
        return this.userId;
    }

    public Object getUser() {
        return this.user;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Task)) {
            return false;
        }
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(title, task.title) && Objects.equals(description, task.description) && done == task.done && Objects.equals(userId, task.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, done, userId);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", user='" + getUser().toString() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", done='" + getDone() + "'" +
            "}";
    }
    
        
}

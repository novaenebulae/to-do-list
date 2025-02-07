import java.util.Date;
import java.util.Objects;

public class DatedTask extends Task {
    private Date dueDate;

    public DatedTask(User user, Date dueDate) {
        super(user); // Appelle le constructeur de la classe parente Task
        this.dueDate = dueDate;
    }

    public DatedTask(User user, String title, Date dueDate) {
        super(user, title);
        this.dueDate = dueDate;
    }

    public DatedTask(User user, String title, String description, Date dueDate) {
        super(user, title, description);
        this.dueDate = dueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof DatedTask))
            return false;
        if (!super.equals(o))
            return false; // Vérifie d'abord l'égalité de la classe parente
        
        DatedTask that = (DatedTask) o;
        return Objects.equals(dueDate, that.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dueDate); // Ajout du hash de la classe parente
    }

    @Override
    public String toString() {
        return super.toString() + ", dueDate='" + dueDate + "'}";
    }
}

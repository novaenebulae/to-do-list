public class TaskBuilder {
    protected User user;
    protected String title;
    protected String description;
    protected boolean done;

    public TaskBuilder setUser(User user) {
        this.user = user;
        return this;
    }

    public TaskBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public TaskBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public TaskBuilder setDone(boolean done) {
        this.done = done;
        return this;
    }

    public Task build() {
        Task task = new Task(user);
        task.setTitle(this.title);
        task.setDescription(this.description);
        task.setDone(this.done);
        return task;
    }
}

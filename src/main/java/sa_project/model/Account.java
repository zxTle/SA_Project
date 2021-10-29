package sa_project.model;

public class Account {
    private String username;

    public Account(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return  "name=" + username;
    }
}

package bitcamp.myapp;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String email;
    private String password;
    private String tell;

    static List<User> users = new ArrayList<>();

    public User(String name, String email, String password, String tell) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.tell = tell;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getTell() {
        return tell;
    }
    public void setTell(String tell) {
        this.tell = tell;
    }

    public static void createUser(User user){
        users.add(user);
    }

    public static List<User> getUsers() {
        return users;
    }

    public static User getUserByIndex(int userNumber) {
        if (userNumber >= 0 && userNumber < users.size()) {
            return users.get(userNumber);
        }
        return null; // User not found
    }

    public static void deleteUser(int userNumber){
        users.remove(userNumber -1);
    }
}

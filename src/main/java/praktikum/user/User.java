package praktikum.user;

import java.time.LocalDateTime;
import java.util.Random;

public class User {

    private String email;
    private String password;
    private String name;

    public User(String email, String password, String name){
            this.email = email;
            this.password = password;
            this.name = name;
    }
    public static User randomUser(){
        return new User("email" + Math.random() + "@yandex.ru", "LkYThgf3211", "Michael");
    }

    public static User userWithoutEmail(){
        return new User(null, "LkYThgf3211", "Michael");
    }

    public static User userWithEmptyEmail(){
        return new User("", "LkYThgf3211", "Michael");
    }

    public static User userWithoutPassword(){
        return new User("email" + Math.random() + "@yandex.ru", null, "Michael");
    }
    public static User userWithEmptyPassword(){
        return new User("email" + Math.random() + "@yandex.ru", "", "Michael");
    }
    public static User userWithoutName(){
        return new User("email" + Math.random() + "@yandex.ru", "LkYThgf3211", null);
    }
    public static User userWithEmptyName(){
        return new User("email" + Math.random() + "@yandex.ru", "LkYThgf3211", "");
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}


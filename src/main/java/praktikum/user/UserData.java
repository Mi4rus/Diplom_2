package praktikum.user;

public class UserData {
    private final String email;
    private final String name;

    public UserData(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public static UserData fromChangeDataUser(User user){
        var data = new UserData(user.getEmail() + Math.random(), user.getName() + Math.random());
        return data;
    }

    public static UserData dataUser(User user){
        var data = new UserData(user.getEmail(), user.getName());
        return data;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
}

package praktikum.user;

public class UserCredentionals {
    private final String email;
    private final String password;

    public UserCredentionals(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static UserCredentionals fromUser(User user){
        var creds = new UserCredentionals(user.getEmail(), user.getPassword());
        return creds;
    }

    public static UserCredentionals fromNonExistentUser(User user){
        var creds = new UserCredentionals(user.getEmail() + Math.random(), user.getPassword() + Math.random());
        return creds;
    }


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

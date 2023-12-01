import java.util.ArrayList;

public class UserList {
	private static ArrayList<User> users = new ArrayList<>();

    public static void addUser(User user) {
        users.add(user);
    }

    public static ArrayList<User> getUserList() {
        return users;
    }
    
    public static void setUserList(ArrayList<User> userList) {
    	users = userList;
    }
}

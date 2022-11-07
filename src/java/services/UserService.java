package services;

import dataaccess.UserDB;
import java.util.List;
import models.User;

/**
 *
 * @author marce
 */
public class UserService {

    public void addUser(User user) throws Exception {
        UserDB userDB = new UserDB();
        userDB.insert(user);
    }

    public User get(String email) throws Exception   {
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        return user;
    }

    public List<User> getAll() throws Exception {
        UserDB userDB = new UserDB();
        List<User> users = userDB.getAll();
        return users;
    }

    public void updateUser(User user) throws Exception  {
        UserDB userDB = new UserDB();
        userDB.update(user);
    }

    public void deleteUser(String email) throws Exception   {
        User user = get(email);
        UserDB userDB = new UserDB();
        userDB.delete(user);
    }   
}
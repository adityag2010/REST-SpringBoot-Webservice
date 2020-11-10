package com.rest.webservices.restfulwebservices.dao;

import com.rest.webservices.restfulwebservices.exception.UserAlreadyExistsException;
import com.rest.webservices.restfulwebservices.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Repository
public class UserDaoService {
    private static List<User> users = new ArrayList<>();
    private static int userCount;

    static{
        users.add(new User(1, "Adam", new Date()));
        users.add(new User(2, "Eve", new Date()));
        users.add(new User(3, "Jack", new Date()));
        userCount = 3;
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User newUser) {
        // set id if null
        if (newUser.getId() == null) {
            newUser.setId(++userCount);
        } else { // check if user already exists
            for (User user : users) {
                if (user.getId().equals(newUser.getId())) {
                    throw new UserAlreadyExistsException("User Already Exists! id -> " + newUser.getId());
                }
            }
        }

        users.add(newUser);
        return newUser;
    }

    public User findById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public User deleteById(int id) {
        Iterator<User> iterator = users.iterator();
        while(iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId() == id) {
                iterator.remove();
                return user;
            }
        }
        return null;
    }
}

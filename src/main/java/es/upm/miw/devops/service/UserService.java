package es.upm.miw.devops.service;

import es.upm.miw.devops.data.UsersDatabase;
import es.upm.miw.devops.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class UserService {

    private final UsersDatabase usersDatabase;

    public UserService(UsersDatabase usersDatabase) {
        this.usersDatabase = usersDatabase;
    }

    public User read (long id){
        return usersDatabase.getUsers().stream().filter(user -> user.getId() == id).findFirst().
                orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found: " + id));
    }
}

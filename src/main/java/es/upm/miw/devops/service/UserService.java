package es.upm.miw.devops.service;

import es.upm.miw.devops.data.UsersDatabase;
import es.upm.miw.devops.model.User;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    public List<User> find(String city, String province, Boolean billable){
        return usersDatabase.getUsers().stream().
                filter(user -> city == null || city.equalsIgnoreCase(user.getCity())).
                filter(user -> province == null || province.equalsIgnoreCase(user.getProvince())).
                filter(user -> billable == null || user.isBillable(user) == billable).toList();
    }

    public void delete(long id) {
        read(id);
        usersDatabase.delete(id);
    }

    public void updateActive(long id) {
        User user = read(id);
        user.setActive(true);
    }
}

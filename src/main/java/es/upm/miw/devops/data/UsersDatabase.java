package es.upm.miw.devops.data;

import es.upm.miw.devops.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsersDatabase {
    private final List <User> users = new ArrayList<>();

    public UsersDatabase(){
        this.users.add(new User(
                1,
                "Yueqi",
                "Xia",
                "yueqi.xia@alumnos.upm.es",
                "12345678A",
                "Calle Gran Vía 25",
                "Madrid",
                "Madrid",
                "28013"
        ));
        this.users.add(new User(
                2,
                "Carlos",
                "García",
                "carlos.garcia@gmail.com",
                "23456789B",
                "Calle Alcalá 120",
                "Madrid",
                "Madrid",
                "28009"
        ));
        this.users.add(new User(
                3,
                "Lucía",
                "Martínez",
                "lucia.martinez@hotmail.com",
                "34567890C",
                "Carrer de Mallorca 45",
                "Barcelona",
                "Barcelona",
                "08029"
        ));
        this.users.add(new User(
                4,
                "Daniel",
                "López",
                "daniel.lopez@gmail.com",
                "45678901D",
                null,
                "Valencia",
                "Valencia",
                "46001"
        ));
        this.users.add(new User(
                5,
                "Sofía",
                "Rodríguez",
                "sofia.rodriguez@icloud.com",
                "56789012E",
                "Calle Colón 18",
                "Valencia",
                null,
                null
        ));
        this.users.add(new User(
                6,
                "Alejandro",
                "Fernández",
                null,
                "67890123F",
                "Calle Sierpes 30",
                "Sevilla",
                "Sevilla",
                "41004"
        ));
    }

    public List<User> getUsers(){
        return users;
    }
}

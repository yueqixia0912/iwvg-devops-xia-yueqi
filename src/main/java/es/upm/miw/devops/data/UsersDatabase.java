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
                "28013",
                false
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
                "28009",
                false
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
                "08029",
                false
        ));

        // Missing firstName
        this.users.add(new User(
                4,
                null,
                "Sánchez",
                "miguel.sanchez@gmail.com",
                "78901234G",
                "Calle Serrano 45",
                "Madrid",
                "Madrid",
                "28006",
                false
        ));

        // Missing familyName
        this.users.add(new User(
                5,
                "Laura",
                null,
                "laura.navarro@gmail.com",
                "89012345H",
                "Calle Valencia 32",
                "Barcelona",
                "Barcelona",
                "08015",
                false
        ));

        // Missing email
        this.users.add(new User(
                6,
                "Alejandro",
                "Fernández",
                null,
                "67890123F",
                "Calle Sierpes 30",
                "Sevilla",
                "Sevilla",
                "41004",
                false
        ));

        // Missing identity
        this.users.add(new User(
                7,
                "Elena",
                "Torres",
                "elena.torres@gmail.com",
                null,
                "Calle Colón 24",
                "Valencia",
                "Valencia",
                "46004",
                false
        ));

        // Missing address
        this.users.add(new User(
                8,
                "Daniel",
                "López",
                "daniel.lopez@gmail.com",
                "45678901D",
                null,
                "Valencia",
                "Valencia",
                "46001",
                false
        ));

        // Missing city
        this.users.add(new User(
                9,
                "Ana",
                "Vázquez",
                "ana.vazquez@gmail.com",
                "23456789L",
                "Calle Mallorca 15",
                null,
                "Barcelona",
                "08001",
                false
        ));

        // Missing province
        this.users.add(new User(
                10,
                "Diego",
                "Castro",
                "diego.castro@gmail.com",
                "34567890M",
                "Calle Princesa 10",
                "Madrid",
                null,
                "28008",
                false
        ));

        // Missing postalCode
        this.users.add(new User(
                11,
                "Marta",
                "Ortega",
                "marta.ortega@gmail.com",
                "45678901N",
                "Calle Alcalá 75",
                "Madrid",
                "Madrid",
                null,
                false
        ));

        this.users.add(new User(
                12,
                "Sofía",
                "Rodríguez",
                "sofia.rodriguez@icloud.com",
                "56789012E",
                "Calle Colón 18",
                "Valencia",
                null,
                null,
                false
        ));
    }

    public List<User> getUsers(){
        return users;
    }

    public void delete(long id) {
        users.removeIf(user -> user.getId() == id);
    }

    public void update(User updatedUser) {
        users.replaceAll(user ->
                user.getId() == updatedUser.getId()
                        ? updatedUser
                        : user);
    }
}

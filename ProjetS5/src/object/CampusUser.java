package object;

import utils.IdGenerator;
import utils.UsernameGenerator;

public class CampusUser extends User {

    public CampusUser(long id, String firstName, String lastName, String username) {
        super(id, firstName, lastName, username);
    }

    public CampusUser(String firstName, String lastName) {
        super(IdGenerator.idGenerator(firstName, lastName), firstName, lastName,
                UsernameGenerator.usernameGenerator(firstName, lastName));
    }
}
package nl.hsleiden.mock;

import nl.hsleiden.model.User;

import javax.persistence.Entity;
import java.util.HashSet;

@Entity
public class MockEmployeeAdministrator extends User {

    public MockEmployeeAdministrator() {
        this.setEmail("bashar@email.nl");
    }
}

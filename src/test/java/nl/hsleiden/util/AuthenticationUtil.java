package nl.hsleiden.util;

import nl.hsleiden.mock.MockUserAdministrator;
import nl.hsleiden.mock.MockUserEmployee;
import nl.hsleiden.model.User;
import org.springframework.util.Base64Utils;

public class AuthenticationUtil {

    private static User administrator = new MockUserAdministrator();
    private static User employee = new MockUserEmployee();

    public static String getAdministratorAuthentication() {
        return "Basic " + Base64Utils.encodeToString("administrator:password".getBytes());
    }

    public static String getEmployeeAuthentication() {
        return "Basic " + Base64Utils.encodeToString("employee:password".getBytes());
    }

    public static String getInstructorAuthentication() {
        return "Basic " + Base64Utils.encodeToString("instructor:password".getBytes());
    }
}

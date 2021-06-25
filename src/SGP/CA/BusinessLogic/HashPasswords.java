package SGP.CA.BusinessLogic;

public class HashPasswords {
    public String generateHashedPass(String pass) {
        return BCrypt.hashpw(pass, BCrypt.gensalt());
    }

    public boolean isValid(String clearTextPassword, String hashedPass) {
        return BCrypt.checkpw(clearTextPassword, hashedPass);
    }
}

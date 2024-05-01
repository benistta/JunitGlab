package org.example;//package org.example;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PasswordComply {
    private String passwordString;
    private final int minPasswordLength = 8;
    private final int maxPasswordLength = 12;

    static final String DB_URL = "jdbc:mysql://localhost/PERSCHOLAS";
    static final String USER = "root";
    static final String PASS = "Macedonio13";
    static final String QUERY = "{call getEmpName (?, ?)}";

    public PasswordComply(String verifyPassword) {
        this.passwordString = verifyPassword;
    }

    public boolean verifyPasswordLength() {
        return !passwordString.isEmpty() && passwordString.length() >= minPasswordLength && passwordString.length() <= maxPasswordLength;
    }

    public boolean verifyAlphaNumeric() {
        return passwordString.matches("[A-Za-z0-9]+");
    }

    public boolean hasAllowedSpecialCharacters() {
        return passwordString.matches(".*[!@#$%^&*()].*");
    }

    public boolean doesNotAlreadyExist() throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             CallableStatement stmt = conn.prepareCall(QUERY)) {
            stmt.setString(1, passwordString);
            stmt.registerOutParameter(2, java.sql.Types.VARCHAR);
            stmt.execute();
            String existingPassword = stmt.getString(2);
            return existingPassword == null;
        }
    }

    public boolean hasNoSpecialCharacters() {
        return !passwordString.matches(".*[{}<>].*");
    }

    public void setPassword(String givenPassword) {
        this.passwordString = givenPassword;
    }

    public boolean doesPasswordComply() {
        try {
            return verifyPasswordLength() && verifyAlphaNumeric() && hasAllowedSpecialCharacters() && hasNoSpecialCharacters() && doesNotAlreadyExist();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
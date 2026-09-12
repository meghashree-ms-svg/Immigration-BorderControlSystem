package repository;

import model.EmbassyOfficer;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmbassyOfficerRepository {

    // =========================================================
    // FIND OFFICER BY USERNAME AND PASSWORD
    // =========================================================

    public EmbassyOfficer findByUsernameAndPassword(
            String username,
            String password) {

        String sql = """
            SELECT *
            FROM embassy_officers
            WHERE BINARY username = ?
            AND BINARY password = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new EmbassyOfficer(
                        resultSet.getString("user_id"),
                        resultSet.getString("name"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("employee_id"),
                        resultSet.getString("department")
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Failed to login embassy officer!"
            );

            e.printStackTrace();
        }

        return null;
    }
}

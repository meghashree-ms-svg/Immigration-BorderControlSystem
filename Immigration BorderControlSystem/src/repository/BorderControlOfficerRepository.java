package repository;

import model.BorderControlOfficer;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BorderControlOfficerRepository {

    public BorderControlOfficer findByUsernameAndPassword(
            String username,
            String password) {

        String sql = """
                SELECT *
                FROM border_control_officers
                WHERE username = ?
                AND password = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new BorderControlOfficer(
                        resultSet.getString("user_id"),
                        resultSet.getString("name"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("employee_id"),
                        resultSet.getString("checkpoint")
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Failed to login Border Control Officer!"
            );

            e.printStackTrace();
        }

        return null;
    }
}

package repository;

import model.Applicant;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class ApplicantRepository {

    // Add Applicant to Database
    public void addApplicant(Applicant applicant) {

        String sql = """
                INSERT INTO applicants
                (user_id, name, username, password, passport_number,
                 nationality, age, visa_status)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, applicant.getUserId());
            statement.setString(2, applicant.getName());
            statement.setString(3, applicant.getUsername());
            statement.setString(4, applicant.getPassword());
            statement.setString(5, applicant.getPassportNumber());
            statement.setString(6, applicant.getNationality());
            statement.setInt(7, applicant.getAge());
            statement.setString(8, applicant.getVisaStatus());

            statement.executeUpdate();

        } catch (SQLException e) {

            System.out.println("Error while registering applicant.");
            e.printStackTrace();
        }
    }


    // Find Applicant by Username
    public Applicant findByUsername(String username) {

        String sql = """
                SELECT * FROM applicants
                WHERE username = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return extractApplicant(resultSet);
            }

        } catch (SQLException e) {

            System.out.println("Error while searching applicant.");
            e.printStackTrace();
        }

        return null;
    }


    // Find Applicant by Username and Password
    // Find Applicant by Username and Password
    public Applicant findByUsernameAndPassword(String username, String password) {

        String sql = """
            SELECT * FROM applicants
            WHERE BINARY username = ?
            AND BINARY password = ?
            """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return extractApplicant(resultSet);
            }

        } catch (SQLException e) {

            System.out.println("Error while logging in applicant.");
            e.printStackTrace();
        }

        return null;
    }


    // Find Applicant by User ID
    public Applicant findByUserId(String userId) {

        String sql = """
                SELECT * FROM applicants
                WHERE user_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, userId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return extractApplicant(resultSet);
            }

        } catch (SQLException e) {

            System.out.println("Error while searching applicant by ID.");
            e.printStackTrace();
        }

        return null;
    }


    // Get All Applicants
    public ArrayList<Applicant> getAllApplicants() {

        ArrayList<Applicant> applicants = new ArrayList<>();

        String sql = "SELECT * FROM applicants";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                applicants.add(extractApplicant(resultSet));
            }

        } catch (SQLException e) {

            System.out.println("Error while retrieving applicants.");
            e.printStackTrace();
        }

        return applicants;
    }



    // Convert Database Row into Applicant Object
    private Applicant extractApplicant(ResultSet resultSet)
            throws SQLException {

        return new Applicant(
                resultSet.getString("user_id"),
                resultSet.getString("name"),
                resultSet.getString("username"),
                resultSet.getString("password"),
                resultSet.getString("passport_number"),
                resultSet.getString("nationality"),
                resultSet.getInt("age"),
                resultSet.getString("visa_status")
        );
    }
}

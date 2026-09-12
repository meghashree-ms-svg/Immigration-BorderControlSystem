package repository;

import model.Visa;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VisaRepository {

    // =========================================================
    // ADD VISA
    // =========================================================

    public void addVisa(Visa visa) {

        String sql = """
                INSERT INTO visas
                (visa_id, user_id, visa_type, destination_country, purpose, status)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, visa.getVisaId());
            statement.setString(2, visa.getApplicantId());
            statement.setString(3, visa.getVisaType());
            statement.setString(4, visa.getDestinationCountry());
            statement.setString(5, visa.getPurpose());
            statement.setString(6, visa.getStatus());

            statement.executeUpdate();

        } catch (SQLException e) {

            System.out.println("Failed to add visa!");
            e.printStackTrace();
        }
    }


    // =========================================================
    // DISPLAY ALL VISAS
    // =========================================================

    public void displayAllVisas() {

        String sql = "SELECT * FROM visas";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            System.out.println("\n========== ALL VISAS ==========");

            while (resultSet.next()) {

                System.out.println(
                        "Visa ID: "
                                + resultSet.getString("visa_id")
                );

                System.out.println(
                        "Applicant ID: "
                                + resultSet.getString("user_id")
                );

                System.out.println(
                        "Visa Type: "
                                + resultSet.getString("visa_type")
                );

                System.out.println(
                        "Destination Country: "
                                + resultSet.getString("destination_country")
                );

                System.out.println(
                        "Purpose: "
                                + resultSet.getString("purpose")
                );

                System.out.println(
                        "Status: "
                                + resultSet.getString("status")
                );

                System.out.println(
                        "-------------------------------"
                );
            }

        } catch (SQLException e) {

            System.out.println("Failed to display visas!");
            e.printStackTrace();
        }
    }


    // =========================================================
    // FIND VISA BY VISA ID
    // =========================================================

    public Visa findByVisaId(String visaId) {

        String sql =
                "SELECT * FROM visas WHERE visa_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, visaId);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                return new Visa(
                        resultSet.getString("visa_id"),
                        resultSet.getString("user_id"),
                        resultSet.getString("visa_type"),
                        resultSet.getString("destination_country"),
                        resultSet.getString("purpose"),
                        resultSet.getString("status")
                );
            }

        } catch (SQLException e) {

            System.out.println("Failed to find visa!");
            e.printStackTrace();
        }

        return null;
    }


    // =========================================================
    // FIND VISA BY APPLICANT ID
    // =========================================================

    public Visa findByApplicantId(String applicantId) {

        String sql =
                "SELECT * FROM visas WHERE user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, applicantId);

            ResultSet resultSet =
                    statement.executeQuery();

            if (resultSet.next()) {

                return new Visa(
                        resultSet.getString("visa_id"),
                        resultSet.getString("user_id"),
                        resultSet.getString("visa_type"),
                        resultSet.getString("destination_country"),
                        resultSet.getString("purpose"),
                        resultSet.getString("status")
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Failed to fetch visa status!"
            );

            e.printStackTrace();
        }

        return null;
    }


    // =========================================================
    // UPDATE VISA STATUS
    // =========================================================

    public void updateVisaStatus(
            String visaId,
            String status) {

        String sql =
                "UPDATE visas SET status = ? WHERE visa_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, status);
            statement.setString(2, visaId);

            int rowsUpdated =
                    statement.executeUpdate();

            if (rowsUpdated > 0) {

                System.out.println(
                        "Visa status updated successfully!"
                );

            } else {

                System.out.println(
                        "Visa not found!"
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Failed to update visa status!"
            );

            e.printStackTrace();
        }
    }
}
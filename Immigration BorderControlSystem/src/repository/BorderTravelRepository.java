package repository;

import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BorderTravelRepository {

    public boolean hasActiveEntry(String applicantId, String visaId) {

        String sql = """
                SELECT record_id
                FROM border_travel_records
                WHERE applicant_id = ?
                AND visa_id = ?
                AND status = 'INSIDE'
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, applicantId);
            statement.setString(2, visaId);

            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {

            System.out.println("Failed to check traveller entry!");
            e.printStackTrace();
        }

        return false;
    }

    public void recordEntry(String applicantId,
                            String visaId,
                            String passportNumber) {

        String sql = """
                INSERT INTO border_travel_records
                (applicant_id, visa_id, passport_number,
                 entry_date, status)
                VALUES (?, ?, ?, NOW(), 'INSIDE')
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, applicantId);
            statement.setString(2, visaId);
            statement.setString(3, passportNumber);

            statement.executeUpdate();

            System.out.println("\nTraveller entry recorded successfully!");

        } catch (SQLException e) {

            System.out.println("Failed to record traveller entry!");
            e.printStackTrace();
        }
    }

    public boolean recordExit(String applicantId, String visaId) {

        String sql = """
                UPDATE border_travel_records
                SET exit_date = NOW(),
                    status = 'OUTSIDE'
                WHERE applicant_id = ?
                AND visa_id = ?
                AND status = 'INSIDE'
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, applicantId);
            statement.setString(2, visaId);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (SQLException e) {

            System.out.println("Failed to record traveller exit!");
            e.printStackTrace();
        }

        return false;
    }

    public void displayTravelRecords() {

        String sql = """
                SELECT record_id,
                       applicant_id,
                       visa_id,
                       passport_number,
                       entry_date,
                       exit_date,
                       status
                FROM border_travel_records
                ORDER BY record_id DESC
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            System.out.println("\n========== TRAVEL RECORDS ==========");

            boolean found = false;

            while (resultSet.next()) {

                found = true;

                System.out.println("Record ID       : "
                        + resultSet.getInt("record_id"));

                System.out.println("Applicant ID    : "
                        + resultSet.getString("applicant_id"));

                System.out.println("Visa ID         : "
                        + resultSet.getString("visa_id"));

                System.out.println("Passport Number : "
                        + resultSet.getString("passport_number"));

                System.out.println("Entry Date      : "
                        + resultSet.getTimestamp("entry_date"));

                System.out.println("Exit Date       : "
                        + resultSet.getTimestamp("exit_date"));

                System.out.println("Status          : "
                        + resultSet.getString("status"));

                System.out.println("------------------------------------");
            }

            if (!found) {
                System.out.println("No travel records found.");
            }

        } catch (SQLException e) {

            System.out.println("Failed to display travel records!");
            e.printStackTrace();
        }
    }
}

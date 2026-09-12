package repository;

import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class TravellerMovementRepository {

    public void recordEntry(
            String movementId,
            String visaId,
            String applicantId,
            String checkpoint) {

        String sql = """
                INSERT INTO traveller_movements
                (movement_id, visa_id, applicant_id,
                 entry_time, checkpoint)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, movementId);
            statement.setString(2, visaId);
            statement.setString(3, applicantId);
            statement.setTimestamp(
                    4,
                    new Timestamp(System.currentTimeMillis())
            );
            statement.setString(5, checkpoint);

            statement.executeUpdate();

            System.out.println(
                    "Traveller entry recorded successfully!"
            );

        } catch (SQLException e) {

            System.out.println(
                    "Failed to record traveller entry!"
            );

            e.printStackTrace();
        }
    }


    public void recordExit(String movementId) {

        String sql = """
                UPDATE traveller_movements
                SET exit_time = ?
                WHERE movement_id = ?
                AND exit_time IS NULL
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setTimestamp(
                    1,
                    new Timestamp(System.currentTimeMillis())
            );

            statement.setString(2, movementId);

            int rows =
                    statement.executeUpdate();

            if (rows > 0) {

                System.out.println(
                        "Traveller exit recorded successfully!"
                );

            } else {

                System.out.println(
                        "No active entry found for this movement ID."
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Failed to record traveller exit!"
            );

            e.printStackTrace();
        }
    }
}

package repository;

import model.VisaDocument;
import util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VisaDocumentRepository {

    // ============================================================
    // ADD DOCUMENT
    // ============================================================

    public void addDocument(VisaDocument document) {

        String sql = """
                INSERT INTO visa_documents
                (visa_id, applicant_id, document_type,
                 document_url, verification_status, requirement_type)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, document.getVisaId());
            statement.setString(2, document.getApplicantId());
            statement.setString(3, document.getDocumentType());
            statement.setString(4, document.getDocumentUrl());
            statement.setString(5, document.getVerificationStatus());
            statement.setString(6, document.getRequirementType());

            statement.executeUpdate();

        } catch (SQLException e) {

            System.out.println("Failed to save document!");
            e.printStackTrace();
        }
    }

    // ============================================================
    // DISPLAY DOCUMENTS
    // ============================================================

    public void displayDocumentsByVisaId(String visaId) {

        String sql = """
                SELECT *
                FROM visa_documents
                WHERE visa_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, visaId);

            ResultSet resultSet = statement.executeQuery();

            boolean found = false;

            System.out.println("\n========== UPLOADED DOCUMENTS ==========");

            while (resultSet.next()) {

                found = true;

                System.out.println(
                        "Document ID       : "
                                + resultSet.getInt("document_id"));

                System.out.println(
                        "Document Type     : "
                                + resultSet.getString("document_type"));

                System.out.println(
                        "Requirement       : "
                                + resultSet.getString("requirement_type"));

                System.out.println(
                        "Cloudinary URL    : "
                                + resultSet.getString("document_url"));

                System.out.println(
                        "Verification      : "
                                + resultSet.getString("verification_status"));

                System.out.println("-----------------------------------------");
            }

            if (!found) {
                System.out.println("No documents found.");
            }

        } catch (SQLException e) {

            System.out.println("Failed to fetch documents!");
            e.printStackTrace();
        }
    }

    // ============================================================
    // COUNT ALL DOCUMENTS
    // ============================================================

    public int countDocuments(String visaId) {

        String sql = """
                SELECT COUNT(*)
                FROM visa_documents
                WHERE visa_id = ?
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, visaId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {

            System.out.println("Failed to count documents!");
            e.printStackTrace();
        }

        return 0;
    }

    // ============================================================
    // COUNT PENDING DOCUMENTS
    // ============================================================

    public int countPendingDocuments(String visaId) {

        String sql = """
                SELECT COUNT(*)
                FROM visa_documents
                WHERE visa_id = ?
                AND verification_status = 'PENDING'
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, visaId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {

            System.out.println("Failed to check document status!");
            e.printStackTrace();
        }

        return 0;
    }

    // ============================================================
    // COUNT REJECTED DOCUMENTS
    // ============================================================

    public int countRejectedDocuments(String visaId) {

        String sql = """
                SELECT COUNT(*)
                FROM visa_documents
                WHERE visa_id = ?
                AND verification_status = 'REJECTED'
                """;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, visaId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }

        } catch (SQLException e) {

            System.out.println("Failed to check rejected documents!");
            e.printStackTrace();
        }

        return 0;
    }
}

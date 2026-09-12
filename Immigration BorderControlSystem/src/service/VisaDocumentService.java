package service;

import model.VisaDocument;
import repository.VisaDocumentRepository;

public class VisaDocumentService {

    private final VisaDocumentRepository repository;

    public VisaDocumentService() {
        repository = new VisaDocumentRepository();
    }

    // ============================================================
    // SAVE DOCUMENT
    // ============================================================

    public boolean saveDocument(String visaId,
                                String applicantId,
                                String documentType,
                                String documentUrl,
                                String requirementType) {

        if (visaId == null || visaId.trim().isEmpty()) {
            System.out.println("Visa ID cannot be empty!");
            return false;
        }

        if (applicantId == null || applicantId.trim().isEmpty()) {
            System.out.println("Applicant ID cannot be empty!");
            return false;
        }

        if (documentType == null || documentType.trim().isEmpty()) {
            System.out.println("Document type cannot be empty!");
            return false;
        }

        if (documentUrl == null || documentUrl.trim().isEmpty()) {
            System.out.println("Document URL cannot be empty!");
            return false;
        }

        if (requirementType == null || requirementType.trim().isEmpty()) {
            System.out.println("Requirement type cannot be empty!");
            return false;
        }

        /*
         * Document is automatically marked VERIFIED
         * after successful Cloudinary upload and database save.
         */
        VisaDocument document =
                new VisaDocument(
                        0,
                        visaId,
                        applicantId,
                        documentType,
                        documentUrl,
                        "VERIFIED",
                        requirementType
                );

        repository.addDocument(document);

        return true;
    }

    // ============================================================
    // DISPLAY DOCUMENTS
    // ============================================================

    public void displayDocuments(String visaId) {

        repository.displayDocumentsByVisaId(visaId);
    }

    // ============================================================
    // DOCUMENT COUNT
    // ============================================================

    public int getDocumentCount(String visaId) {

        return repository.countDocuments(visaId);
    }

    // ============================================================
    // PENDING COUNT
    // ============================================================

    public int getPendingDocumentCount(String visaId) {

        return repository.countPendingDocuments(visaId);
    }

    // ============================================================
    // REJECTED COUNT
    // ============================================================

    public int getRejectedDocumentCount(String visaId) {

        return repository.countRejectedDocuments(visaId);
    }

    // ============================================================
    // CHECK PENDING
    // ============================================================

    public boolean hasPendingDocuments(String visaId) {

        return getPendingDocumentCount(visaId) > 0;
    }
}
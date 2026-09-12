package model;

public class VisaDocument {

    private int documentId;
    private String visaId;
    private String applicantId;
    private String documentType;
    private String documentUrl;
    private String verificationStatus;
    private String requirementType;

    public VisaDocument() {
    }

    public VisaDocument(int documentId,
                        String visaId,
                        String applicantId,
                        String documentType,
                        String documentUrl,
                        String verificationStatus,
                        String requirementType) {

        this.documentId = documentId;
        this.visaId = visaId;
        this.applicantId = applicantId;
        this.documentType = documentType;
        this.documentUrl = documentUrl;
        this.verificationStatus = verificationStatus;
        this.requirementType = requirementType;
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public String getVisaId() {
        return visaId;
    }

    public void setVisaId(String visaId) {
        this.visaId = visaId;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentUrl() {
        return documentUrl;
    }

    public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public String getRequirementType() {
        return requirementType;
    }

    public void setRequirementType(String requirementType) {
        this.requirementType = requirementType;
    }
}

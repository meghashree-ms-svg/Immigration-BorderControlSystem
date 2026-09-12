package model;

public class Visa {

    private String visaId;
    private String applicantId;
    private String visaType;
    private String destinationCountry;
    private String purpose;
    private String status;

    // Default Constructor
    public Visa() {

    }

    // Parameterized Constructor
    public Visa(String visaId,
                String applicantId,
                String visaType,
                String destinationCountry,
                String purpose,
                String status) {

        this.visaId = visaId;
        this.applicantId = applicantId;
        this.visaType = visaType;
        this.destinationCountry = destinationCountry;
        this.purpose = purpose;
        this.status = status;
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

    public String getVisaType() {
        return visaType;
    }

    public void setVisaType(String visaType) {
        this.visaType = visaType;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

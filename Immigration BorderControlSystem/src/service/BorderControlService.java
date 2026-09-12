package service;

import model.Visa;
import repository.TravellerMovementRepository;

public class BorderControlService {

    private final VisaService visaService;
    private final TravellerMovementRepository movementRepository;

    public BorderControlService() {

        visaService = new VisaService();
        movementRepository =
                new TravellerMovementRepository();
    }


    // =========================================================
    // CHECK VISA BEFORE ENTRY
    // =========================================================

    public boolean isVisaApproved(String visaId) {

        Visa visa = visaService.getVisaById(visaId);

        if (visa == null) {

            System.out.println(
                    "Visa application not found!"
            );

            return false;
        }

        if (!visa.getStatus().equalsIgnoreCase("Approved")) {

            System.out.println(
                    "Traveller cannot enter."
            );

            System.out.println(
                    "Visa Status : " + visa.getStatus()
            );

            return false;
        }

        return true;
    }


    // =========================================================
    // RECORD ENTRY
    // =========================================================

    public void recordEntry(
            String visaId,
            String checkpoint) {

        Visa visa =
                visaService.getVisaById(visaId);

        if (visa == null) {

            System.out.println(
                    "Visa not found!"
            );

            return;
        }

        if (!visa.getStatus().equalsIgnoreCase("Approved")) {

            System.out.println(
                    "Entry denied! Visa is not approved."
            );

            return;
        }

        String movementId =
                "MOV" +
                        (System.currentTimeMillis() % 100000);

        movementRepository.recordEntry(
                movementId,
                visa.getVisaId(),
                visa.getApplicantId(),
                checkpoint
        );

        System.out.println(
                "Movement ID : " + movementId
        );
    }


    // =========================================================
    // RECORD EXIT
    // =========================================================

    public void recordExit(String movementId) {

        if (movementId == null ||
                movementId.trim().isEmpty()) {

            System.out.println(
                    "Movement ID cannot be empty!"
            );

            return;
        }

        movementRepository.recordExit(
                movementId
        );
    }
}

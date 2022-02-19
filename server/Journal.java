package src.server;

import java.util.ArrayList;

public class Journal {
    private String patient; //ändra typ till Person?

    public Journal(String patient){
        this.patient = patient;
        //skapa fil med patientens namn
    }

    public void addRecord(String doctor, String nurse, String division, String medicalData) {
        //skriv till patientens fil
        //tex datum och tid:division:doctor:nurse:medicalData
    }

    public void removeRecord() {
        //hur specifisera vilken man vill ta bort? numrera dom?
    }

    public void printRecords() {
        //bara printa innehållet i filen
    }

}
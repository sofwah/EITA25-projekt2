package server;

import java.util.HashSet;

public class Nurse extends Person {
    private HashSet<Patient> patients;
    private String division;

    public Nurse(String name, String division) {
        super(name);
        patients = new HashSet<>();
        this.division = division;
    }

    public HashSet<Patient> getPatients() {
        return patients;
    }

    public String getDivision() {
        return division;
    }
}

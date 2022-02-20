package src.server;

import java.util.HashSet;

public class Doctor extends Person {
    private HashSet<Patient> patients;
    private String division;

    public Doctor(String name) {
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

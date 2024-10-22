package com.example.licenseactivity;

public class MedicalRecord {
    private int id;
    private String spital;
    private String departament;
    private String doctor;

    public MedicalRecord(int id, String spital, String departament, String doctor) {
        this.id = id;
        this.spital = spital;
        this.departament = departament;
        this.doctor = doctor;
    }

    public int getId() {
        return id;
    }

    public String getSpital() {
        return spital;
    }

    public String getDepartament() {
        return departament;
    }

    public String getDoctor() {
        return doctor;
    }
}


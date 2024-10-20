package com.example.appointment_management.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime appointmentDate;
    private String branchOfMedicine;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getBranchOfMedicine() {
        return branchOfMedicine;
    }

    public void setBranchOfMedicine(String branchOfMedicine) {
        this.branchOfMedicine = branchOfMedicine;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Appointment() {
    }

    public Appointment(Long id, LocalDateTime appointmentDate, String branchOfMedicine, User user) {
        this.id = id;
        this.appointmentDate = appointmentDate;
        this.branchOfMedicine = branchOfMedicine;
        this.user = user;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", appointmentDate=" + appointmentDate +
                ", branchOfMedicine='" + branchOfMedicine + '\'' +
                ", user=" + user +
                '}';
    }
}

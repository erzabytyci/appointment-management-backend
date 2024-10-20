package com.example.appointment_management.repository;

import com.example.appointment_management.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByAppointmentDateAndBranchOfMedicine(LocalDateTime date, String branch);

    List<Appointment> findByAppointmentDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Appointment> findByUserId(Long userId);

    List<Appointment> findByBranchOfMedicine(String branch);
}

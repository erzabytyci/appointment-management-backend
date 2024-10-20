package com.example.appointment_management.service;

import com.example.appointment_management.model.Appointment;
import com.example.appointment_management.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Appointment> getAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment saveAppointment(Appointment appointment) {
        List<Appointment> existingAppointments = appointmentRepository
                .findByAppointmentDateAndBranchOfMedicine(appointment.getAppointmentDate(), appointment.getBranchOfMedicine());

        if (!existingAppointments.isEmpty()) {
            throw new IllegalArgumentException("Appointment slot is already taken.");
        }

        return appointmentRepository.save(appointment);
    }

    public List<String> getAvailableSlots(LocalDate date) {
        List<String> allSlots = List.of(
                "08:00-09:00", "09:00-10:00", "10:00-11:00",
                "11:00-12:00", "12:00-13:00", "14:00-15:00"
        );

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        List<Appointment> bookedAppointments =
                appointmentRepository.findByAppointmentDateBetween(startOfDay, endOfDay);

        List<String> bookedSlots = bookedAppointments.stream()
                .map(appointment -> appointment.getAppointmentDate().toLocalTime().toString())
                .toList();

        return allSlots.stream()
                .filter(slot -> !bookedSlots.contains(slot.split("-")[0]))
                .toList();
    }


    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    public List<Appointment> getAppointmentsWithin24Hours() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime next24Hours = now.plusHours(24);
        return appointmentRepository.findByAppointmentDateBetween(now, next24Hours);
    }

    public List<Appointment> getAppointmentHistory(Long userId) {
        return appointmentRepository.findByUserId(userId);
    }

    public List<Appointment> getAppointmentsByBranch(String branch) {
        return appointmentRepository.findByBranchOfMedicine(branch);
    }
}

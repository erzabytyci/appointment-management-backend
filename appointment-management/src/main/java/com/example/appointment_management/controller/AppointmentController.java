package com.example.appointment_management.controller;

import com.example.appointment_management.model.Appointment;
import com.example.appointment_management.repository.AppointmentRepository;
import com.example.appointment_management.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAppointments();
    }

    @PostMapping
    public Appointment createAppointment(@RequestBody Appointment appointment) {
        if (appointment.getAppointmentDate() == null ||
                appointment.getBranchOfMedicine() == null ||
                appointment.getUser() == null) {
            throw new IllegalArgumentException("Appointment date, branch of medicine, and user must not be null");
        }

        List<Appointment> existingAppointments =
                appointmentRepository.findByAppointmentDateAndBranchOfMedicine(
                        appointment.getAppointmentDate(),
                        appointment.getBranchOfMedicine()
                );

        if (!existingAppointments.isEmpty()) {
            throw new IllegalStateException("This time slot is already booked.");
        }

        return appointmentService.saveAppointment(appointment);
    }


    @DeleteMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return "Appointment deleted successfully";
    }

    @GetMapping("/available")
    public List<String> getAvailableSlots(@RequestParam String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        return appointmentService.getAvailableSlots(parsedDate);
    }

    @GetMapping("/history/{userId}")
    public List<Appointment> getUserAppointmentHistory(@PathVariable Long userId) {
        return appointmentService.getAppointmentHistory(userId);
    }

    @GetMapping("/upcoming")
    public List<Appointment> getUpcomingAppointments() {
        return appointmentService.getAppointmentsWithin24Hours();
    }

    @GetMapping("/branch")
    public List<Appointment> getAppointmentsByBranch(@RequestParam String branch) {
        return appointmentService.getAppointmentsByBranch(branch);
    }
}
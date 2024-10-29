package com.example.appointment_management.controller;

import com.example.appointment_management.model.Appointment;
import com.example.appointment_management.model.User;
import com.example.appointment_management.repository.AppointmentRepository;
import com.example.appointment_management.repository.UserRepository;
import com.example.appointment_management.service.AppointmentService;
import com.example.appointment_management.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private UserRepository userRepository;
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAppointments();
    }

    @PostMapping
    public Appointment createAppointment(@RequestBody Appointment appointment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        appointment.setUser(user);

        if (appointment.getAppointmentDate() == null || appointment.getBranchOfMedicine() == null) {
            throw new IllegalArgumentException("Appointment date and branch of medicine must not be null");
        }

        List<Appointment> existingAppointments = appointmentRepository.findByAppointmentDateAndBranchOfMedicine(
                appointment.getAppointmentDate(), appointment.getBranchOfMedicine());

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

    @GetMapping("/history")
    public List<Appointment> getUserAppointmentHistory() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Long userId = userDetails.getId();

        return appointmentService.getAppointmentHistory(userId);
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
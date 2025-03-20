package com.example.NetUp.appointment.web;

import com.example.NetUp.appointment.dtos.AppointmentDTOReq;
import com.example.NetUp.appointment.dtos.AppointmentDTORes;
import com.example.NetUp.appointment.entities.Appointment;
import com.example.NetUp.appointment.mapper.AppointmentMapper;
import com.example.NetUp.appointment.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @PostMapping
    public ResponseEntity<AppointmentDTORes> createAppointment(@Valid @RequestBody AppointmentDTOReq appointmentDTOReq) {
        Appointment appointment = appointmentService.createAppointment(appointmentDTOReq);
        return ResponseEntity.ok(appointmentMapper.toDto(appointment));
    }

    @PostMapping("/{appointmentId}/join/{userId}")
    public ResponseEntity<AppointmentDTORes> joinAppointment(
            @PathVariable Long appointmentId,
            @PathVariable Long userId) {
        Appointment appointment = appointmentService.joinAppointment(appointmentId, userId);
        return ResponseEntity.ok(appointmentMapper.toDto(appointment));
    }

    @PostMapping("/{appointmentId}/approve/{userId}")
    public ResponseEntity<AppointmentDTORes> approveUser(
            @PathVariable Long appointmentId,
            @PathVariable Long userId,
            @RequestParam Long creatorId) {
        Appointment appointment = appointmentService.approveUser(appointmentId, userId, creatorId);
        return ResponseEntity.ok(appointmentMapper.toDto(appointment));
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDTORes>> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        List<AppointmentDTORes> appointmentDTORes = appointments.stream()
                .map(appointmentMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(appointmentDTORes);
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<AppointmentDTORes> getAppointmentById(@PathVariable Long appointmentId) {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        return ResponseEntity.ok(appointmentMapper.toDto(appointment));
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long appointmentId) {
        appointmentService.deleteAppointment(appointmentId);
        return ResponseEntity.noContent().build();
    }
}
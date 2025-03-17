package com.example.NetUp.appointment.service;

import com.example.NetUp.appointment.dtos.AppointmentDTOReq;
import com.example.NetUp.appointment.entities.Appointment;
import com.example.NetUp.appointment.mapper.AppointmentMapper;
import com.example.NetUp.appointment.repository.AppointmentRepository;
import com.example.NetUp.user.entities.User;
import com.example.NetUp.user.repository.UserRepository;
import com.example.NetUp.user_appointment.AppointmentStatus;
import com.example.NetUp.user_appointment.entites.UserAppointment;
import com.example.NetUp.user_appointment.repository.UserAppointmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppointmentMapper appointmentMapper;

    @Autowired
    private UserAppointmentRepository userAppointmentRepository;

    @Override
    @Transactional
    public Appointment createAppointment(AppointmentDTOReq appointmentDTOReq) {
        User creator = userRepository.findById(appointmentDTOReq.creatorId())
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        Appointment appointment = appointmentMapper.toEntity(appointmentDTOReq);

        appointment.setCreator(creator);

        UserAppointment userAppointment = new UserAppointment();
        userAppointment.setUser(creator);
        userAppointment.setAppointment(appointment);
        userAppointment.setStatus(AppointmentStatus.APPROVED);

        appointment.setUserAppointments(
                Set.of(userAppointment));

        log.info("Appointment en cours + {}", appointment);

        return appointmentRepository.save(appointment);
    }

    @Override
    @Transactional
    public Appointment joinAppointment(Long appointmentId, Long userId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean alreadyJoined = appointment.getUserAppointments()
                .stream()
                .anyMatch(ua -> ua.getUser().getId().equals(userId));

        if (!alreadyJoined) {
            UserAppointment userAppointment = new UserAppointment();
            userAppointment.setUser(user);
            userAppointment.setAppointment(appointment);
            userAppointment.setStatus(AppointmentStatus.PENDING);

            appointment.addUserAppointment(userAppointment);

            return appointmentRepository.save(appointment);
        }

        return appointment;
    }

    @Override
    @Transactional
    public Appointment approveUser(Long appointmentId, Long userId, Long creatorId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (!appointment.getCreator().getId().equals(creatorId)) {
            throw new RuntimeException("Only creator can approve participants");
        }

        appointment.getUserAppointments()
                .stream()
                .filter(ua -> ua.getUser().getId().equals(userId))
                .findFirst()
                .ifPresent(ua -> ua.setStatus(AppointmentStatus.APPROVED));

        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getAllAppointments() {
        log.info("Retrieving all appointments");
        return appointmentRepository.findAll();
    }

    @Override
    public Appointment getAppointmentById(Long appointmentId) {
        log.info("Retrieving appointment with id: {}", appointmentId);
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found with id: " + appointmentId));
    }
}
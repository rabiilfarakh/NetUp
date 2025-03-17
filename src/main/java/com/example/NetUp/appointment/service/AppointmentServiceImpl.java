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

import java.util.HashSet;
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

    private UserAppointmentRepository userAppointmentRepository;

    @Override
    @Transactional
    public Appointment createAppointment(AppointmentDTOReq appointmentDTOReq) {
        User creator = userRepository.findById(appointmentDTOReq.creatorId())
                .orElseThrow(() -> new RuntimeException("Creator not found"));

//        log.info("User en cours + {}", creator.getAppointments());
//        log.info("Appointment DTO en cours + {}", appointmentDTOReq);


        // Créer l'appointment sans toucher aux collections existantes
        Appointment appointment = appointmentMapper.toEntity(appointmentDTOReq);
//        log.info("Appointment en cours + {}", appointment.getUserAppointments());

        appointment.setCreator(creator);

        // Créer l'association UserAppointment
        UserAppointment userAppointment = new UserAppointment();
        userAppointment.setUser(creator);
        userAppointment.setAppointment(appointment);
        userAppointment.setStatus(AppointmentStatus.APPROVED);

        // Ne pas modifier les collections manuellement, Hibernate s'en chargera via cascade
        appointment.setUserAppointments(
                Set.of(userAppointment)); // Initialisation explicite

        log.info("Appointment en cours + {}", appointment);

        // Sauvegarder l'appointment, Hibernate mettra à jour les relations
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

            // Utiliser la méthode helper pour ajouter la relation
            appointment.addUserAppointment(userAppointment);

            // Sauvegarder, Hibernate gérera la relation bidirectionnelle
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
}
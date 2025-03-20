package com.example.NetUp.appointment;

import com.example.NetUp.appointment.dtos.AppointmentDTOReq;
import com.example.NetUp.appointment.entities.Appointment;
import com.example.NetUp.appointment.mapper.AppointmentMapper;
import com.example.NetUp.appointment.repository.AppointmentRepository;
import com.example.NetUp.appointment.service.AppointmentServiceImpl;
import com.example.NetUp.user.entities.User;
import com.example.NetUp.user.repository.UserRepository;
import com.example.NetUp.user_appointment.AppointmentStatus;
import com.example.NetUp.user_appointment.entites.UserAppointment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AppointmentMapper appointmentMapper;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    private AppointmentDTOReq validDTO;
    private User creator;
    private Appointment appointment;

    @BeforeEach
    void setUp() {
        validDTO = new AppointmentDTOReq(
                "Réunion",
                "Description",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(1).plusHours(1),
                "Bureau",
                1L
        );

        creator = new User();
        creator.setId(1L);

        appointment = new Appointment();
        appointment.setId(1L);
        appointment.setCreator(creator);
    }

    // Tests pour createAppointment
    @Test
    void createAppointment_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(creator));
        when(appointmentMapper.toEntity(validDTO)).thenReturn(appointment);
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        Appointment result = appointmentService.createAppointment(validDTO);

        assertNotNull(result);
        assertEquals(creator, result.getCreator());
        assertEquals(1, result.getUserAppointments().size());
        assertEquals(AppointmentStatus.APPROVED, result.getUserAppointments().iterator().next().getStatus());
        verify(appointmentRepository, times(1)).save(any(Appointment.class));
    }

    @Test
    void createAppointment_CreatorNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            appointmentService.createAppointment(validDTO);
        });

        assertEquals("Creator not found", exception.getMessage());
        verify(appointmentRepository, never()).save(any());
    }

    // Tests pour joinAppointment
    @Test
    void joinAppointment_Success() {
        User user = new User();
        user.setId(2L);
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        Appointment result = appointmentService.joinAppointment(1L, 2L);

        assertNotNull(result);
        verify(appointmentRepository, times(1)).save(appointment);
    }

    @Test
    void joinAppointment_AlreadyJoined() {
        User user = new User();
        user.setId(2L);
        UserAppointment ua = new UserAppointment();
        ua.setUser(user);
        ua.setAppointment(appointment);
        appointment.setUserAppointments(Set.of(ua));
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));

        Appointment result = appointmentService.joinAppointment(1L, 2L);

        assertNotNull(result);
        verify(appointmentRepository, never()).save(any());
    }

    @Test
    void joinAppointment_AppointmentNotFound() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            appointmentService.joinAppointment(1L, 2L);
        });

        assertEquals("Appointment not found", exception.getMessage());
    }

    // Tests pour approveUser
    @Test
    void approveUser_Success() {
        User user = new User();
        user.setId(2L);
        UserAppointment ua = new UserAppointment();
        ua.setUser(user);
        ua.setAppointment(appointment);
        ua.setStatus(AppointmentStatus.PENDING);
        appointment.setUserAppointments(Set.of(ua));
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));
        when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        Appointment result = appointmentService.approveUser(1L, 2L, 1L);

        assertNotNull(result);
        assertEquals(AppointmentStatus.APPROVED, result.getUserAppointments().iterator().next().getStatus());
        verify(appointmentRepository, times(1)).save(appointment);
    }

    @Test
    void approveUser_NotCreator() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            appointmentService.approveUser(1L, 2L, 3L);
        });

        assertEquals("Only creator can approve participants", exception.getMessage());
        verify(appointmentRepository, never()).save(any());
    }

    // Tests pour getAllAppointments
    @Test
    void getAllAppointments_Success() {
        when(appointmentRepository.findAll()).thenReturn(List.of(appointment));

        List<Appointment> result = appointmentService.getAllAppointments();

        assertEquals(1, result.size());
        verify(appointmentRepository, times(1)).findAll();
    }

    // Tests pour getAppointmentById
    @Test
    void getAppointmentById_Success() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));

        Appointment result = appointmentService.getAppointmentById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(appointmentRepository, times(1)).findById(1L);
    }

    @Test
    void getAppointmentById_NotFound() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            appointmentService.getAppointmentById(1L);
        });

        assertEquals("Appointment not found with id: 1", exception.getMessage());
    }

    // Tests pour deleteAppointment
    @Test
    void deleteAppointment_Success() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.of(appointment));

        appointmentService.deleteAppointment(1L);

        verify(appointmentRepository, times(1)).delete(appointment);
    }

    @Test
    void deleteAppointment_NotFound() {
        when(appointmentRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            appointmentService.deleteAppointment(1L);
        });

        assertEquals("Appointment not found with id: 1", exception.getMessage());
        verify(appointmentRepository, never()).delete(any());
    }
}

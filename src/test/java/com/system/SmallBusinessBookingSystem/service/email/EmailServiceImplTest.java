package com.system.SmallBusinessBookingSystem.service.email;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
public class EmailServiceImplTest {

    @Mock
    JavaMailSender mailSender;

    @InjectMocks
    EmailServiceImpl emailService;

    @Test
    void shouldSuccessfullySendEmail() {
        // given
        String to = "taras.shevchuk0024@gmail.com";
        String subject = "Test Subject";
        String body = "This is a test email.";

        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        // when
        emailService.sendEmail(to, subject, body);

        // then
        // Verify that the mailSender.send method was called with a SimpleMailMessage object.
        mailSender.send(any(SimpleMailMessage.class));
    }
}
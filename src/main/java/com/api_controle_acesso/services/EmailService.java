package com.api_controle_acesso.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendPasswordResetCode(String email, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Código de Verificação para Redefinição de Senha");
        message.setText("Seu código de verificação para redefinição de senha é: " + code);
        javaMailSender.send(message);
    }

    public void sendPasswordResetLink(String email, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Link para Redefinição de Senha");
        message.setText("Você solicitou a redefinição de senha. Clique no link a seguir para redefinir sua senha: " + resetLink);
        javaMailSender.send(message);
    }
    
}

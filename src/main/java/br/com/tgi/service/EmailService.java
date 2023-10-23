package br.com.tgi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import br.com.tgi.exception.EnvioEmailException;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void notificarClientePorEmail(String destinatario, String assunto, String mensagem) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(destinatario);
        mailMessage.setSubject(assunto);
        mailMessage.setText(mensagem);

        try {
        	javaMailSender.send(mailMessage);
        } catch (Exception e) {
        	throw new EnvioEmailException();
        }
    }

}

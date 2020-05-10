package PayDayBankApi.service;

import PayDayBankApi.config.QueueConfig;
import PayDayBankApi.model.queue.LoanDemand;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class LoanDemandProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendToQueue(LoanDemand loanDemand) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(loanDemand.email);

        msg.setSubject("Kredi Talebi");
        msg.setText("Kredi Talebiniz alınmıştır");

        javaMailSender.send(msg);

        rabbitTemplate.convertAndSend(QueueConfig.routingName, QueueConfig.routingName, loanDemand);
    }
}

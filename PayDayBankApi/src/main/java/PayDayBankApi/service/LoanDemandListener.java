package PayDayBankApi.service;

import PayDayBankApi.config.QueueConfig;
import PayDayBankApi.model.queue.LoanDemand;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class LoanDemandListener {

    @Autowired
    private JavaMailSender javaMailSender;

    @RabbitListener(queues = QueueConfig.queueName)
    public void handleMessage(LoanDemand loanDemand) {

        Double result = loanDemand.income- (loanDemand.requestedLoan+loanDemand.previouslyGrantedLoan)*loanDemand.interestRate;

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(loanDemand.email);
        msg.setSubject("Kredi Talebi");

        if (result > 0)
        {
            msg.setText("Kredi Talebiniz olumlu sonuçlanmıştır");
        }
        else
        {
            msg.setText("Kredi Talebiniz olumsuz sonuçlanmıştır");
        }

        javaMailSender.send(msg);

    }
}

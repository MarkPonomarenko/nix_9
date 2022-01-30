package ua.com.alevel.cron;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.entity.server.Server;
import ua.com.alevel.persistence.entity.user.Personal;
import ua.com.alevel.service.PaymentService;
import ua.com.alevel.service.PersonalCrudService;
import ua.com.alevel.service.ServerService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PaymentCronService {

    private final PersonalCrudService personalService;
    private final PaymentService paymentService;
    private final ServerService serverService;

    public PaymentCronService(PersonalCrudService personalService, PaymentService paymentService, ServerService serverService) {
        this.personalService = personalService;
        this.paymentService = paymentService;
        this.serverService = serverService;
    }

    @Scheduled(fixedDelay = 60000)
    public void checkTime() {
        System.out.println("CRON triggered");
        List<Personal> personals = personalService.findAll();
        for (Personal personal : personals) {
            System.out.println(personal.getEmail());
            if (personal.getRented() != null) {
                for (Server server : personal.getRented()) {
                    Calendar c = Calendar.getInstance();
                    c.setTime(server.getUpdated());
                    c.add(Calendar.MINUTE, 5); //5 minutes for example
                    if (new Date().after(c.getTime())) {
                        System.out.println(server.getServerName());
                        paymentService.paymentProcess(server.getPersonal(), server);
                    }
                }
            }
        }
    }
}

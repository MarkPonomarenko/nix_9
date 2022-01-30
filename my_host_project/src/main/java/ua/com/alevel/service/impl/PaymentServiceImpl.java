package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.logger.LoggerLevel;
import ua.com.alevel.logger.LoggerService;
import ua.com.alevel.persistence.entity.server.Server;
import ua.com.alevel.persistence.entity.user.Personal;
import ua.com.alevel.service.PaymentService;
import ua.com.alevel.service.PersonalCrudService;
import ua.com.alevel.service.ServerService;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final LoggerService loggerService;

    private final PersonalCrudService personalService;

    private final ServerService serverService;

    public PaymentServiceImpl(LoggerService loggerService, PersonalCrudService personalService, ServerService serverService) {
        this.loggerService = loggerService;
        this.personalService = personalService;
        this.serverService = serverService;
    }

    @Override
    public void paymentProcess(Personal personal, Server server) {
        if (personal.getBalance() < server.getPrice()) {
            personal.removeRented(server);
            System.out.println(personal + "removed server " + server.getServerName());
            personalService.update(personal);
            serverService.update(server);
            loggerService.commit(LoggerLevel.INFO, personal.getEmail() + " removed server " + server.getServerName());
        } else {
            personal.setBalance(personal.getBalance() - server.getPrice());
            System.out.println(personal + " " + personal.getBalance());
            personalService.update(personal);
            loggerService.commit(LoggerLevel.INFO, personal.getEmail() + " payed "
                    + server.getPrice() + " for " + server.getServerName());
        }
    }

    @Override
    public void paymentProcess(Long userId, Integer serverPrice) {
        Personal personal = personalService.findById(userId).get();
        personal.setBalance(personal.getBalance() - serverPrice);
        System.out.println(personal + " " + personal.getBalance());
        personalService.update(personal);
    }
}

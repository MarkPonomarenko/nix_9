package ua.com.alevel.service;

import ua.com.alevel.persistence.entity.server.Server;
import ua.com.alevel.persistence.entity.user.Personal;

public interface PaymentService {

    void paymentProcess(Personal personal, Server server);

    void paymentProcess(Long personalId, Integer serverPrice);
}

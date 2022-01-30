package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.entity.user.Personal;
import ua.com.alevel.service.PaymentService;
import ua.com.alevel.service.PersonalCrudService;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PersonalCrudService personalService;

    public PaymentServiceImpl(PersonalCrudService personalService) {
        this.personalService = personalService;
    }

    @Override
    public void paymentProcess(Long userId, Integer serverPrice) {
        Personal personal = personalService.findById(userId).get();
        personal.setBalance(personal.getBalance() - serverPrice);
        System.out.println(personal + " " + personal.getBalance());
        personalService.update(personal);
    }
}

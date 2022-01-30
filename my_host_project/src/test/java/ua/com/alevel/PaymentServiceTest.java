package ua.com.alevel;

import com.neovisionaries.i18n.CountryCode;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ua.com.alevel.exception.EntityExistException;
import ua.com.alevel.persistence.entity.provider.Provider;
import ua.com.alevel.persistence.entity.user.Personal;
import ua.com.alevel.persistence.type.CPU;
import ua.com.alevel.service.PaymentService;
import ua.com.alevel.service.PersonalCrudService;
import ua.com.alevel.web.dto.request.ServerRequestDto;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
public class PaymentServiceTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PersonalCrudService personalCrudService;



    private final int ITEM_SIZE = 10;

    @Order(1)
    @Test
    void shouldBeMoneyTaken() {

        Personal personal = new Personal();
        personal.setFirstName("test");
        personal.setLastName("test");
        personal.setBalance(400);
        personal.setEmail("test@mail.com");
        personal.setPassword("password");
        personalCrudService.create(personal);
        Long createdId = personalCrudService.findAll().stream().findFirst().get().getId();
        paymentService.paymentProcess(createdId, 300);
        Assertions.assertEquals(100, personalCrudService.findById(createdId).get().getBalance());
    }
}

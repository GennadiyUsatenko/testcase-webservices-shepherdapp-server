package com.paazl.service;

import com.paazl.data.CurrentBalance;
import com.paazl.data.repositories.CurrentBalanceRepository;
import com.paazl.data.repositories.SheepRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;

import static junit.framework.TestCase.*;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ShepherdServiceTests {

    @MockBean
    private CurrentBalanceRepository currentBalanceRepository;
    @Autowired
    private ShepherdService shepherdService;
    @Value("${price_of_new_sheep}")
    private Integer priceOfSheep;

    @Test
    public void OrderNewSheep_HaveEnoughMoneyOnBalance_StringMessageReturns(){
        when(currentBalanceRepository.findFirstByOrderByTimestampDesc())
                .thenReturn(new CurrentBalance(BigInteger.valueOf(++priceOfSheep)));
        assertEquals("In total 1 sheep were ordered and added to your flock!", shepherdService.orderNewSheep(1));
    }

    @Test
    public void OrderNewSheep_NotHaveEnoughMoneyOnBalance_StringMessageReturns(){
        when(currentBalanceRepository.findFirstByOrderByTimestampDesc())
                .thenReturn(new CurrentBalance(BigInteger.valueOf(priceOfSheep)));
        assertEquals("You don't have enough money to order 2 sheep for your flock!", shepherdService.orderNewSheep(2));
    }

    @Test
    public void OrderNewSheep_ZeroNumberAsParam_ExceptionThrown(){
        try {
            shepherdService.orderNewSheep(0);
            fail();
        }catch (IllegalArgumentException expected){
            assertEquals("Parameter nofSheepDesired cannot be lower than 1", expected.getMessage());
        }
    }

    @Test
    public void OrderNewSheep_NegativeNumberAsParam_ExceptionThrown(){
        try {
            shepherdService.orderNewSheep(-1);
            fail();
        }catch (IllegalArgumentException expected){
            assertEquals("Parameter nofSheepDesired cannot be lower than 1", expected.getMessage());
        }
    }
}

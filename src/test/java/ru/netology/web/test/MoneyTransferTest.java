package ru.netology.web.test;

import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @Test
    void shouldTransferMoneyFromFirstToSecond() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var accountInfo = DataHelper.getAccountInfo();
        var verificationPage = loginPage.validLogin(accountInfo);
        var verifyInfo = DataHelper.getVerificationCodeFor(accountInfo);
        var dashboardPage = verificationPage.validVerify(verifyInfo);

        var secondId = accountInfo.getSecond().getId();
        var secondBalanceBefore = dashboardPage.getCardBalance(secondId);
        var firstId = accountInfo.getFirst().getId();
        var firstBalanceBefore = dashboardPage.getCardBalance(firstId);

        var transferPage = dashboardPage.replenishCardFrom(accountInfo.getFirst());
        int amount = 1500;
        transferPage.transferMoney(accountInfo.getFirst(), amount);
        var secondBalanceAfter = dashboardPage.getCardBalance(secondId);
        var firstBalanceAfter = dashboardPage.getCardBalance(firstId);

        assertEquals(firstBalanceBefore - amount, firstBalanceAfter);
        assertEquals(secondBalanceBefore + amount, secondBalanceAfter);
    }

    @Test
    void shouldTransferMoneyFromSecondToFirst() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var accountInfo = DataHelper.getAccountInfo();
        var verificationPage = loginPage.validLogin(accountInfo);
        var verifyInfo = DataHelper.getVerificationCodeFor(accountInfo);
        var dashboardPage = verificationPage.validVerify(verifyInfo);

        var secondCard = accountInfo.getSecond();
        var secondId = secondCard.getId();
        var secondBalanceBefore = dashboardPage.getCardBalance(secondId);
        var firstId = accountInfo.getFirst().getId();
        var firstBalanceBefore = dashboardPage.getCardBalance(firstId);

        var transferPage = dashboardPage.replenishCardFrom(secondCard);
        int amount = 1500;
        transferPage.transferMoney(secondCard, amount);
        var secondBalanceAfter = dashboardPage.getCardBalance(secondId);
        var firstBalanceAfter = dashboardPage.getCardBalance(firstId);

        assertEquals(firstBalanceBefore + amount, firstBalanceAfter);
        assertEquals(secondBalanceBefore - amount, secondBalanceAfter);
    }

    @Test
    void reloadPageShouldNotAffectBalance() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var accountInfo = DataHelper.getAccountInfo();
        var verificationPage = loginPage.validLogin(accountInfo);
        var verifyInfo = DataHelper.getVerificationCodeFor(accountInfo);
        var dashboardPage = verificationPage.validVerify(verifyInfo);

        var secondId = accountInfo.getSecond().getId();
        var secondBalanceBefore = dashboardPage.getCardBalance(secondId);
        var firstId = accountInfo.getFirst().getId();
        var firstBalanceBefore = dashboardPage.getCardBalance(firstId);

        dashboardPage.reloadPage();
        var secondBalanceAfter = dashboardPage.getCardBalance(secondId);
        var firstBalanceAfter = dashboardPage.getCardBalance(firstId);

        assertEquals(firstBalanceBefore, firstBalanceAfter);
        assertEquals(secondBalanceBefore, secondBalanceAfter);
    }

    /**
     * This test finds the bug.
     */
    @Test
    void shouldTransferMoneyIfAmountMoreThanBalance() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var accountInfo = DataHelper.getAccountInfo();
        var verificationPage = loginPage.validLogin(accountInfo);
        var verifyInfo = DataHelper.getVerificationCodeFor(accountInfo);
        var dashboardPage = verificationPage.validVerify(verifyInfo);

        var secondId = accountInfo.getSecond().getId();
        var secondBalanceBefore = dashboardPage.getCardBalance(secondId);
        var firstId = accountInfo.getFirst().getId();
        var firstBalanceBefore = dashboardPage.getCardBalance(firstId);

        var transferPage = dashboardPage.replenishCardFrom(accountInfo.getFirst());
        int amount = firstBalanceBefore + 1000;
        transferPage.transferMoney(accountInfo.getFirst(), amount);
        var secondBalanceAfter = dashboardPage.getCardBalance(secondId);
        var firstBalanceAfter = dashboardPage.getCardBalance(firstId);

        assertEquals(firstBalanceBefore, firstBalanceAfter);
        assertEquals(secondBalanceBefore, secondBalanceAfter);
    }
}
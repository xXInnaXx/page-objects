package ru.netology.web.test;

import org.junit.jupiter.api.Test;
import ru.netology.web.page.LoginPage;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransfer {

    @Test
    void shouldTransferMoneyBetweenOwnCardsV1() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var accountInfo = DataHelper.getAccountInfo();
        var verificationPage = loginPage.validLogin(accountInfo);
        var verifyInfo = DataHelper.getVerificationCodeFor(accountInfo);
        var dashboardPage = verificationPage.validVerify(verifyInfo);
        var id = accountInfo.getSecond().getId();
        var cardBalanceBefore = dashboardPage.getCardBalance(id);
        dashboardPage.transferMoney(accountInfo.getFirst(), 1500);
        var cardBalanceAfter = dashboardPage.getCardBalance(id);
        assertEquals(cardBalanceBefore + 1500, cardBalanceAfter);
    }

    @Test
    void shouldTransferMoneyBetweenOwnCardsV2() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var accountInfo = DataHelper.getAccountInfo();
        var verificationPage = loginPage.validLogin(accountInfo);
        var verifyInfo = DataHelper.getVerificationCodeFor(accountInfo);
        var dashboardPage = verificationPage.validVerify(verifyInfo);
        var id = accountInfo.getFirst().getId();
        var cardBalanceBefore = dashboardPage.getCardBalance(id);
        dashboardPage.transferMoney(accountInfo.getSecond(), 1000);
        var cardBalanceAfter = dashboardPage.getCardBalance(id);
        assertEquals(cardBalanceBefore + 1000, cardBalanceAfter);
    }

    @Test()
    void shouldNotTransferMoneyOverBalanceBetweenOwnCards() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var accountInfo = DataHelper.getAccountInfo();
        var verificationPage = loginPage.validLogin(accountInfo);
        var verifyInfo = DataHelper.getVerificationCodeFor(accountInfo);
        var dashboardPage = verificationPage.validVerify(verifyInfo);
        var id = accountInfo.getSecond().getId();
        var cardBalanceBefore = dashboardPage.getCardBalance(id);
        dashboardPage.transferMoney(accountInfo.getFirst(), 500000);
        var cardBalanceAfter = dashboardPage.getCardBalance(id);
        assertEquals(cardBalanceBefore, cardBalanceAfter);
    }
}
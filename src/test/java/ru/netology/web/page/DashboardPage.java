package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import lombok.val;
import org.openqa.selenium.By;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.not;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    // к сожалению, разработчики не дали нам удобного селектора, поэтому так
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public int getCardBalance(String id) {
        val text = cards.findBy(attribute("data-test-id", id)).text();
        return extractBalance(text);
    }

    public TransferPage replenishCardFrom(DataHelper.IdToCard from) {
        cards.findBy(not(attribute("data-test-id", from.getId())))
                .find(By.cssSelector("button"))
                .click();
        return new TransferPage();
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public void reloadPage() {
        $("[data-test-id=action-reload]").click();
    }
}

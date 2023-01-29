package ru.netology.page;

import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {

    public void transferMoney(DataHelper.IdToCard from, int amount) {
        $("[data-test-id=amount] input").setValue(String.valueOf(amount));
        $("[data-test-id=from] input").setValue(from.getCard());
        $("[data-test-id=action-transfer]").click();
    }
}

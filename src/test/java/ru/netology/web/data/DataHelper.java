package ru.netology.web.data;

import lombok.Value;

import java.util.Map;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AccountInfo {
        private String login;
        private String password;
        private Map<String, IdToCard> nameToCardNumber;

        public IdToCard getFirst() {
            return nameToCardNumber.get("first");
        }

        public IdToCard getSecond() {
            return nameToCardNumber.get("second");
        }
    }

    @Value
    public static class IdToCard {
        private String id;
        private String card;
    }

    public static AccountInfo getAccountInfo() {
        return new AccountInfo("vasya", "qwerty123",
                Map.of("first", new IdToCard("92df3f1c-a033-48e6-8390-206f6b1f56c0",
                                "5559 0000 0000 0001"),
                        "second", new IdToCard("0f3f5c2a-249e-4c3d-8287-09f7a039391d",
                                "5559 0000 0000 0002")));
    }


    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AccountInfo accountInfo) {
        return new VerificationCode("12345");
    }


}



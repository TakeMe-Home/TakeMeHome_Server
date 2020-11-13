package com.toy.takemehome.utils.notification;

public class NotificationBody {

    public static final String ORDER_REQUEST = "주문 요청이 왔어요!";

    public static String orderReceptionWithTime(int requiredTime) {
        return String.format("고객님이 주문하신 음식이 %d분 내에 도착할 예정입니다.", requiredTime);
    }

    public static String orderRefuseWithTime(String reason) {
        return String.format("고객님이 주문하신 음식이 취소되었습니다. \n 취소 사유: %s", reason);
    }
}

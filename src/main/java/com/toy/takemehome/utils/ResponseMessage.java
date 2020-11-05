package com.toy.takemehome.utils;

public class ResponseMessage {
    public static String INTERNAL_SERVER_ERROR = "서버 내부 에러";

    //customer
    public static final String CREATE_CUSTOMER = "고객 회원 가입 성공";
    public static final String CREATE_CUSTOMER_FAIL = "고객 회원 가입 실패";

    public static final String FIND_CUSTOMER = "고객 정보 조회 성공";
    public static final String NOT_FOUND_CUSTOMER = "고객 정보 조회 실패";

    public static String UPDATE_CUSTOMER = "고객 정보 수정 성공";
    public static String UPDATE_CUSTOMER_FAIL = "고객 정보 수정 실패";

    public static String DELETE_CUSTOMER = "고객 회원 탈퇴 성공";
    public static String DELETE_CUSTOMER_FAIL = "고객 회원 탈퇴 실패";

    //rider
    public static final String CREATE_RIDER = "라이더 회원 가입 성공";
    public static final String CREATE_RIDER_FAIL = "라이더 회원 가입 실패";

    public static final String FIND_RIDER = "라이더 정보 조회 성공";
    public static final String NOT_FOUND_RIDER = "라이더 정보 조회 실패";

    public static String UPDATE_RIDER = "라이더 정보 수정 성공";
    public static String UPDATE_RIDER_FAIL = "라이더 정보 수정 실패";

    public static String DELETE_RIDER = "라이더 회원 탈퇴 성공";
    public static String DELETE_RIDER_FAIL = "라이더 회원 탈퇴 실패";

    //restaurant
    public static final String CREATE_RESTAURANT = "가게 등록 성공";
    public static final String CREATE_RESTAURANT_FAIL = "가게 등록 실패";

    public static final String FIND_RESTAURANT = "가게 정보 조회 성공";
    public static final String NOT_FOUND_RESTAURANT = "가게 정보 조회 실패";

    public static String UPDATE_RESTAURANT = "가게 정보 수정 성공";
    public static String UPDATE_RESTAURANT_FAIL = "가게 정보 수정 실패";

    public static String DELETE_RESTAURANT = "가게 삭제 성공";
    public static String DELETE_RESTAURANT_FAIL = "가게 삭제 실패";

    //owner
    public static final String CREATE_OWNER = "가게 주인 등록 성공";
    public static final String CREATE_OWNER_FAIL = "가게 주인 등록 실패";

    public static final String FIND_OWNER = "가게 주인 정보 조회 성공";
    public static final String NOT_FOUND_OWNER = "가게 주인 정보 조회 실패";

    public static String UPDATE_OWNER = "가게 정보 수정 성공";
    public static String UPDATE_OWNER_FAIL = "가게 정보 수정 실패";

    public static String DELETE_OWNER = "가게 주인 삭제 성공";
    public static String DELETE_OWNER_FAIL = "가게 주인 삭제 실패";

    //menu
    public static final String CREATE_MENU = "메뉴 등록 성공";
    public static final String CREATE_MENU_FAIL = "메뉴 등록 실패";

    public static final String FIND_MENU = "메뉴 조회 성공";
    public static final String NOT_FOUND_MENU = "메뉴 조회 실패";

    public static String UPDATE_MENU = "메뉴 수정 성공";
    public static String UPDATE_MENU_FAIL = "메뉴 수정 실패";

    public static String DELETE_MENU = "메뉴 삭제 성공";
    public static String DELETE_MENU_FAIL = "메뉴 삭제 실패";

    //order
    public static final String CREATE_ORDER = "주문 등록 성공";
    public static final String CREATE_ORDER_FAIL = "주문 등록 실패";

    public static final String FIND_ORDER = "주문 조회 성공";
    public static final String NOT_FOUND_ORDER = "주문 조회 실패";

    public static String UPDATE_ORDER = "주문 수정 성공";
    public static String UPDATE_ORDER_FAIL = "주문 수정 실패";

    public static String CANCEL_ORDER = "주문 취소 성공";
    public static String CANCEL_ORDER_FAIL = "주문 취소 실패";

    public static String ASSIGNED_ORDER = "주문 배차 완료";
    public static String ASSIGNED_ORDER_FAIL = "주문 배차 실패";

    public static String ORDER_DELIVERY_REQUEST = "주문 배달 요청 성공";
    public static String ORDER_DELIVERY_REQUEST_FAIL = "주문 배달 요청 실패";

    //common
    public static String LOGIN_SUCCESS = "로그인 성공";
    public static String LOGIN_FAIL = "로그인 실패";
}

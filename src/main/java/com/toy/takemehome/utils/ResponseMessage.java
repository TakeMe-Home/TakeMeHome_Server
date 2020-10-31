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
}

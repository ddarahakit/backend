package com.example.backend.config;


import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청이 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),
    INVALID_USER_STATUS(false,2004,"삭제되거나 휴면인 계정의 접근입니다."),
    INVALID_USER_PASSWORD(false,2005,"잘못된 비밀번호입니다."),


    // [POST] /course/create
    POST_COURSES_EMPTY_NAME(false,2100,"코스 이름을 입력해주세요."),
    POST_COURSES_EMPTY_PRICE(false,2101,"코스 가격을 입력해주세요."),
    POST_COURSES_EMPTY_DESCRIPTION(false,2102,"코스 설명을 입력해주세요."),
    POST_COURSES_EMPTY_DETAIL(false,2103,"코스 상세 내용을 입력해주세요."),
    POST_COURSES_EMPTY_DISCOUNT(false,2104,"할인 비율을 입력해주세요."),
    POST_COURSES_EMPTY_CATEGORY_IDX(false,2105,"카테고리 IDX를 입력해주세요."),
    POST_COURSES_PRE_EXIST_COURSE(false,2106,"이미 존재하는 코스의 이름이 입력되었습니다."),

    // [GET] /course/{idx}
    GET_COURSES_EMPTY_IDX(false,2200,"코스 IDX 값을 입력해주세요."),
    GET_COURSES_INVALID_IDX(false,2201,"잘못된 코스 IDX 값입니다."),

    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),
    RESPONSE_NULL_ERROR_BY_IDX(false,3001,"입력된 IDX 값로 접근한 DB의 유효한 ROW가 존재하지 않습니다."),
    RESPONSE_NULL_ERROR(false,3002,"접근한 데이터 중 유효한 ROW가 존재하지 않습니다."),


    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다.");


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
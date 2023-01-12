package com.meetnow.meetnowbackend.global.error.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    /**
     *  에러처리 안내용 ENUM
     */
    HTTP_REQUEST_METHOD_NOT_SUPPORTED(405, "지정하신 URL은 사용하신 HTTP 메소드를 지원하지 않습니다."),
    METHOD_ARGUMENT_TYPE_MISMATCH(400, "유효하지 않은 요청값입니다"),

    // 회원
    DUPLICATED_USERNAME(400, "이미 존재하는 회원명입니다"),


    // 방 (Room)
    DUPLICATED_INVITATION_CODE(400, "한번 더 요청해주세요."),
    INVALID_INVITATION_CODE(400, "잘못된 초대 코드입니다"),
    USER_ALREADY_EXISTS(400, "이미 입장한 방입니다."),
    NO_TIMETABLE_YET(400, "시간표를 찾을 수 없습니다."),
    USER_NOT_IN_ROOM(400, "입장한 방이 아닙니다"),
    TIMETABLE_ALREADY_EXISTS(400, "이미 시간표를 만들었습니다"),


    //  회원 인증
    NOT_VALID_TOKEN(401, "유효하지 않은 토큰입니다."),
    NOT_EXISTS_AUTHORIZATION(401, "Authorization Header가 없습니다."),
    NOT_VALID_BEARER_GRANT_TYPE(401, "인증 타입이 Bearer 타입이 아닙니다.");

    private int status;
    private String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}

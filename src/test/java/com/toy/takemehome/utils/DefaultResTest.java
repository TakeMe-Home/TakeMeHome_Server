package com.toy.takemehome.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DefaultResTest {

    @DisplayName("정적 변수 FAIL_DEFAULT_RES가 생성되는지 확인")
    @Test
    public void staticVariable() throws Exception {
        //given
        final DefaultRes failDefaultRes = DefaultRes.FAIL_DEFAULT_RES;

        //then
        assertThat(failDefaultRes.getStatusCode()).isEqualTo(StatusCode.INTERNAL_SERVER_ERROR);
        assertThat(failDefaultRes.getMessage()).isEqualTo(ResponseMessage.INTERNAL_SERVER_ERROR);
        assertNull(failDefaultRes.getData());
    }

    @DisplayName("3개의 파라미터를 입력받는 res메서드가 잘 동작하는지 확인")
    @Test
    public void resWithThreeParameter() throws Exception {
        //given
        final DefaultRes<Object> actual =
                DefaultRes.res(StatusCode.INTERNAL_SERVER_ERROR, ResponseMessage.INTERNAL_SERVER_ERROR, null);

        //then
        assertThat(actual.getStatusCode()).isEqualTo(StatusCode.INTERNAL_SERVER_ERROR);
        assertThat(actual.getMessage()).isEqualTo(ResponseMessage.INTERNAL_SERVER_ERROR);
        assertNull(actual.getData());
    }
}
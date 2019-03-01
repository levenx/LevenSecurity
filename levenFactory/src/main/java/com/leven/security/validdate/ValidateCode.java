package com.leven.security.validdate;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by 孙乐进 on 2019/3/1.
 */
@Data
public class ValidateCode {

    private String code;

    private LocalDateTime expireTime;

    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    public boolean isExpired() {
        return this.expireTime.isAfter(LocalDateTime.now());
    }
}

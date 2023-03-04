package com.david.ptjob.item.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@Slf4j
public class ItemExpirationValidator {

    private static long baseExpiredTime;

    @Value("${item.expiration-time}")
    protected void setBaseExpiredTime(long baseExpiredTime) {
        ItemExpirationValidator.baseExpiredTime = baseExpiredTime;
    }

    public static boolean isExpiredFrom(LocalDateTime base, LocalDateTime comparedTarget) {
        // 기준시간(base) = 기준시간(base) - 유지시간(baseExpiredTime)
        base = base.minus(Duration.ofMillis(baseExpiredTime));
        return comparedTarget.isBefore(base);
    }
}

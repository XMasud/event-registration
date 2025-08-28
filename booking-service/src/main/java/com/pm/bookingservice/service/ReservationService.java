package com.pm.bookingservice.service;

import com.pm.bookingservice.dto.ReservationDetails;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReservationService {
    private final StringRedisTemplate redis;
    private static final Duration HOLD_TTL = Duration.ofMinutes(15);

    public ReservationService(StringRedisTemplate redis) {
        this.redis = redis;
    }

    public boolean reserveTicket(UUID ticketId, Long userId){

        String key = "reservation:ticket:" + ticketId;
        String value = "{\"userId\":\"" + userId + "\"}";

        Boolean ok = redis.opsForValue().setIfAbsent(key, value, HOLD_TTL);
        return Boolean.TRUE.equals(ok);
    }

    public Optional<ReservationDetails> getReservationInfo(UUID ticketId){

        String val = redis.opsForValue().get("reservation:ticket:" + ticketId);
        if (val == null) return Optional.empty();

        return Optional.of(ReservationDetails.fromJson(val));
    }

    public void releaseHold(UUID ticketId) {
        redis.delete("reservation:ticket:" + ticketId);
    }
}

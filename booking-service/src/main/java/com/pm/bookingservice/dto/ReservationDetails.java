package com.pm.bookingservice.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDetails implements Serializable {

    private UUID ticketId;
    private Long userId;
    private LocalDateTime reservedAt;
    private static final ObjectMapper mapper = new ObjectMapper();

    public String toJson() {
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize ReservationInfo", e);
        }
    }

    public static ReservationDetails fromJson(String val) {
        try {
            return mapper.readValue(val, ReservationDetails.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize ReservationInfo", e);
        }
    }
}

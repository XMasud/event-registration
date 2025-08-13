package com.pm.bookingservice.dto;

import com.pm.bookingservice.enums.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class TicketResponseDTO {

    private UUID id;
    private TicketStatus status;
    private int price;

}

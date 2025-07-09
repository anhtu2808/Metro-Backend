package com.metro.scanner.service;

import com.metro.scanner.dto.request.ScannerRequest;
import com.metro.scanner.dto.response.ScannerResponse;
import com.metro.scanner.dto.response.TicketOrderResponse;

public interface ScannerService {

    /**
     * Validates a ticket based on the provided scanner request.
     *
     * @param request the scanner request containing line ID, station ID, and ticket order token
     * @return a response indicating whether the ticket is valid or not
     */
    ScannerResponse validateTicket(ScannerRequest request);


    /**
     * Decode the ticket order token to retrieve the order ID.
     *
     * @param token ticket order token
     * @return a response containing a message and validation status
     */
    Long getTicketOrderIdFromToken(String token);

    TicketOrderResponse getTicketOrderByToken(String token);


}

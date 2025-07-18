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
     * Retrieves a ticket order by its token(QR code).
     *
     * @param token the token of the ticket order
     * @return the ticket order response containing details of the order
     */
    TicketOrderResponse getTicketOrderByToken(String token);


}

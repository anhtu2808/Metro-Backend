package com.metro.user.service;

import com.metro.user.dto.request.auth.AuthenticationRequest;
import com.metro.user.dto.request.auth.IntrospectRequest;
import com.metro.user.dto.request.auth.LogoutRequest;
import com.metro.user.dto.request.auth.RefreshRequest;
import com.metro.user.dto.response.auth.AuthenticationResponse;
import com.metro.user.dto.response.auth.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

import java.text.ParseException;

/**
 * Service interface for handling authentication and token management operations.
 */
public interface AuthenticationService {

    /**
     * Validate and introspect a given JWT token.
     * @param request payload containing the token to validate
     * @return response indicating token validity
     */
    IntrospectResponse introspect(IntrospectRequest request);

    /**
     * Authenticate user credentials and issue a new JWT.
     * @param request credentials (username and password)
     * @return authentication response with the issued token
     */
    AuthenticationResponse authenticate(AuthenticationRequest request);

    /**
     * Invalidate a refresh or access token (logout operation).
     * @param request containing the token to revoke
     * @throws ParseException if token parsing fails
     * @throws JOSEException if token verification fails
     */
    void logout(LogoutRequest request) throws ParseException, JOSEException;

    /**
     * Refresh an existing JWT using a valid refresh token.
     * @param request containing the refresh token
     * @return new authentication response with refreshed tokens
     * @throws ParseException if token parsing fails
     * @throws JOSEException if token verification fails
     */
    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;
}

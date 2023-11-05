package com.kanchan.synonyms.controller;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kanchan.synonyms.model.UserDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class TokenController {

	@Autowired
	JwtEncoder encoder;

	@Autowired
	AuthenticationManager authenticationManager;

	@Operation(summary = "Authorize/Authenticate user", description = "Returns API token")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Request is successful")})
	@PostMapping(path="/token",consumes="application/json")
	public String token(@RequestBody UserDTO user) {

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), 
				user.getPassword());

		Authentication authentication = authenticationManager.authenticate(token);

		String token1 = getJWTToken(authentication);
		return token1;
	}

	private String getJWTToken(Authentication authentication) {
		Instant now = Instant.now();
		long expiry = 36000L;

		String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(" "));
		JwtClaimsSet claims = JwtClaimsSet.builder().issuer("self").issuedAt(now).expiresAt(now.plusSeconds(expiry))
				.subject(authentication.getName()).claim("scope", scope).build();

		return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
	}

}

package com.deividsantos.assembly.client;

import com.deividsantos.assembly.client.response.UserVotePermissionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class UserInfoClient {

    private final RestTemplate restTemplate;
    private final String userInfoUrl;

    public UserInfoClient(RestTemplate restTemplate, @Value("${url.user-info}") String userInfoUrl) {
        this.restTemplate = restTemplate;
        this.userInfoUrl = userInfoUrl;
    }

    public UserVotePermissionResponse validate(String cpf) {
        return restTemplate.getForObject(buildUserInfoUrl(cpf), UserVotePermissionResponse.class);
    }

    private String buildUserInfoUrl(String cpf) {
        return UriComponentsBuilder
                .fromUriString(userInfoUrl)
                .pathSegment("users", cpf)
                .toUriString();
    }
}

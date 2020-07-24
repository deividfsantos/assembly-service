package com.deividsantos.assembly.service;

import com.deividsantos.assembly.client.UserInfoClient;
import com.deividsantos.assembly.exception.UnableToVoteException;
import com.deividsantos.assembly.type.VotePermission;
import org.springframework.stereotype.Service;

@Service
public class AssociatedService {

    private final UserInfoClient userInfoClient;

    public AssociatedService(UserInfoClient userInfoClient) {
        this.userInfoClient = userInfoClient;
    }

    public void validateAssociatedAbleToVote(String cpf) {
        final VotePermission status = userInfoClient.validate(cpf).getStatus();
        if (status.equals(VotePermission.UNABLE_TO_VOTE)) {
            throw new UnableToVoteException();
        }
    }
}

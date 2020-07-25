package com.deividsantos.assembly.service;

import com.deividsantos.assembly.client.UserInfoClient;
import com.deividsantos.assembly.exception.IncorrectCpfFormatException;
import com.deividsantos.assembly.exception.UnableToVoteException;
import com.deividsantos.assembly.type.VotePermission;
import com.deividsantos.assembly.util.DocumentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AssociatedService {
    private static final Logger logger = LoggerFactory.getLogger(AssociatedService.class);

    private final UserInfoClient userInfoClient;

    public AssociatedService(UserInfoClient userInfoClient) {
        this.userInfoClient = userInfoClient;
    }

    public void validateAssociatedAbleToVote(String cpf) {
        if (!DocumentUtil.isValidCpf(cpf)) {
            logger.error("CPF {} is incorrect.", cpf);
            throw new IncorrectCpfFormatException();
        }

        final VotePermission status = userInfoClient.validate(cpf).getStatus();
        if (status.equals(VotePermission.UNABLE_TO_VOTE)) {
            logger.error("The associated with cpf {} is unable to vote.", cpf);
            throw new UnableToVoteException();
        }
    }
}

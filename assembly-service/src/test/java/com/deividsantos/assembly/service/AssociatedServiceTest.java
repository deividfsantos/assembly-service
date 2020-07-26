package com.deividsantos.assembly.service;

import com.deividsantos.assembly.client.UserInfoClient;
import com.deividsantos.assembly.exception.IncorrectCpfFormatException;
import com.deividsantos.assembly.exception.UnableToVoteException;
import com.deividsantos.assembly.stub.UserInfoStub;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AssociatedServiceTest {

    private final AssociatedService associatedService;
    private final UserInfoClient userInfoClient;

    AssociatedServiceTest() {
        this.userInfoClient = mock(UserInfoClient.class);
        this.associatedService = new AssociatedService(userInfoClient);
    }

    @Test
    void validateAssociatedWithValidCpfAndAbleToVoteShouldDoNothing() {
        when(userInfoClient.validate("22246632005")).thenReturn(UserInfoStub.build());
        associatedService.validateAssociatedAbleToVote("22246632005");
    }

    @Test
    void validateAssociatedWithInvalidCpfShouldThrowsIncorrectCpfException() {
        assertThrows(IncorrectCpfFormatException.class, () -> associatedService.validateAssociatedAbleToVote("22246632003"));
    }

    @Test
    void validateAssociatedWithInvalidCpfShouldThrowsIncorrectCpfSizeException() {
        assertThrows(IncorrectCpfFormatException.class, () -> associatedService.validateAssociatedAbleToVote("246632003"));
    }

    @Test
    void validateAssociatedWithValidCpfAndUnableToVoteShouldThrowsUnableToVoteException() {
        when(userInfoClient.validate("22246632005")).thenReturn(UserInfoStub.buildUnable());
        assertThrows(UnableToVoteException.class, () -> associatedService.validateAssociatedAbleToVote("22246632005"));
    }
}
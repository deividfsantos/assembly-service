package com.deividsantos.assembly.stub;

import com.deividsantos.assembly.client.response.UserVotePermissionResponse;
import com.deividsantos.assembly.type.VotePermission;

public class UserInfoStub {
    public static UserVotePermissionResponse build() {
        final UserVotePermissionResponse value = new UserVotePermissionResponse();
        value.setStatus(VotePermission.ABLE_TO_VOTE);
        return value;
    }

    public static UserVotePermissionResponse buildUnable() {
        final UserVotePermissionResponse value = new UserVotePermissionResponse();
        value.setStatus(VotePermission.UNABLE_TO_VOTE);
        return value;
    }
}

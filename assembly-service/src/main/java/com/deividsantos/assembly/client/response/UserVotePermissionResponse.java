package com.deividsantos.assembly.client.response;

import com.deividsantos.assembly.type.VotePermission;

public class UserVotePermissionResponse {
    private VotePermission status;

    public VotePermission getStatus() {
        return status;
    }

    public void setStatus(VotePermission status) {
        this.status = status;
    }
}

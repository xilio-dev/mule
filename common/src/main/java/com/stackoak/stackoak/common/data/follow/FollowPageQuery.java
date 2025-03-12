package com.stackoak.stackoak.common.data.follow;

import com.stackoak.stackoak.common.data.PageQuery;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FollowPageQuery extends PageQuery {
    /**
     * 作者ID
     */
    @NotEmpty
    private String authorId;
}

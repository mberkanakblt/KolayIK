package com.kolayik.dto.request;

import com.kolayik.utility.enums.Status;

public record StatusUpdateRequest(
        Status status
) {
}

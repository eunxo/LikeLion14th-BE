package com.project.likelion14thbe.domain.review.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class ReviewReqDTO {

    public record ReviewCreateReq(
            String content,
            Double rating
    ) {

    }

    @Builder
    public record ReviewFixReq(
            String content
    ){

    }
}

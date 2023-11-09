package com.javahunter.multipleloggers.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseDto {

    private String name;
    private String email;
    private String location;
}

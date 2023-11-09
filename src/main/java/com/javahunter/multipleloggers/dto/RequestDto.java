package com.javahunter.multipleloggers.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestDto {

    private String name;
    private String email;
    private String password;
    private String location;
}

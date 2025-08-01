package com.mstra.removebg.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String clerkId;
    private String email;
    private String firstName;
    private String lastName;
    private Integer credits;
    private String photoUrl;
}

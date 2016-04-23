package dto;

import lombok.*;

/*
    Sposób użycia:
    UserInfo.builder().email(...).firstName(...).lastName(...).build();
*/

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserInfo {
    private String email;
    private String firstName;
    private String lastName;
}

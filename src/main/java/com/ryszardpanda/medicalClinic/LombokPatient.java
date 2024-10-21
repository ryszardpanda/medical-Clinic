package com.ryszardpanda.medicalClinic;

import lombok.*;

import java.time.LocalDate;
//@Getter
//@Setter
//@ToString
//@EqualsAndHashCode
@AllArgsConstructor

@Data
//@Builder
public class LombokPatient {
    private String email;
    private String password;
    private String idCardNo;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthDay;
}

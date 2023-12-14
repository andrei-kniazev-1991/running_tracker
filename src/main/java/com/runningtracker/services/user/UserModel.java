package com.runningtracker.services.user;

import com.runningtracker.services.runs.RunsModel;
import com.runningtracker.utils.models.UserGender;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserModel {

    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private Date birthDate;

    @NotNull
    private UserGender gender;

    private List<RunsModel> runs;
}

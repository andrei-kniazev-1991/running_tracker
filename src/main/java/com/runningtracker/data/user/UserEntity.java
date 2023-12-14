package com.runningtracker.data.user;

import com.runningtracker.data.runs.RunsEntity;
import com.runningtracker.utils.models.UserGender;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    private Date birthDate;

    @NotNull
    private UserGender gender;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RunsEntity> runs;
}

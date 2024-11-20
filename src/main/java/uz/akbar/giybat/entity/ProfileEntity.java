package uz.akbar.giybat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

import uz.akbar.giybat.enums.GeneralStatus;

import java.time.LocalDateTime;

/** Profile */
@Data
@Entity
@Table(name = "profile")
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String username; // phone or email

    private String password;

    @Enumerated(EnumType.STRING)
    private GeneralStatus status; // ACTIVE or BLOCK

    private Boolean visible = Boolean.TRUE;

    private LocalDateTime createdDate;
}

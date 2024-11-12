package uz.akbar.giybat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import uz.akbar.giybat.enums.ProfileRole;

/** ProfileRoleEntity */
@Entity
@Table(name = "profile_role")
public class ProfileRoleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "profile_id")
  private Integer profileId;

  @Column(name = "roles")
  @Enumerated(EnumType.STRING)
  private ProfileRole roles;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_id", insertable = false, updatable = false)
  private ProfileEntity profile;

  private LocalDateTime createdDate;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getProfileId() {
    return profileId;
  }

  public void setProfileId(Integer profileId) {
    this.profileId = profileId;
  }

  public ProfileRole getRoles() {
    return roles;
  }

  public void setRoles(ProfileRole roles) {
    this.roles = roles;
  }

  public ProfileEntity getProfile() {
    return profile;
  }

  public void setProfile(ProfileEntity profile) {
    this.profile = profile;
  }

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }
}

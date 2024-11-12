package uz.akbar.giybat.dto;

import jakarta.validation.constraints.NotBlank;

/** RegistrationDto */
public class RegistrationDto {

  @NotBlank(message = "Name required")
  private String name;

  @NotBlank(message = "Username required")
  private String username;

  @NotBlank(message = "Password required")
  private String password;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}

package uz.akbar.giybat.service;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.akbar.giybat.entity.ProfileRoleEntity;
import uz.akbar.giybat.enums.ProfileRole;
import uz.akbar.giybat.repository.ProfileRoleRepository;

/** ProfileRoleService */
@Service
public class ProfileRoleService {

  @Autowired ProfileRoleRepository repository;

  public void create(Integer profileId, ProfileRole role) {

    ProfileRoleEntity entity = new ProfileRoleEntity();
    entity.setProfileId(profileId);
    entity.setRoles(role);
    entity.setCreatedDate(LocalDateTime.now());

    repository.save(entity);
  }

  public void deleteRoles(Integer profileId) {
    repository.deleteByProfileId(profileId);
  }
}

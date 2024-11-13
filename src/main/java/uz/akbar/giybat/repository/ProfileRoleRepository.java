package uz.akbar.giybat.repository;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import uz.akbar.giybat.entity.ProfileRoleEntity;

/** ProfileRoleRepository */
public interface ProfileRoleRepository extends JpaRepository<ProfileRoleEntity, Integer> {

    @Transactional
    @Modifying
    void deleteByProfileId(Integer profileId);
}

package uz.akbar.giybat.repository;

import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.akbar.giybat.entity.ProfileEntity;
import uz.akbar.giybat.enums.GeneralStatus;

/** ProfileRepository */
public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer> {

  Optional<ProfileEntity> findByUsernameAndVisibleTrue(String username);

  Optional<ProfileEntity> findByIdAndVisibleTrue(Integer id);

  @Transactional
  @Modifying
  @Query("update ProfileEntity set status = ?2 where id = ?1")
  void changeStatus(Integer id, GeneralStatus status);
}

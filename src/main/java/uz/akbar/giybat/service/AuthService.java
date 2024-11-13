package uz.akbar.giybat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import uz.akbar.giybat.dto.LoginDto;
import uz.akbar.giybat.dto.ProfileDto;
import uz.akbar.giybat.dto.RegistrationDto;
import uz.akbar.giybat.entity.ProfileEntity;
import uz.akbar.giybat.enums.GeneralStatus;
import uz.akbar.giybat.enums.ProfileRole;
import uz.akbar.giybat.exceptions.AppBadException;
import uz.akbar.giybat.repository.ProfileRepository;

import java.time.LocalDateTime;
import java.util.Optional;

/** AuthService */
@Service
public class AuthService {

    @Autowired private BCryptPasswordEncoder passwordEncoder;
    @Autowired private ProfileRepository repository;
    @Autowired private ProfileRoleService profileRoleService;
    @Autowired private EmailService emailService;
    @Autowired private ProfileService profileService;

    public String registration(RegistrationDto dto) {

        Optional<ProfileEntity> optional =
                repository.findByUsernameAndVisibleTrue(dto.getUsername());
        if (optional.isPresent()) {

            ProfileEntity profile = optional.get();
            if (profile.getStatus().equals(GeneralStatus.IN_REGISTRATION)) {
                profileRoleService.deleteRoles(profile.getId());
                repository.delete(profile);
                // send sms or email
            } else {
                throw new AppBadException("Username already exists!");
            }
        }

        ProfileEntity profile = new ProfileEntity();
        profile.setName(dto.getName());
        profile.setUsername(dto.getUsername());
        profile.setPassword(passwordEncoder.encode(dto.getPassword()));
        profile.setStatus(GeneralStatus.IN_REGISTRATION);
        profile.setVisible(true);
        profile.setCreatedDate(LocalDateTime.now());

        repository.save(profile);

        profileRoleService.create(profile.getId(), ProfileRole.ROLE_USER);

        emailService.sendRegistrationEmail(profile.getUsername(), profile.getId());

        return "Successfully registered!";
    }

    public String regVerification(Integer profileId) {
        ProfileEntity profile = profileService.getById(profileId);

        if (profile.getStatus().equals(GeneralStatus.IN_REGISTRATION)) {
            repository.changeStatus(profileId, GeneralStatus.ACTIVE);
            return "Verification finished!";
        }

        throw new AppBadException("Verification failed!");
    }

    public ProfileDto login(LoginDto dto) {
        Optional<ProfileEntity> optional =
                repository.findByUsernameAndVisibleTrue(dto.getUsername());

        if (optional.isEmpty()) throw new AppBadException("Username or password is incorrect!");

        ProfileEntity profile = optional.get();

        if (!passwordEncoder.matches(dto.getPassword(), profile.getPassword()))
            throw new AppBadException("Username or password is incorrect!");

        if (!profile.getStatus().equals(GeneralStatus.ACTIVE))
            throw new AppBadException("Wrong status!");

        // TODO: return response
        return null;
    }
}

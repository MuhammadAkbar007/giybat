package uz.akbar.giybat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import uz.akbar.giybat.dto.AppResponse;
import uz.akbar.giybat.dto.LoginDto;
import uz.akbar.giybat.dto.RegistrationDto;
import uz.akbar.giybat.entity.ProfileEntity;
import uz.akbar.giybat.enums.AppLanguage;
import uz.akbar.giybat.enums.GeneralStatus;
import uz.akbar.giybat.enums.ProfileRole;
import uz.akbar.giybat.exceptions.AppBadException;
import uz.akbar.giybat.repository.ProfileRepository;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

/** AuthService */
@Service
public class AuthService {

    @Autowired private BCryptPasswordEncoder passwordEncoder;
    @Autowired private ProfileRepository repository;
    @Autowired private ProfileRoleService profileRoleService;
    @Autowired private EmailService emailService;
    @Autowired private ProfileService profileService;
    @Autowired private ResourceBundleMessageSource bundleMessage;

    public AppResponse registration(RegistrationDto dto, AppLanguage lang) {

        Optional<ProfileEntity> optional =
                repository.findByUsernameAndVisibleTrue(dto.getUsername());
        if (optional.isPresent()) {

            ProfileEntity profile = optional.get();
            if (profile.getStatus().equals(GeneralStatus.IN_REGISTRATION)) {
                profileRoleService.deleteRoles(profile.getId());
                repository.delete(profile);
            } else {
                throw new AppBadException(
                        bundleMessage.getMessage(
                                "email.phone.exists", null, new Locale(lang.name())));
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

        return new AppResponse(
                bundleMessage.getMessage("email.conform.send", null, new Locale(lang.name())));
    }

    public AppResponse regVerification(Integer profileId, AppLanguage lang) {
        ProfileEntity profile = profileService.getById(profileId);

        if (profile.getStatus().equals(GeneralStatus.IN_REGISTRATION)) {
            repository.changeStatus(profileId, GeneralStatus.ACTIVE);
            return new AppResponse(
                    bundleMessage.getMessage(
                            "verification.success", null, new Locale(lang.name())));
        }

        throw new AppBadException(
                bundleMessage.getMessage("verification.fail", null, new Locale(lang.name())));
    }

    public AppResponse login(LoginDto dto, AppLanguage lang) {
        Optional<ProfileEntity> optional =
                repository.findByUsernameAndVisibleTrue(dto.getUsername());

        ProfileEntity profile =
                optional.orElseThrow(
                        () ->
                                new AppBadException(
                                        bundleMessage.getMessage(
                                                "username.password.wrong",
                                                null,
                                                new Locale(lang.name()))));

        if (!passwordEncoder.matches(dto.getPassword(), profile.getPassword()))
            throw new AppBadException(
                    bundleMessage.getMessage(
                            "username.password.wrong", null, new Locale(lang.name())));

        if (!profile.getStatus().equals(GeneralStatus.ACTIVE))
            throw new AppBadException("Wrong status!");

        // TODO: return token
        return new AppResponse(null);
    }
}

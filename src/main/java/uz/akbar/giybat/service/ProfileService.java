package uz.akbar.giybat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uz.akbar.giybat.entity.ProfileEntity;
import uz.akbar.giybat.exceptions.AppBadException;
import uz.akbar.giybat.repository.ProfileRepository;

/** ProfileService */
@Service
public class ProfileService {

    @Autowired private ProfileRepository repository;

    public ProfileEntity getById(Integer id) {
        return repository
                .findByIdAndVisibleTrue(id)
                .orElseThrow(
                        () -> {
                            throw new AppBadException("Profile not found!");
                        });
    }
}

package com.Apple.Service;

import com.Apple.Dto.AppUserDto;
import com.Apple.Entity.AppUserEntity;
import com.Apple.Repository.AppUserRepository;
import com.Apple.payLoad.LoginDto;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserService {
    private AppUserRepository appUserRepository;

    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public AppUserDto addAppUser(AppUserDto appUserDto) {
        AppUserEntity entity =new AppUserEntity();
        entity.setName(appUserDto.getName());
        entity.setUsername(appUserDto.getUsername());
        entity.setEmail(appUserDto.getEmail());
        entity.setPassword(appUserDto.getPassword());
        AppUserEntity saved = appUserRepository.save(entity);
        AppUserDto dto = new AppUserDto();
        dto.setId(saved.getId());
        dto.setName(saved.getName());
        dto.setUsername(saved.getUsername());
        dto.setEmail(saved.getEmail());
        return dto;

    }

    public boolean verifyLogin(LoginDto loginDto) {
        Optional<AppUserEntity> opAppUser = appUserRepository.findByUsername(loginDto.getUsername());
        if (opAppUser.isPresent()){
            AppUserEntity appUser = opAppUser.get();
            return BCrypt.checkpw(loginDto.getPassword(),appUser.getPassword());
        }
        return false;
    }}

package com.Apple.Controller;

import com.Apple.Dto.AppUserDto;
import com.Apple.Repository.AppUserRepository;
import com.Apple.Service.AppUserService;
import com.Apple.payLoad.LoginDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class AppUserController {
    private AppUserService appUserService;
    private AppUserRepository appUserRepository;

    public AppUserController(AppUserService appUserService, AppUserRepository appUserRepository) {
        this.appUserService = appUserService;
        this.appUserRepository = appUserRepository;
    }

    @PostMapping
    public ResponseEntity<?> addAppUser(@RequestBody AppUserDto appUserDto){
        if(appUserRepository.existsByEmail(appUserDto.getEmail())){
            return new ResponseEntity<>("Email exists",HttpStatus.BAD_REQUEST);
        }
        if (appUserRepository.existsByUsername(appUserDto.getUsername())){
            return new ResponseEntity<>("Username exists",HttpStatus.BAD_REQUEST);
        }
        appUserDto.setPassword(BCrypt.hashpw(appUserDto.getPassword(),BCrypt.gensalt()));

        AppUserDto userDto = appUserService.addAppUser(appUserDto);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity <String> verifyLogin(@RequestBody LoginDto loginDto){
        boolean val = appUserService.verifyLogin(loginDto);
        if (val){
            return new ResponseEntity<>("login successful",HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid username/password",HttpStatus.OK);

    }
}

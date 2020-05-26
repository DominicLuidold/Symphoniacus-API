package at.fhv.teamb.symphoniacus.rest.service;

import at.fhv.teamb.symphoniacus.application.LoginManager;
import at.fhv.teamb.symphoniacus.application.dto.LoginUserDto;
import java.util.Optional;

public class LoginService {

    /**
     * Service to process a Login of a User.
     *
     * @return a Optional of a LoginUserDto if the login is valid the Optional is Present.
     */
    public Optional<LoginUserDto> login(String username, String password) throws Exception {
        LoginManager loginManager = new LoginManager();
        LoginUserDto dto = new LoginUserDto.UserDtoBuilder(-1)
                .withUserShortcut(username)
                .withPassword(password)
                .build();


        return loginManager.login(dto);
    }
}

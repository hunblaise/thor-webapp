package com.balazs.hajdu.components.delegators;

import com.balazs.hajdu.domain.Sensor;
import com.balazs.hajdu.domain.User;
import com.balazs.hajdu.domain.UserSearchType;
import com.balazs.hajdu.domain.view.AdminSearchQueryForm;
import com.balazs.hajdu.service.UserService;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * User Search Delegator.
 *
 * @author Balazs Hajdu
 */
@Component
public class UserSearchDelegator {

    @Inject
    private UserService userService;

    public Map<User, List<Sensor>> delegateSearch(String searchType, AdminSearchQueryForm adminSearchQueryForm) {
        Optional<UserSearchType> userSearchType = UserSearchType.getUserSearchType(searchType);

        Map<User, List<Sensor>> users = new HashMap<>();
        if (userSearchType.isPresent()) {
            switch (userSearchType.get()) {
                case KEYWORD_BASED:
                    users = userService.findUsersByKeyword(adminSearchQueryForm.getKeyword());
                    break;
                case LOCATION_BASED:
                    users = userService.findUsersInGivenArea(adminSearchQueryForm.getAddress(), adminSearchQueryForm.getDistance());
                    break;
                case USERNAME_BASED:
                    users = userService.findUserByUsername(adminSearchQueryForm.getUsername());
                    break;
            }
        } else {
            users = Collections.emptyMap();
        }

        return users;
    }
}

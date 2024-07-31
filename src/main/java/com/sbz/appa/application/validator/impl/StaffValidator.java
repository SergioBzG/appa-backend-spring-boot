package com.sbz.appa.application.validator.impl;

import com.sbz.appa.application.dto.UserDto;
import com.sbz.appa.application.exception.InvalidOrMissingDataException;
import com.sbz.appa.application.validator.Validator;
import com.sbz.appa.commons.Role;
import org.springframework.stereotype.Component;

@Component
public class StaffValidator implements Validator<UserDto>  {

    @Override
    public boolean validate(UserDto user) {
      if (hasRole(user) && user.getRole().equals(Role.ROLE_ADMIN.name()))
          return true;
      else if (!user.getRole().equals(Role.ROLE_BISON.name()))
          throw new InvalidOrMissingDataException("user", "role");
      return hasPhone(user) && hasVehicle(user) && hasDocument(user);
    }

    private boolean hasRole(UserDto user) {
        if (user.getRole() != null)
            return true;
        throw new InvalidOrMissingDataException("user", "role");
    }

    private boolean hasDocument(UserDto user) {
        if (user.getDocument() != null)
            return true;
        throw new InvalidOrMissingDataException("user", "document");
    }

    private boolean hasPhone(UserDto user) {
        if (user.getPhone() != null)
            return true;
        throw new InvalidOrMissingDataException("user", "phone");
    }

    private boolean hasVehicle(UserDto user) {
        if (user.getVehicle() != null)
            return true;
        throw new InvalidOrMissingDataException("user", "vehicle");
    }
}

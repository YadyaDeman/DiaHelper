package de.ait_tr.DiaHelper.service.mapping;

import de.ait_tr.DiaHelper.domain.dto.UserDto;
import de.ait_tr.DiaHelper.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface UserMappingService {

    UserDto mapEntityToDto(User entity);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    User mapDtoToEntity(UserDto dto);
}

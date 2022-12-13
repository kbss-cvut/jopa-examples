package cz.cvut.kbss.jopa.jsonld.dto.mapper;

import cz.cvut.kbss.jopa.jsonld.dto.OrganizationDto;
import cz.cvut.kbss.jopa.jsonld.dto.StudyDto;
import cz.cvut.kbss.jopa.jsonld.dto.UserDto;
import cz.cvut.kbss.jopa.jsonld.model.Organization;
import cz.cvut.kbss.jopa.jsonld.model.Study;
import cz.cvut.kbss.jopa.jsonld.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DtoMapper {

    OrganizationDto organizationToDto(Organization entity);

    Organization dtoToOrganization(OrganizationDto dto);

    StudyDto studyToDto(Study entity);

    Study dtoToStudy(StudyDto dto);

    UserDto userToDto(User entity);

    User dtoToUser(UserDto dto);
}

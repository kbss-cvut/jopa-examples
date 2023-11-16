/*
 * JOPA Examples
 * Copyright (C) 2023 Czech Technical University in Prague
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */
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

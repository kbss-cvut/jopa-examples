/**
 * Copyright (C) 2019 Czech Technical University in Prague
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details. You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package cz.cvut.kbss.jopa.eswc2016.util;

import java.util.Objects;

public enum ConfigParam {

    REPOSITORY_TYPE("repositoryType");

    private final String name;

    ConfigParam(String name) {
        this.name = name;
    }

    public static ConfigParam fromString(String str) {
        Objects.requireNonNull(str);
        for (ConfigParam param : values()) {
            if (str.equalsIgnoreCase(param.name)) {
                return param;
            }
        }
        throw new IllegalArgumentException("Unsupported configuration parameter " + str);
    }
}

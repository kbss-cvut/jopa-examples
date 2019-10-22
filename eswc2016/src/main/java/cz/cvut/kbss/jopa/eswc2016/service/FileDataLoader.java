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
package cz.cvut.kbss.jopa.eswc2016.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Classpath-based file content loader.
 */
@Service
public class FileDataLoader {

    private static final Logger LOG = LoggerFactory.getLogger(FileDataLoader.class);

    /**
     * Loads data from the specified file.
     * <p/>
     * The file is loaded as a classloader resource, so it should be on classpath or it should be an absolute path.
     *
     * @param fileName Name of the file to load
     * @return File contents as string
     */
    public String loadData(String fileName) {
        final InputStream is = this.getClass().getClassLoader().getResourceAsStream(fileName);
        if (is == null) {
            throw new IllegalArgumentException("File " + fileName + " not found.");
        }
        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            final StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            return sb.toString();
        } catch (IOException e) {
            LOG.error("Unable to read file contents.", e);
            return "";
        }
    }
}

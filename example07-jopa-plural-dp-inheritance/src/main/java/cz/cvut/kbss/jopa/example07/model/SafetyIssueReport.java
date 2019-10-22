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
package cz.cvut.kbss.jopa.example07.model;

import cz.cvut.kbss.jopa.model.annotations.OWLClass;
import cz.cvut.kbss.jopa.model.annotations.OWLObjectProperty;

import java.util.Set;

@OWLClass(iri = Vocabulary.c_SafetyIssueReport)
public class SafetyIssueReport extends Report {

    @OWLObjectProperty(iri = Vocabulary.p_basedOn)
    private Set<Report> bases;

    public Set<Report> getBases() {
        return bases;
    }

    public void setBases(Set<Report> bases) {
        this.bases = bases;
    }
}

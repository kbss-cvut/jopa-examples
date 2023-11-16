///
/// JOPA Examples
/// Copyright (C) 2023 Czech Technical University in Prague
///
/// This library is free software; you can redistribute it and/or
/// modify it under the terms of the GNU Lesser General Public
/// License as published by the Free Software Foundation; either
/// version 3.0 of the License, or (at your option) any later version.
///
/// This library is distributed in the hope that it will be useful,
/// but WITHOUT ANY WARRANTY; without even the implied warranty of
/// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
/// Lesser General Public License for more details.
///
/// You should have received a copy of the GNU Lesser General Public
/// License along with this library.
///

export default interface Record {
    id?: string;
    name?: string;
    types?: string[];

    has_question?: Question;
    has_answer?: Answer;

    isNew?: boolean;
}

export interface Question {
    id?: string;
    identifier?: number;
    has_data_value: string;
}

export interface Answer {
    id?: string;
    has_data_value?: string;
}

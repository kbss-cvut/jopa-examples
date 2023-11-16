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

export interface BaseEntity {
    id?: string;
}

export type Properties = { [key: string]: string | string[] };

export type SelectOption = {
    label?: string;
    value?: string;
}

export type Message = {
    message: string,
    type: MessageType,
    timestamp?: number
};

export type MessageType = "success" | "danger" | "warning" | "info";

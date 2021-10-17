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

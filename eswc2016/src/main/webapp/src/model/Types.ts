export interface BaseEntity {
    id?: string;
}

export type Properties = {[key: string]: string | string[]};

export type SelectOption = {
    label?: string;
    value: string;
}

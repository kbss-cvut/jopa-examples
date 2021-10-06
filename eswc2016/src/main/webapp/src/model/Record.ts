export default interface Record {
    id: string;
    name: string;
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
    id: string;
    has_data_value: string;
}

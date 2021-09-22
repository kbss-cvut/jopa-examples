export default interface Event {
    id: string;
    identifier?: number;
    title?: string;
    start: number;
    end: number;
    parts?: Event[];
    types?: string[];
}

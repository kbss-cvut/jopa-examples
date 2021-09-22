import {BaseEntity} from "./Types";

export default interface Person extends BaseEntity {
    accountName: string;
    firstName: string;
    lastName: string;
}

export class User {
    id?: string;
    email?: string;
    password?: string;
    firstName?: string;
    lastName?: string;
    birthDate?: string;
    gender?: Gender;
    nationality?: string;
    token?: string
}

export enum Gender {
    MALE = "MALE",
    FEMALE = "FEMALE"
}
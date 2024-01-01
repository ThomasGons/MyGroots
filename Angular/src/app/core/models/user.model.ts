export class User {
    id?: string;
    token?: string
    email?: string;
    firstName?: string;
    lastName?: string;
    birthDate?: string;
    gender?: Gender;
    nationality?: string;
    socialSecurityNumber?: string;
}

export enum Gender {
    MALE = "MALE",
    FEMALE = "FEMALE"
}

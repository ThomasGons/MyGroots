export class User {
    id?: string;
    token?: string
    email?: string;
    firstName?: string;
    lastName?: string;
    birthDate?: string;
    gender?: Gender;
    nationality?: string;
    socialSecurity?: string;
}

export enum Gender {
    MALE = "MALE",
    FEMALE = "FEMALE"
}

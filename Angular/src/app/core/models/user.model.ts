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

export enum Relation {
  FATHER = "FATHER",
  MOTHER = "MOTHER",
  PARTNER = "PARTNER",
  CHILD = "CHILD",
}

export enum Visibility {
  PUBLIC = "PUBLIC",
  PROTECTED = "PROTECTED",
  PRIVATE = "PRIVATE",
}

export class Person {
  firstName?: string;
  lastName?: string;
  gender?: Gender;
}

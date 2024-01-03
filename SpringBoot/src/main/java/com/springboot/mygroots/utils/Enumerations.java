package com.springboot.mygroots.utils;

public class Enumerations {
    public enum Visibility{
        PUBLIC,
        PROTECTED,
        PRIVATE
    }
    public enum NotifType {
        DEMAND_ADDTOFAMILY,
        ALERT_DEMANDDECLINED,
        ALERT_DEMANDACCEPTED,
    }

    public enum Gender {
        MALE,
        FEMALE
    }

    public enum Status {
        ALIVE,
        DEAD
    }

    public enum Relation{
        FATHER,
        MOTHER,
        CHILD,
        PARTNER,
    }


}

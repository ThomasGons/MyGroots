package com.springboot.mygroots.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Document(collection = "FamilyGraph")
public class FamilyGraph {

        private Map<Integer, Map<Integer,FamilyRelation>> adjacencyMatrix;
        private Map<Integer, Person> nodes;








    private enum FamilyRelation{
            FATHER,
            MOTHER,
            SON,
            DAUGHTER,
            BROTHER,
            SISTER,
            HUSBAND,
            WIFE,
            GRANDFATHER,
            GRANDMOTHER,
            GRANDSON,
            GRANDDAUGHTER,
            UNCLE,
            AUNT,
            NEPHEW,
            NIECE,
            COUSIN
    }


}

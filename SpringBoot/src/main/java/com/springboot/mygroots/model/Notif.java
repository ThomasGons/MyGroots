package com.springboot.mygroots.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.springboot.mygroots.utils.Enumerations.*;

import javax.management.Notification;

@Document(collection = "Notif")
public class Notif {

    @Id
    private String id;

    @DBRef
    private Account source;
    @DBRef
    private Account target;
    @DBRef
    private Person member;
    private Relation relation;
    private String body;
    private NotifType type;

    public Notif(Account source, Person member, Account target,NotifType type,Relation relation) { //Source : celui qui envoie la notification, member : celui qui est lié à la demande, target : celui qui reçoit la notification
        this.source = source;
        this.target = target;
        this.member = member;
        this.type = type;
        if(type == NotifType.DEMAND_ADDTOFAMILY){
            this.body = source.getPerson().getFirstName() + " " + source.getPerson().getLastName() + " wants to add you to his family as " + relation + " of " + member.getFirstName() + " " + member.getLastName();
        }
        else if(type == NotifType.ALERT_DEMANDACCEPTED){
            this.body = source.getPerson().getFirstName() + " " + source.getPerson().getLastName() + " accepted your demand";
        }
        else if(type == NotifType.ALERT_DEMANDDECLINED){
            this.body = source.getPerson().getFirstName() + " " + source.getPerson().getLastName() + " declined your demand";
        }
    }

    public void acceptDemand(){
        source.addNotif(new Notif(target,member,source,NotifType.ALERT_DEMANDACCEPTED,relation));
        target.removeNotif(this);

        Boolean isTreeEquivalant = source.getFamilyTree().isEquivalant(target.getFamilyTree());

        if(relation == Relation.FATHER){
            source.getFamilyTree().addFather(member,target.getPerson());
            if(isTreeEquivalant){
                target.getFamilyTree().addChild(member,source.getPerson());
            }
        }
        else if(relation == Relation.MOTHER){
            source.getFamilyTree().addMother(member,target.getPerson());
            if(isTreeEquivalant){
                target.getFamilyTree().addChild(member,source.getPerson());
            }
        }
        else if(relation == Relation.PARTNER){
            source.getFamilyTree().addPartner(member,target.getPerson());
            if(isTreeEquivalant){
                target.getFamilyTree().addPartner(member,source.getPerson());
            }
        }
        else if(relation == Relation.CHILD){
            source.getFamilyTree().addChild(member,target.getPerson());
            if(isTreeEquivalant){
                if(target.getPerson().getGender() == Gender.MALE){
                    target.getFamilyTree().addFather(member,source.getPerson());
                }
                else{
                    target.getFamilyTree().addMother(member,source.getPerson());
                }
            }
        }


    }

    public void declineDemand(){
        source.addNotif(new Notif(target,member,source,NotifType.ALERT_DEMANDDECLINED,relation));
        target.removeNotif(this);
    }

    public Account getSource() {
        return source;
    }

    public Account getTarget() {
        return target;
    }

    public String getBody() {
        return body;
    }


    public NotifType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Notif{" +
                "source=" + source +
                ", member=" + member +
                ", target=" + target +
                ", body='" + body + '\'' +
                ", type=" + type +
                '}';
    }


    public String getId() {return id;}
}

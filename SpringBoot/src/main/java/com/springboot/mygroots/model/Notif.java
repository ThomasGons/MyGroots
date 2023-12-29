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
            this.body = source.getPerson().getName() + " " + source.getPerson().getLastName() + " wants to add you to his family as " + relation + " of " + member.getName() + " " + member.getLastName();
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
        if(relation == Relation.FATHER){
            source.getFamilyTree().addFather(member,target.getPerson());
        }
        else if(relation == Relation.MOTHER){
            source.getFamilyTree().addMother(member,target.getPerson());
        }
        else if(relation == Relation.PARTNER){
            source.getFamilyTree().addPartner(member,target.getPerson());
        }
        else if(relation == Relation.CHILD){
            source.getFamilyTree().addChild(member,target.getPerson());
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



}

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

    private String body;
    private NotifType type;

    public Notif(Account source, Account target,NotifType type) {
        this.source = source;
        this.target = target;
        this.type = type;
        if(type == NotifType.DEMAND_ADDTOFAMILY){
            this.body = source.getPerson().getName() + " " + source.getPerson().getLastName() + " wants to add you to his family";
        }
        else if(type == NotifType.ALERT_DEMANDACCEPTED){
            this.body = source.getPerson().getName() + " " + source.getPerson().getLastName() + " accepted your demand";
        }
        else if(type == NotifType.ALERT_DEMANDDECLINED){
            this.body = source.getPerson().getName() + " " + source.getPerson().getLastName() + " declined your demand";
        }
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
                ", target=" + target +
                ", body='" + body + '\'' +
                ", type=" + type +
                '}';
    }



}

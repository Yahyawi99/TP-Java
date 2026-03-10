package netatlas.model;

import java.sql.*;
import java.time.*;

public class Invitation {

    private Long id;
    private Membre sender;
    private Membre recipient;
    private Enums.InvitationStatus status;
    private Date dateSent;

    public Invitation() {
    }

    public Invitation(Membre sender, Membre recipient) {
        this.sender = sender;
        this.recipient = recipient;
        this.status = Enums.InvitationStatus.EN_ATTENTE;
        this.dateSent = Date.valueOf(LocalDate.now());
    }

    public Invitation(Membre sender, Membre recipient, Date dateSent, Enums.InvitationStatus status) {
        this.sender = sender;
        this.recipient = recipient;
        this.status = status;
        this.dateSent = dateSent;
    }

    public boolean accept() {
        if (this.status == Enums.InvitationStatus.EN_ATTENTE) {
            this.status = Enums.InvitationStatus.ACCEPTEE;
            return true;
        }
        return false;
    }

    public boolean refuse() {
        if (this.status == Enums.InvitationStatus.EN_ATTENTE) {
            this.status = Enums.InvitationStatus.REFUSEE;
            return true;
        }
        return false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Membre getSender() {
        return sender;
    }

    public void setSender(Membre sender) {
        this.sender = sender;
    }

    public void setRecipient(Membre recipient) {
        this.recipient = recipient;
    }

    public Membre getRecipient() {
        return recipient;
    }

    public Enums.InvitationStatus getStatus() {
        return status;
    }

    public void setStatus(Enums.InvitationStatus status) {
        this.status = status;
    }

    public Date getDateSent() {
        return dateSent;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }
}
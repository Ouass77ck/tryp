import { Component, OnInit } from "@angular/core";
import { Invitation } from "../../../models/invitation.model";
import { InvitationService } from "../../../services/invitation.services";

@Component({
    standalone: false,
    selector: 'tryp-invitations',
    templateUrl: './invitations.component.html',
    styleUrl: './invitations.component.scss'
})

export class InvitationComponent implements OnInit{
    invitations!: Invitation[];
    constructor(private invitationService: InvitationService){

    }
    ngOnInit(): void{
        this.invitations = this.invitationService.getInvitations().filter(i => i.status === 'pending');;
    }

    acceptInvitation(id: string) {
        console.log('Accepted', id);
        this.invitationService.changeStatusById(id, 'accepted');
        this.invitations = this.invitations.filter(inv => inv.id !== id);
    }

    rejectInvitation(id: string) {
        console.log('Rejected', id);
        this.invitationService.changeStatusById(id, 'refused');
        this.invitations = this.invitations.filter(inv => inv.id !== id);
  }

  trackByInvitation(index: number, invitation: Invitation): string {
    return invitation.id;
}

}
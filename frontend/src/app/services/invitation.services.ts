import { Injectable } from "@angular/core";
import { Invitation } from "../models/invitation.model";

@Injectable({
    providedIn: "root"
})
export class InvitationService{
    private invitations : Invitation[] = 
    [new Invitation(
        "josua",
        "https://previews.123rf.com/images/lytasepta/lytasepta2212/lytasepta221200027/195898915-round-profile-image-of-female-avatar-for-social-networks-with-half-circle-fashion-and-beauty.jpg",
        "pending"
    ),
    new Invitation(
        "roger",
        "https://cdn-icons-png.flaticon.com/512/10910/10910346.png",
        "pending"
    ),
    new Invitation(
        "melanieedi98",
        "https://img.freepik.com/vecteurs-premium/profil-avatar-femme-icone-ronde_24640-14048.jpg",
        "pending"
    )
]

    getInvitations(): Invitation[]{
        return this.invitations;
    }

    changeStatusById(id: string, status: string): void{
        const foundInvitation: Invitation | undefined = this.invitations.find((invit : Invitation)=> invit.id ===id); //implement later a findInvitationById function
        if (!foundInvitation){
            throw new Error("Invitation non trouvée!")
        }
        foundInvitation.changeStatus(status);
    }
}
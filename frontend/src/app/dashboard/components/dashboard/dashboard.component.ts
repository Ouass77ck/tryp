import { Component } from "@angular/core";
import { HeaderComponent } from "../../../shared/components/header/components/header.component";
import { SidebarComponent} from "../../../shared/components/sidebar/components/sidebar.component";
import { UpcomingActivitiesComponent } from "../upcoming-activities/upcoming-activities.component";
import { UpcomingTripsComponent } from "../upcoming-trips/upcoming-trips.component";
import { InvitationComponent } from "../invitations/invitations.component";

@Component({
    selector: 'tryp-dashboard',
    templateUrl: './dashboard.component.html',
    styleUrl: './dashboard.component.scss',
    standalone: false
    })
export class DashboardComponent{

}
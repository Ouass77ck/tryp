import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { DashboardRoutingModule } from './dashboard-routing.module';
import { HeaderComponent } from "../shared/components/header/components/header.component";
import { SidebarComponent} from "../shared/components/sidebar/components/sidebar.component";
import { UpcomingActivitiesComponent } from './components/upcoming-activities/upcoming-activities.component';
import { UpcomingTripsComponent } from './components/upcoming-trips/upcoming-trips.component';
import { InvitationComponent } from './components/invitations/invitations.component';
import {MatCardModule} from '@angular/material/card';

@NgModule({
  declarations: [DashboardComponent,
    InvitationComponent
],
  imports: [
    CommonModule,
    DashboardRoutingModule,
    MatCardModule,
    HeaderComponent,
    SidebarComponent,
    UpcomingActivitiesComponent,
    UpcomingTripsComponent,
  ]
})

export class DashboardModule{}
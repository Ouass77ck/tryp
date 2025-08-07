import { Component } from "@angular/core";
import {MatIconModule} from '@angular/material/icon';
import { NgFor } from "@angular/common";
import { RouterLink, RouterLinkActive, RouterModule } from "@angular/router";
@Component({
    standalone: true,
    imports:[MatIconModule,
        NgFor,
        RouterLink,
        RouterLinkActive,
        RouterModule
    ],
    selector: 'tryp-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrl: './sidebar.component.scss'
})

export class SidebarComponent {
navItems = [
    { label: 'Dashboard', icon: 'dashboard', route: '/dashboard' },
    { label: 'Mes informations', icon: 'person', route: '/profile' },
    { label: 'Invitations', icon: 'mail', route: '/invitations' },
    { label: 'Mes voyages', icon: 'map', route: '/trips' },
    { label: 'Déconnexion', icon: 'logout', route: '/logout' }
  ];

  onNavigate(item: string) {
    console.log('Navigating to:', item);
    // Tu pourras brancher ça aux routes plus tard
  }
}
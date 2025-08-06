import { Component } from "@angular/core";
import {MatIconModule} from '@angular/material/icon';
import { NgFor } from "@angular/common";
@Component({
    standalone: true,
    imports:[MatIconModule,
        NgFor
    ],
    selector: 'tryp-sidebar',
    templateUrl: './sidebar.component.html',
    styleUrl: './sidebar.component.scss'
})

export class SidebarComponent {
  navItems = [
    { label: 'Dashboard', icon: 'dashboard' },
    { label: 'Mes informations', icon: 'person' },
    { label: 'Invitations', icon: 'mail' },
    { label: 'Mes voyages', icon: 'map' },
    { label: 'Déconnexion', icon: 'logout' }
  ];

  onNavigate(item: string) {
    console.log('Navigating to:', item);
    // Tu pourras brancher ça aux routes plus tard
  }
}
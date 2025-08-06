import { Component } from "@angular/core";
import { NgIf } from "@angular/common";
@Component({
    standalone: true,
    imports: [NgIf],
    selector: 'tryp-header',
    templateUrl: './header.component.html',
    styleUrl: './header.component.scss'
    })

export class HeaderComponent {
  user = {
    username: 'Paules Jacques',
    avatar: 'https://img.freepik.com/vecteurs-libre/illustration-du-jeune-homme-souriant_1308-174669.jpg?semt=ais_hybrid&w=740' // Mets ici une image locale fictive
  };

  menuOpen = false;

  toggleMenu() {
    this.menuOpen = !this.menuOpen;
  }

  closeMenu() {
    this.menuOpen = false;
  }

  onNavigate(action: string) {
    console.log('Action:', action);
    this.closeMenu();
  }
}
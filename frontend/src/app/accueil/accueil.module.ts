import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccueilComponent } from './components/accueil.component';
import { AccueilRoutingModule } from './accueil-routing.module';

@NgModule({
  declarations: [AccueilComponent],
  imports: [
    CommonModule,
    AccueilRoutingModule
  ]
})
export class AccueilModule {}

import { Routes } from '@angular/router';

export const routes: Routes = [
    {
  path: '',
  loadChildren: () => import('./accueil/accueil.module').then(m => m.AccueilModule)
},
{
  path: 'auth',
  loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule)
}
];


import { Routes } from '@angular/router';

export const routes: Routes = [
    {
  path: '',
  loadChildren: () => import('./accueil/accueil.module').then(m => m.AccueilModule)
},
{
  path: 'auth',
  loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule)
},
{
  path: 'dashboard',
  loadChildren: () => import('./dashboard/dashboard.module').then(m => m.DashboardModule)
}
];


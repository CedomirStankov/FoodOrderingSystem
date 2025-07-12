import { Routes } from '@angular/router';
import {LoginComponent} from './login/login.component';
import {PrikazKorisnikaComponent} from './prikaz-korisnika/prikaz-korisnika.component';
import {AddKorisnikaComponent} from './add-korisnika/add-korisnika.component';
import {UpdateKorisnikaComponent} from './update-korisnika/update-korisnika.component';
import {AddPorudzbineComponent} from './add-porudzbine/add-porudzbine.component';
import {SearchPorudzbineComponent} from './search-porudzbine/search-porudzbine.component';
import {ErrorHistoryComponent} from './error-history/error-history.component';

export const routes: Routes = [
  {
    path: "login",
    component: LoginComponent
  },
  {
    path: "prikaz",
    component: PrikazKorisnikaComponent
  },
  {
    path: "add",
    component: AddKorisnikaComponent
  },
  {
    path: "update/:id",
    component: UpdateKorisnikaComponent
  },
  {
    path: "addOrder",
    component: AddPorudzbineComponent
  },
  {
    path: "searchOrder",
    component: SearchPorudzbineComponent
  },
  {
    path: "errorHistory",
    component: ErrorHistoryComponent
  }
];

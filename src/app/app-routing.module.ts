import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./Components/Authentication/login/login.component";
import {RegisterComponent} from "./Components/Authentication/register/register.component";
import {HomeComponent} from "./Components/home/home.component";
import {DocumentsComponent} from "./Components/documents/documents.component";
import {ContributorsComponent} from "./Components/contributors/contributors.component";
import {MainlayoutComponent} from "./Components/Main/mainlayout/mainlayout.component";
import {UserComponent} from "./Components/user/user.component";
import {authGuard} from "./auth.guard";
import {loginGuard} from "./login.guard";

const routes: Routes = [
  { path: '', component: LoginComponent, title: 'Iniciar Sesión', canActivate: [loginGuard]},
  { path: 'login', component: LoginComponent, title: 'Iniciar Sesión', canActivate: [loginGuard]},
  { path: 'register', component: RegisterComponent, title: 'Registro'},
  { path: '', component: MainlayoutComponent, canActivate: [authGuard], children: [
      { path: 'home', component: HomeComponent, title: 'Inicio'},
      { path: 'documents', component: DocumentsComponent, title: 'Tipos de Documentos'},
      { path: 'contributors', component: ContributorsComponent, title: 'Tipos de Contribuidores'},
      { path: 'me', component: UserComponent, title: 'Mi Perfil'},
    ]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

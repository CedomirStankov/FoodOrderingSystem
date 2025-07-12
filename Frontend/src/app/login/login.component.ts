import { Component } from '@angular/core';
import {FormsModule} from '@angular/forms';
import {LoginData, Token, User} from '../model';
import {LoginService} from '../services/login/login.service';
import {UserService} from '../services/user/user.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  loginData: LoginData={
    email: "",
    password: ""
  }

  token: Token={
    jwt:""
  }

  constructor(private loginService: LoginService, private userService: UserService) { }

  login(){
    this.loginService.getToken(this.loginData).subscribe({
      next: (response: Token) => {
        this.token.jwt = response.jwt;
        console.log('Token:', this.token);
        localStorage.setItem("token",this.token.jwt)

        const payload = this.decodeJwtPayload(this.token.jwt);

        localStorage.setItem("can_create_users", String(payload.can_create_users));
        localStorage.setItem("can_read_users", String(payload.can_read_users));
        localStorage.setItem("can_update_users", String(payload.can_update_users));
        localStorage.setItem("can_delete_users", String(payload.can_delete_users));
        localStorage.setItem("can_search_orders", String(payload.can_search_order));
        localStorage.setItem("can_place_order", String(payload.can_place_order));
        localStorage.setItem("can_cancel_order", String(payload.can_cancel_order));
        localStorage.setItem("can_track_order", String(payload.can_track_order));
        localStorage.setItem("can_schedule_order", String(payload.can_schedule_order));
        localStorage.setItem("userId", String(payload.id));

        if(localStorage.getItem("can_create_users")==="false" &&
            localStorage.getItem("can_read_users")==="false" &&
            localStorage.getItem("can_update_users")==="false" &&
            localStorage.getItem("can_delete_users")==="false" &&
            localStorage.getItem("can_search_orders")==="false" &&
            localStorage.getItem("can_place_order")==="false" &&
            localStorage.getItem("can_cancel_order")==="false" &&
            localStorage.getItem("can_track_order")==="false" &&
            localStorage.getItem("can_schedule_order")==="false"){
            alert("You don't have any permission")
          }

      },
      error: (err) => {
        alert("Credentials are not correct")
        console.error('Login error:', err);
      }
    });


  }

  decodeJwtPayload(token: string): any {
    const payload = token.split('.')[1];
    const decoded = atob(payload);
    return JSON.parse(decoded);
  }
  

}

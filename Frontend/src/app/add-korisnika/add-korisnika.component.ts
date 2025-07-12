import {Component, OnInit} from '@angular/core';
import {LoginService} from '../services/login/login.service';
import {UserService} from '../services/user/user.service';
import {Token, User} from '../model';
import {FormsModule} from '@angular/forms';
import {NgIf} from '@angular/common';
import {Router} from '@angular/router';

@Component({
  selector: 'app-add-korisnika',
  standalone: true,
  imports: [
    FormsModule,
    NgIf
  ],
  templateUrl: './add-korisnika.component.html',
  styleUrl: './add-korisnika.component.css'
})
export class AddKorisnikaComponent implements OnInit{

  user: User={
    name: "",
    lastName: "",
    email: "",
    password: "",
    can_read_users: false,
    can_create_users: false,
    can_update_users: false,
    can_delete_users: false,
    can_search_order: false,
    can_place_order: false,
    can_cancel_order: false,
    can_track_order: false,
    can_schedule_order: false
  }

  users: User[]=[]

  isVisible: boolean=false

  ngOnInit(): void {
    if(localStorage.getItem("can_create_users")==="true"){
      this.isVisible=true
    }
  }
  constructor(private userService: UserService, private router: Router) { }

  addUser(){
    // console.log(this.user.email)
    // console.log(this.user.name)
    // console.log(this.user.lastName)
    // console.log(this.user.password)
    // console.log(this.user.can_create_users)
    // console.log(this.user.can_read_users)
    // console.log(this.user.can_update_users)
    // console.log(this.user.can_delete_users)

    this.userService.getUsers().subscribe((users: User[]) => {
      this.users=users
      for(let i=0;i<this.users.length;i++){
        if(this.users[i].email===this.user.email){
          alert("User with this email already exists")
          return
        }
      }
      this.userService.addUser(this.user).subscribe()
    })
    //this.router.navigate(['/prikaz'])

  }

}

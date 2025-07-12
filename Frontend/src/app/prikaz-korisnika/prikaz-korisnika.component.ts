import {Component, OnInit} from '@angular/core';
import {User} from '../model';
import {UserService} from '../services/user/user.service';
import {FormsModule} from '@angular/forms';
import {NgForOf, NgIf} from '@angular/common';
import {RouterLink} from '@angular/router';

@Component({
  selector: 'app-prikaz-korisnika',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf,
    RouterLink,
    NgIf
  ],
  templateUrl: './prikaz-korisnika.component.html',
  styleUrl: './prikaz-korisnika.component.css'
})
export class PrikazKorisnikaComponent implements OnInit{

  users: User[]=[]

  isVisibleRead: boolean=false
  isVisibleDelete: boolean=false
  isVisibleUpdate: boolean=false

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    if(localStorage.getItem("can_read_users")==="true"){
      this.isVisibleRead=true
    }
    if(localStorage.getItem("can_delete_users")==="true"){
      this.isVisibleDelete=true
    }
    if(localStorage.getItem("can_update_users")==="true"){
      this.isVisibleUpdate=true
    }
      this.getUsers()
  }

  getUsers(){
    this.userService.getUsers().subscribe((users: User[]) => {
      this.users=users
    })
  }

  deleteUser(id: number | undefined){
    this.userService.deleteUser(id).subscribe()
    window.location.reload();
  }
}

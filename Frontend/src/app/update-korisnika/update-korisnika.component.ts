import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {User} from '../model';
import {UserService} from '../services/user/user.service';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-update-korisnika',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './update-korisnika.component.html',
  styleUrl: './update-korisnika.component.css'
})
export class UpdateKorisnikaComponent implements OnInit {
  userId!: string;

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
    can_schedule_order: false,
  }

  isVisible: boolean=false
  constructor(private route: ActivatedRoute, private userService: UserService, private router: Router) {}

  ngOnInit() {
    if(localStorage.getItem("can_update_users")==="true"){
      this.isVisible=true
    }

    this.userId = this.route.snapshot.paramMap.get('id')!;
    this.userService.getUserById(Number(this.userId)).subscribe((user: User) => {
      this.user=user
    })
  }

  updateUser(){
    this.userService.updateUser(this.user, this.userId).subscribe()
    // this.router.navigate(['/prikaz'])
  }
}

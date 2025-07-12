import {Component, OnInit, ViewChild} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {ErrorMessage, User} from '../model';
import {UserService} from '../services/user/user.service';
import {ErrorMessageService} from '../services/errorMessage/error-message.service';
import {MatPaginatorModule} from '@angular/material/paginator';
import { MatPaginator, PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-error-history',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    MatPaginatorModule
  ],
  templateUrl: './error-history.component.html',
  styleUrl: './error-history.component.css'
})
export class ErrorHistoryComponent implements OnInit{

  isVisibleCreateUser: boolean=false

  errorMessages: ErrorMessage[]=[]

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  totalMessages: number = 0;
  pageSize: number = 10;
  currentPage: number = 0;

  constructor(private errorMessageService: ErrorMessageService) { }

  ngOnInit(): void {
    if(localStorage.getItem("can_create_users")==="true"){
      this.isVisibleCreateUser=true
    }
    this.getErrorMessages(this.currentPage, this.pageSize);
  }

  getErrorMessages(page: number, size: number){
    if(this.isVisibleCreateUser){
      this.errorMessageService.getErrorMessages(page, size).subscribe((response) => {
        this.errorMessages = response.content;
        this.totalMessages = response.totalElements;
      });
    }else{
      this.errorMessageService.getErrorMessagesForUser(page,size).subscribe((response) => {
        this.errorMessages = response.content;
        this.totalMessages = response.totalElements;
      })
    }
  }

  onPageChange(event: PageEvent): void {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.getErrorMessages(this.currentPage, this.pageSize);
  }
}

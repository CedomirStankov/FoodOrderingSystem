import {Component, OnDestroy, OnInit} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {FilterData, LoginData, Order, User} from '../model';
import {UserService} from '../services/user/user.service';
import {OrderService} from '../services/order/order.service';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-search-porudzbine',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    FormsModule
  ],
  templateUrl: './search-porudzbine.component.html',
  styleUrl: './search-porudzbine.component.css'
})
export class SearchPorudzbineComponent implements OnInit, OnDestroy{

  orders: Order[]=[]

  isVisibleSearchOrder: boolean=false
  isVisibleCreateUser: boolean=false
  isVisibleCancel: boolean=false
  isVisibleTrack: boolean=false

  private intervalId: any;

  filterData: FilterData={
    status: "",
    dateFrom: null as unknown as Date,
    dateTo: null as unknown as Date,
    userId: ""
  }

  filterData2: FilterData={
    status: "",
    dateFrom: null as unknown as Date,
    dateTo: null as unknown as Date,
    userId: ""
  }
  constructor(private orderService: OrderService) { }

  ngOnInit(): void {
    if(localStorage.getItem("can_search_orders")==="true"){
      this.isVisibleSearchOrder=true
    }
    if(localStorage.getItem("can_create_users")==="true"){
      this.isVisibleCreateUser=true
    }
    if(localStorage.getItem("can_cancel_order")==="true"){
      this.isVisibleCancel=true
    }
    if(localStorage.getItem("can_track_order")==="true"){
      this.isVisibleTrack=true
    }
    this.getOrders()
    if (!this.intervalId) {
      this.intervalId = setInterval(() => {
        this.filter2();
      }, 5000);
    }
  }

  ngOnDestroy(): void {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  }

  getOrders(){
    if(this.isVisibleCreateUser){
      this.orderService.getAllOrders().subscribe((orders: Order[]) => {
        this.orders=orders
      })
    }else{
      this.orderService.getOrdersOfUser().subscribe((orders: Order[]) => {
        this.orders=orders
      })
    }
  }

  filter(){
    this.orderService.getAllOrdersFiltered(this.filterData, this.isVisibleCreateUser).subscribe((orders: Order[]) => {
      this.orders=orders
    })
    this.filterData2 = { ...this.filterData };
  }

  filter2(){
    this.orderService.getAllOrdersFiltered(this.filterData2, this.isVisibleCreateUser).subscribe((orders: Order[]) => {
      this.orders=orders
    })
  }

  cancelOrder(orderId: number | undefined){
    this.orderService.cancelOrder(orderId).subscribe()
    window.location.reload()
  }
}

import {Component, OnInit} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {Food, LoginData, Order, OrderwithoutId, ScheduledOrder, User} from '../model';
import {UserService} from '../services/user/user.service';
import {Router} from '@angular/router';
import {OrderService} from '../services/order/order.service';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-add-porudzbine',
  standalone: true,
  imports: [
    FormsModule,
    NgIf
  ],
  templateUrl: './add-porudzbine.component.html',
  styleUrl: './add-porudzbine.component.css'
})
export class AddPorudzbineComponent implements OnInit{

  isVisiblePlaceOrder: boolean=false
  isVisibleScheduleOrder: boolean=false

  food: Food={
    food1: false,
    food2: false,
    food3: false
  }

  foodScheduled: Food={
    food1: false,
    food2: false,
    food3: false
  }

  order: OrderwithoutId={
    status: "ORDERED",
    active: true,
    items: "",
    createdBy:-1
  }

  scheduledOrder: ScheduledOrder={
    food: "",
    dateTime: null as unknown as Date,
    createdBy: -1
  }

  constructor(private orderService: OrderService) { }

  ngOnInit(): void {
    if(localStorage.getItem("can_place_order")==="true"){
      this.isVisiblePlaceOrder=true
    }
    if(localStorage.getItem("can_schedule_order")==="true"){
      this.isVisibleScheduleOrder=true
    }
  }

  addOrders(){
    this.order.items=this.getFoodString(this.food)
    this.order.createdBy=Number(localStorage.getItem("userId"))
    console.log(this.order)
    this.orderService.addOrders(this.order).subscribe()
  }

  getFoodString(food: Food): string {
    const result: string[] = [];
    if (food.food1)
      result.push("pica");
    if (food.food2)
      result.push("cevapi");
    if (food.food3)
      result.push("piletina");
    return result.join(", ");
  }

  scheduleOrder(){
    this.scheduledOrder.food = this.getFoodString(this.foodScheduled)
    this.scheduledOrder.createdBy=Number(localStorage.getItem("userId"))
    console.log(this.scheduledOrder.food)
    console.log(this.scheduledOrder.dateTime)
    this.orderService.scheduleOrder(this.scheduledOrder).subscribe()

  }

}

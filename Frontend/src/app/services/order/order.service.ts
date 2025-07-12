import { Injectable } from '@angular/core';
import {Observable, throwError} from 'rxjs';
import {FilterData, Order, OrderwithoutId, ScheduledOrder, User} from '../../model';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from '../../enviroments/enviroments';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private readonly apiUrl = environment.Api
  constructor(private httpClient: HttpClient) { }

  getAllOrders(): Observable<Order[]>{
    const token = localStorage.getItem("token");

    if (!token) {
      console.error('Token nije dostupan u localStorage.');
      return throwError(() => new Error('Autorizacija nije moguća. Token nije pronađen.'));
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.httpClient.get<Order[]>(`${this.apiUrl}/api/orders/filter`, {headers});
  }

  getOrdersOfUser(): Observable<Order[]>{
    const token = localStorage.getItem("token");
    const userId = localStorage.getItem("userId");

    if (!token) {
      console.error('Token nije dostupan u localStorage.');
      return throwError(() => new Error('Autorizacija nije moguća. Token nije pronađen.'));
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    console.log("AAA "+ `${this.apiUrl}/api/orders/filter?userId=${userId}`)
    return this.httpClient.get<Order[]>(`${this.apiUrl}/api/orders/filter?userId=${userId}`, {headers});
  }

  getAllOrdersFiltered(filterData: FilterData, isVisibleCreateUser: boolean): Observable<Order[]>{
    const token = localStorage.getItem("token");

    if (!token) {
      console.error('Token nije dostupan u localStorage.');
      return throwError(() => new Error('Autorizacija nije moguća. Token nije pronađen.'));
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    console.log(`${this.apiUrl}/api/orders/filter?status=${filterData.status}&dateFrom=${filterData.dateFrom ? filterData.dateFrom: ""}&dateTo=${filterData.dateTo ? filterData.dateTo: ""}&userId=${filterData.userId}`)
    if(isVisibleCreateUser){
      return this.httpClient.get<Order[]>(`${this.apiUrl}/api/orders/filter?status=${filterData.status}&dateFrom=${filterData.dateFrom ? filterData.dateFrom: ""}&dateTo=${filterData.dateTo ? filterData.dateTo: ""}&userId=${filterData.userId}`, {headers});
    }else{
      const userId = localStorage.getItem("userId");
      return this.httpClient.get<Order[]>(`${this.apiUrl}/api/orders/filter?status=${filterData.status}&dateFrom=${filterData.dateFrom ? filterData.dateFrom: ""}&dateTo=${filterData.dateTo ? filterData.dateTo: ""}&userId=${userId}`, {headers});
    }

  }

  addOrders(newOrder: OrderwithoutId){
    const token = localStorage.getItem("token");

    if (!token) {
      console.error('Token nije dostupan u localStorage.');
      return throwError(() => new Error('Autorizacija nije moguća. Token nije pronađen.'));
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.httpClient.post<OrderwithoutId>(`${this.apiUrl}/api/orders/add`, newOrder, { headers });
  }

  scheduleOrder(scheduledOrder: ScheduledOrder){
    const token = localStorage.getItem("token");

    if (!token) {
      console.error('Token nije dostupan u localStorage.');
      return throwError(() => new Error('Autorizacija nije moguća. Token nije pronađen.'));
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    console.log(scheduledOrder.food)
    console.log(scheduledOrder.dateTime)
    return this.httpClient.post<ScheduledOrder>(`${this.apiUrl}/api/orders/schedule`, scheduledOrder, { headers });
  }

  cancelOrder(orderId: number | undefined): Observable<Order>{
    const token = localStorage.getItem("token");

    if (!token) {
      console.error('Token nije dostupan u localStorage.');
      return throwError(() => new Error('Autorizacija nije moguća. Token nije pronađen.'));
    }

    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);

    return this.httpClient.patch<Order>(`${this.apiUrl}/api/orders/cancel/${orderId}`,{}, { headers });
  }
}

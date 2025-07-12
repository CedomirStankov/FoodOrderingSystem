export interface Token{
  jwt: string
}

export interface LoginData{
  email: string,
  password: string
}

export interface User{
  id?: number,
  name: string,
  lastName: string,
  email: string,
  password: string,
  can_read_users: boolean,
  can_create_users: boolean,
  can_update_users: boolean,
  can_delete_users: boolean,
  can_search_order: boolean,
  can_place_order: boolean,
  can_cancel_order: boolean,
  can_track_order: boolean,
  can_schedule_order: boolean,
}

export interface UserWithId{
  id: number,
  name: string,
  lastName: string,
  email: string,
  password: string,
  can_read_users: boolean,
  can_create_users: boolean,
  can_update_users: boolean,
  can_delete_users: boolean,
}

export interface Order{
  id: number,
  status: string,
  active: boolean,
  items: string,
  createdBy: number
}

export interface OrderwithoutId{
  id?: number,
  status: string,
  active: boolean,
  items: string,
  createdBy: number
}
export interface FilterData{
  status: string,
  dateFrom: Date,
  dateTo: Date,
  userId: string
}

export interface Food{
  food1: boolean,
  food2: boolean,
  food3: boolean
}

export interface ErrorMessage{
  id: number,
  createdAt: string,
  message: string,
  orderId: number,
  userId: number
}

export interface ScheduledOrder{
  food: string,
  dateTime: Date,
  createdBy: number,
}

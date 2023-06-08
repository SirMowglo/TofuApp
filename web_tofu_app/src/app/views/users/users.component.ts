import { Component } from '@angular/core';
import { UserDetailsResponse, UserResponse } from 'src/app/models/user.interface';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent {
  userList: UserResponse[] = []
  selectedUser = {} as UserDetailsResponse;

  indexUsers = 0;

  constructor(
    private userService: UserService,
  ) {}

  ngOnInit(): void {
    this.getUsers();
  }
  //TODO Crear vistas para el usuario (Cartas y tal)
  //TODO Crear lista de usuario ocupando todo el espacio
  //TODO Ver detalles del usuario al clickar
  //TODO Posibilidad de borrar el usuario seleccionado


  getUsers(){
    this.userService.getUsersBySearch('', this.indexUsers)
    .subscribe(res =>{
      if(res.totalPages>= this.indexUsers){
        this.userList = res.content
      }
    })
  }

  getUserDetails(user: UserResponse){
    this.userService.getUserDetailsByUsername(user.username).subscribe(res =>{
      this.selectedUser = res
    })
  }
}

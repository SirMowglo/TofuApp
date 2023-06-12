import { Component } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { RegisterDialogComponent } from 'src/app/components/dialogs/register-dialog/register-dialog.component';
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

  totalPages= 0
  indexUsers = 0;

  constructor(
    private userService: UserService,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.getUsers();
  }

  getUsers(){
    this.userService.getUsersBySearch('', this.indexUsers)
    .subscribe(res =>{
      if(res.totalPages>= this.indexUsers){
        this.userList = res.content
        this.totalPages = res.totalPages -1
      }
    })
  }

  getUserDetails(user: UserResponse){
    this.userService.getUserDetailsByUsername(user.username).subscribe(res =>{
      this.selectedUser = res
    })
  }

  onScroll(){
    if(this.totalPages > this.indexUsers){
      this.indexUsers++
      this.userService
      .getUsersBySearch('', this.indexUsers)
      .subscribe(res =>{
        this.userList.push(...res.content)
      })
    }
  }
  openDialog(): void {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.autoFocus = true;
    dialogConfig.panelClass = 'outlined_box_4px';

    this.dialog.open(RegisterDialogComponent, dialogConfig);
  }
}

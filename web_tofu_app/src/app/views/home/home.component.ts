import { Component, OnInit } from '@angular/core';
import { finalize } from 'rxjs';
import { UserDetailsResponse } from 'src/app/models/user.interface';
import { AuthService } from 'src/app/services/auth.service';
import { UserService} from 'src/app/services/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],

})
export class HomeComponent implements OnInit{
  currentUser :UserDetailsResponse = {} as UserDetailsResponse

  constructor(private userService: UserService){}

  ngOnInit(): void {
    this.getCurrentUser()
  }
  

  getCurrentUser(){
    this.userService.getCurrentUser().subscribe((res) =>{
      this.currentUser = res
      // TODO Intentar pillar las recetas del usuario mediante finalize
        // finalize(()=>{
          
        // })

    })
  }

}

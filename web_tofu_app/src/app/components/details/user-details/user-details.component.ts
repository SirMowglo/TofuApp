import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, ReplaySubject } from 'rxjs';
import { AuthService } from 'src/app/services/auth.service';
import { FileService } from 'src/app/services/file.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrls: ['./user-details.component.css'],
})
export class UserDetailsComponent implements OnInit {
  @Input() name = 'Prueba';
  @Input() username = 'Prueba';
  @Input() description = 'Prueba';
  @Input() birthday = 'Prueba';
  @Input() registerDate = 'Prueba';

  //** AVATAR OBSERVABLE
  private _avatar = new BehaviorSubject<string>('koalaSad.jpg');
  @Input() set avatar(value: string) {
    this._avatar.next(value);
  }
  get avatar() {
    return this._avatar.getValue();
  }

  avatarData = '';

  constructor(
    private fileService: FileService,
    private authService: AuthService,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this._avatar.subscribe((val) => {
      this.getImage(val);
    });
  }

  getImage(avatar: string) {
    if (avatar != null) {
      this.fileService
        .getData(avatar)
        .subscribe((res) => (this.avatarData = res));
    }
  }

  deleteSelectedUser() {
    if (this.authService.authUser == this.username) {
      this.userService.deleteUserByUsername("me").subscribe(() => {
        this.authService.logout()
      });
    }else{
      this.userService.deleteUserByUsername(this.username).subscribe(() => {
        this.reloadCurrentRoute();
      });
    }
  }

  reloadCurrentRoute() {
    const currentUrl = this.router.url;
    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.router.navigate([currentUrl]);
    });
  }
}

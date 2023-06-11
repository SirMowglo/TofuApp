import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { CreateUserRequest } from 'src/app/models/user.interface';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-register-dialog',
  templateUrl: './register-dialog.component.html',
  styleUrls: ['./register-dialog.component.css'],
})
export class RegisterDialogComponent {
  formGroup = new FormGroup({
    username: new FormControl('', [
      Validators.required,
      Validators.maxLength(16),
    ]),
    fullname: new FormControl('', [
      Validators.required,
      Validators.maxLength(32),
    ]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(8),
      Validators.pattern(/^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])/)
    ]),
    verifyPassword: new FormControl('', [
      Validators.required,
      Validators.maxLength(8),
    ]),
    email: new FormControl('', [
      Validators.required,
      Validators.maxLength(64),
      Validators.email,
    ]),
    verifyEmail: new FormControl('', [
      Validators.required,
      Validators.maxLength(64),
      Validators.email,
    ]),
    isAdmin: new FormControl(false, [])
  });

  usernameErrors: Record<string,string> = {
    required: "The username can't be empty",
    maxlength:"The username can't be longer than 16 characters"
  }
  fullnameErrors: Record<string,string> = {
    required: "The fullname can't be empty",
    maxlength:"The fullname can't be longer than 32 characters"
  }
  passwordErrors: Record<string,string> = {
    required: "The password field can't be empty",
    minlength: "The password needs to be atleast 8 characters long",
    pattern: "The password needs to contain upper and lower case and numbers"
  }
  emailErrors: Record<string,string> = {
    required: "The email field can't be empty",
    maxlength: "The email can't be longer than 64 characters",
    email: "The email needs to be a proper email"
  }

  constructor(
    private router: Router,
     private authService: AuthService,
     public dialogRef: MatDialogRef<RegisterDialogComponent>,

     ) {}

  register() {
    const userRequest: CreateUserRequest = {
      username: this.formGroup.value.username ?? '',
      fullname: this.formGroup.value.fullname ?? '',
      password: this.formGroup.value.password ?? '',
      verifyPassword: this.formGroup.value.verifyPassword ?? '',
      email: this.formGroup.value.email ?? '',
      verifyEmail: this.formGroup.value.verifyEmail ?? '',
    };
    if(this.formGroup.value.isAdmin){
      this.authService.registerAdmin(userRequest).subscribe(() =>{
        this.reloadCurrentRoute();
        this.dialogRef.close()
      })
    }else{
      this.authService.registerUser(userRequest).subscribe(() =>{
        this.reloadCurrentRoute();
        this.dialogRef.close()
      })
    }
  }

  reloadCurrentRoute() {
    const currentUrl = this.router.url;
    this.router.navigateByUrl('/', { skipLocationChange: true }).then(() => {
      this.router.navigate([currentUrl]);
    });
  }
}

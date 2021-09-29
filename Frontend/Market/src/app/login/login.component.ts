import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { Error } from '../error';
import { User } from '../user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  LoginForm: FormGroup;
  ErrorMsg:any;
  user:User=new User();
  constructor(private authService:AuthService, private router:Router) { }

  ngOnInit(): void {
    this.fillForm();
  }

  fillForm(){
    this.LoginForm = new FormGroup({
      email: new FormControl('',Validators.compose([Validators.required,Validators.email])),
      password: new FormControl('',Validators.required)
    });
  }

  login(){
    if(this.LoginForm.valid){
      this.authService.login(this.LoginForm.value).subscribe(
        (data:User) => {
          this.user=new User();
          this.user.token=data.token;
          this.user.firstName=data.firstName;
          this.user.lastName=data.lastName;
          localStorage.setItem("Authorization","Bearer "+data.token);
          console.log(localStorage.getItem('Authorization'));
          this.router.navigate([`/product`]);
      },
      (error:Error) => {
         alert("Incorrect email or password.");
        }
      )}
  }

  openRegistrationPage(){
    console.log("navigating to registration page");
    this.router.navigate([`/register`]);
  }

}

import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { Error } from '../error';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  RegistrationForm: FormGroup;
  constructor(private authService:AuthService, private router:Router) {}

  ngOnInit(): void {
    this.fillForm();
  }
  fillForm(){
    this.RegistrationForm = new FormGroup({
      email: new FormControl('',Validators.compose([Validators.required,Validators.email])),
      password: new FormControl('',Validators.required),
      firstName: new FormControl('',Validators.required),
      lastName: new FormControl('',Validators.required)
    });
  }

  register(){
    if(this.RegistrationForm.valid){
      this.authService.register(this.RegistrationForm.value).subscribe(
        (data) => {
          alert("User Successfully registered.");
          this.router.navigate([`/login`]);
      },
      (error:Error) => {
         alert("registration unsuccessful.");
        }
      )}
  }
  moveToLogin(){
    this.router.navigate([`/login`]);
  }

}

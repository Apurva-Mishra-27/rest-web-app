import { Component, OnInit, Input } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Product } from '../product';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-add-product',
  templateUrl: './add-product.component.html',
  styleUrls: ['./add-product.component.css']
})
export class AddProductComponent implements OnInit {
  @Input() name;
  AddProductForm: FormGroup;
  prod: Product= new Product

  constructor(public activeModal: NgbActiveModal, private productService: ProductService,
    private router: Router) {}

  ngOnInit(): void {
    this.AddProductForm= new FormGroup({
      'productId': new FormControl(null,Validators.required),
      'productCategory': new FormControl(null,Validators.required),
      'productName': new FormControl(null,Validators.required),
      'productDescription': new FormControl(null,Validators.required),
      'units': new FormControl(null,Validators.required),
    })
  }

  addProduct() {
    if(this.AddProductForm.valid){
    this.productService
    .createProduct(this.AddProductForm.value).subscribe(data => {
      console.log(data)
      this.activeModal.close();
      this.gotoList();
    }, 
    error => console.log(error));
  }
}

  gotoList() {
    this.router.navigate(['/product']);
    window.location.reload();
  }
}

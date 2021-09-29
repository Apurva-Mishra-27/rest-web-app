import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Product } from '../product';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-update-modal',
  templateUrl: './update-modal.component.html',
  styleUrls: ['./update-modal.component.css']
})
export class UpdateModalComponent implements OnInit {

  @Input() prod;
  @Input() id;
  UpdateProductForm:FormGroup;

  constructor(public activeModal: NgbActiveModal, private productService:ProductService,private route: ActivatedRoute,private router: Router ) { }

  ngOnInit(): void {
    this.prod = new Product();
    
    this.productService.getProductById(this.id)
      .subscribe(data => {
        console.log(data);
        this.prod = data;
      }, error => console.log(error));
    
      
      this.UpdateProductForm= new FormGroup({
        'productId': new FormControl(null),
        'productCategory': new FormControl(null),
        'productName': new FormControl(null),
        'productDescription': new FormControl(null),
        'units': new FormControl(null)
      })
     
  }

  updateProduct(id: string) {
    this.UpdateProductForm.value.productId=id;
    
    if(!this.UpdateProductForm.dirty)
    alert("No changes to save");
    if(this.UpdateProductForm.valid && this.UpdateProductForm.dirty)
    {
      console.log(this.UpdateProductForm.value);
      if(this.UpdateProductForm.value.productCategory===null)
        this.UpdateProductForm.value.productCategory=this.prod.productCategory;
      if(this.UpdateProductForm.value.productName===null)
        this.UpdateProductForm.value.productName=this.prod.productName;
      if(this.UpdateProductForm.value.productDescription===null)
        this.UpdateProductForm.value.productDescription=this.prod.productDescription;
      if(this.UpdateProductForm.value.units===null)
        this.UpdateProductForm.value.units=this.prod.units;
      
      
      this.productService.updateProduct(this.id,this.UpdateProductForm.value)
      .subscribe(
        data => {
          console.log(data);
          window.location.reload();
        },
        error => console.log(error));
      }
  }

}

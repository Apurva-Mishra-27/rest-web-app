import { Component, OnInit } from '@angular/core';
import { Product } from '../product';
import { ProductService } from '../product.service';
import { AddProductComponent } from '../add-product/add-product.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DeleteModalComponent } from '../delete-modal/delete-modal.component';
import { UpdateModalComponent } from '../update-modal/update-modal.component';
import { ActivatedRoute } from '@angular/router';



@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})

export class ProductComponent implements OnInit {

  products:Product[];

  searchCategory:string="";
  displayedColumns: string[] = ['productId', 'productCategory', 'productName', 'productDescription','units','actions'];
  constructor(private productService:ProductService, private modalService: NgbModal, private route: ActivatedRoute) { }

  ngOnInit(): void {
    console.log("before call");
    this.productService.getProducts().subscribe((data: Product[]) => {
      this.products=data;
    });
  }
  //@ViewChild("addProductModal") addProductModal: AddProductComponent;
  open() {
    this.modalService.open(AddProductComponent);
  }  
  
  delete(product: Product) {
    const modalRef = this.modalService.open(DeleteModalComponent);
    modalRef.componentInstance.name = product.productName;
    modalRef.componentInstance.id=product.productId;
  }

  update(product: Product) {
    const modalRef = this.modalService.open(UpdateModalComponent);
    modalRef.componentInstance.id=product.productId;
  }


}

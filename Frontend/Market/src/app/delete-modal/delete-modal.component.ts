import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ProductService } from '../product.service';


@Component({
  selector: 'app-delete-modal',
  templateUrl: './delete-modal.component.html',
  styleUrls: ['./delete-modal.component.css']
})
export class DeleteModalComponent implements OnInit {

  @Input() name;
  @Input() id;

  constructor(public activeModal: NgbActiveModal, private productService:ProductService,
    private router: Router) {}

  ngOnInit(): void {
  }
  
  deleteProduct(id: String) {
    this.productService.deleteProduct(this.id)
      .subscribe(
        data => {
          
        },
        error => console.log(error));
        this.router.navigate(['/product']);
          window.location.reload();
  }
  

}

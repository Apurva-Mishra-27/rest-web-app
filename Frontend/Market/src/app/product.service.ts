import {  Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from './product';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private baseUrl = "http://localhost:8080/product";
  
  authToken:String;
  constructor(private http:HttpClient) { }

  
  getProducts(): Observable<Product[]>{
    this.authToken=localStorage.getItem('Authorization');
    return this.http.get<Product[]>(`${this.baseUrl}`,{
      headers: {'Authorization':`${this.authToken}`}
   });
  }

  createProduct(product: Object): Observable<Object> {
    this.authToken=localStorage.getItem('Authorization');
    console.log(product);
    return this.http.post(`${this.baseUrl}`, product,{
      headers: {'Authorization':`${this.authToken}`}
    });
  }

  updateProduct(id: string, value: any): Observable<Object> {
    this.authToken=localStorage.getItem('Authorization');
    console.log(value);
    return this.http.put(`${this.baseUrl}/${id}`, value,{
      headers: {'Authorization':`${this.authToken}`}
    });
  }

  deleteProduct(id: string): Observable<any> {
    this.authToken=localStorage.getItem('Authorization');
    return this.http.delete(`${this.baseUrl}/${id}`,{
      headers: {'Authorization':`${this.authToken}`, responseType: 'text' }
    });
  }

  getProductById(id: string): Observable<Product[]>{
    this.authToken=localStorage.getItem('Authorization');
    return this.http.get<Product[]>(`${this.baseUrl}/${id}`,{
      headers: {'Authorization':`${this.authToken}`}});
  }

  getProductByCategory(category: string):Observable<Product[]>{
    this.authToken=localStorage.getItem('Authorization');
    return this.http.get<Product[]>(`${this.baseUrl}/${category}`,{
      headers: {'Authorization':`${this.authToken}`}
    });
  }


}

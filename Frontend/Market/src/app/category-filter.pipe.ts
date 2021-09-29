import { Pipe, PipeTransform } from '@angular/core';
import { Product } from './product';

@Pipe({
  name: 'categoryFilter'
})
export class CategoryFilterPipe implements PipeTransform {

  transform(value: Product[], cName: string): Product[] {
    if(cName===""){
      return value;
    }
    const productArray:Product[]=[];
    for(let i=0;i<value.length;i++)
    {
      let catName:string= value[i].productCategory;
      if(catName.startsWith(cName))
        productArray.push(value[i])
    }
    return productArray;
  }

}

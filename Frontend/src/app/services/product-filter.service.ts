// services/product-filter.service.ts
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ProductFilterService {
  private filterByDescriptionSource = new Subject<string>();

  filterByDescription$ = this.filterByDescriptionSource.asObservable();

  filterProductsByDescription(description: string) {
    this.filterByDescriptionSource.next(description);
  }
}

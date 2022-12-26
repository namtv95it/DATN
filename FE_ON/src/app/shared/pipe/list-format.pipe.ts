import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'currencyVND'
})
export class ListFormatPipe implements PipeTransform {

  transform(value: any ):any {
    return new Intl.NumberFormat('de-DE', { style: 'currency', currency: 'VND' }).format(value);
  }

}

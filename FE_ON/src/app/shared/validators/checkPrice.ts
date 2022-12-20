import {AbstractControl} from "@angular/forms";

export function checkPrice( a:AbstractControl ) {
   const c = a.value ;
   if( c.max == null || c.min == null|| c.min == '' || c.max ==''  ){
      return null ;
   }
   return ( Number(c.min) >= Number(c.max) ) ? {checkprice: true} : null ;
}

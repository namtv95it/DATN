import {AbstractControl, FormGroup} from '@angular/forms';

export const Regex = {
    unicode: '[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s]+(([\',. -][a-zA-Z ])?[a-zA-Z]*)*$',
    unicodeAndNumber: '[a-zA-ZÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯĂẠẢẤẦẨẪẬẮẰẲẴẶẸẺẼỀỀỂẾưăạảấầẩẫậắằẳẵặẹẻẽềềểếỄỆỈỊỌỎỐỒỔỖỘỚỜỞỠỢỤỦỨỪễệỉịọỏốồổỗộớờởỡợụủứừỬỮỰỲỴÝỶỸửữựỳỵỷỹ\\s0-9]+(([\',. -][a-zA-Z ])?[a-zA-Z]*)*$',
    email: '^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$',
    phone: '^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$',
    codeColor:'\\#(\\w+){6,}'
}

export function MustMatch(controlName: string, matchingControlName: string) {
    return (formGroup: FormGroup) => {
        const control = formGroup.controls[controlName];
        const matchingControl = formGroup.controls[matchingControlName];

        if (control.value !== matchingControl.value) {
            matchingControl.setErrors({ mustMatch: true });
        } else {
            matchingControl.setErrors(null);
        }
    }
}

export function compareDate( startDate: string , endDate: string ){
    return ( formGroup: FormGroup ) => {
        const startDateControl = formGroup.controls[startDate] ;
        const endDateControl = formGroup.controls[endDate] ;

        const a = new Date(startDateControl.value)
        const b = new Date(endDateControl.value )

        if( a >= b ){
            endDateControl.setErrors({compareDate: true});
        }else {
            endDateControl.setErrors(null) ;
        }
    }
}

export function checkDiscount( c: AbstractControl ){
    const b = c.value ;
    if( b < 0 || b > 100){
        return {isDiscount: true}
    }
    return null ;
}

export function checkSpace( c: AbstractControl ) {
    return ( c.value.trim() == '' ) ? {
        isSpace: true
    }: null ;
}

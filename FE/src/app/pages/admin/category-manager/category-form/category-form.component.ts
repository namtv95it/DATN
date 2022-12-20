import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {Constant} from '../../../../shared/constants/Constant';
import {CategoryService} from '../../../../shared/service/category/category.service';
import {ToastrService} from 'ngx-toastr';

@Component({
    selector: 'category-form',
    templateUrl: './category-form.component.html',
    styleUrls: ['./category-form.component.scss']
})
export class CategoryFormComponent implements OnInit {


    isLoadingButton: boolean = false;
    title: string = '';
    images: any [] = [];

    formGroup = this.fb.group({
        id: [''],
        name: ['', []],
        image: [''],
        createDate: new Date(),
        status: [1]
    })


    constructor(private readonly fb: FormBuilder,
                private readonly matDialogRef: MatDialogRef<CategoryFormComponent>,
                @Inject(MAT_DIALOG_DATA) public dataDialog: any,
                private categoryService: CategoryService,
                private toastService: ToastrService) {
    }

    ngOnInit(): void {
        if (this.dataDialog.type === Constant.TYPE_DIALOG.NEW) {
            this.title = 'Thêm mới danh mục';
        } else {
            this.title = 'Cập nhật danh mục';
            this.formGroup.patchValue(this.dataDialog.row);
        }
    }

    save() {
        this.formGroup.markAllAsTouched();
        if (this.formGroup.invalid) {
            return;
        }

        if (this.dataDialog.type === Constant.TYPE_DIALOG.NEW) {
            this.categoryService.createCategory(this.formGroup.getRawValue());
        } else {
            this.categoryService.updateCategory(this.formGroup.getRawValue());
        }

        this.categoryService.isCloseDialog.subscribe(value => {
            if (value) {
                this.matDialogRef.close(Constant.RESULT_CLOSE_DIALOG.SUCCESS);
                this.categoryService.isCloseDialog.next(false);
            }
            this.isLoadingButton = false;
        })
    }

    onDismiss() {
        this.matDialogRef.close(Constant.RESULT_CLOSE_DIALOG.CLOSE);
    }

    onChangeImage(event: any) {
        this.images = event.addedFiles;
    }

    onRemove(f: any) {
        this.images.splice(this.images.indexOf(f), 1);
    }
}

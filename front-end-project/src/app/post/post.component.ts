import { Component, OnInit } from '@angular/core';
import { FormGroup,  FormBuilder,  Validators } from '@angular/forms';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.css']
})
export class PostComponent implements OnInit {
    postForm: any;

    constructor(private formBuilder: FormBuilder) {
        this.postForm = this.formBuilder.group({
            title: ['', [Validators.required, Validators.maxLength(20)]],
            media: ['', Validators.required ],
            category: ['', Validators.required ],
            front_image: ['', Validators.required ],
            description: ['', Validators.required ]
        });
    }

    ngOnInit() {
    }

  savePost() {
    if (this.postForm.dirty && this.postForm.valid) {
      alert(`Name: ${this.postForm.value.name} Email: ${this.postForm.value.media}`);
    }
  }



}


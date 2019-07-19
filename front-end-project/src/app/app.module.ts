import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AngularFontAwesomeModule } from 'angular-font-awesome';

import { AppRoutingModule } from './app-routing.module';
import {Routes, RouterModule} from '@angular/router';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { CategoryComponent } from './category/category.component';
import { PostComponent } from './post/post.component';
import { NavComponent } from './nav/nav.component';
import { FooterComponent } from './footer/footer.component';
import { UserComponent } from './user/user.component';
import { NalaviliGeeComponent } from './nalavili-gee/nalavili-gee.component';
import { PurwaGarbaniComponent } from './purwa-garbani/purwa-garbani.component';
import { GarbaniComponent } from './garbani/garbani.component';
import { BilidaComponent } from './bilida/bilida.component';
import { PunchiPuthaComponent } from './punchi-putha/punchi-putha.component';
import { ContactComponent } from './contact/contact.component';

const routes: Routes = [
  { path : 'home', component : HomeComponent},
  { path : 'category', component : CategoryComponent},
  { path : 'user', component : UserComponent},
  { path : 'post', component : PostComponent},
  { path : 'nalaviliGee', component : NalaviliGeeComponent},
  { path : 'purwaGarbani', component : PurwaGarbaniComponent},
  { path : 'garbani', component : GarbaniComponent},
  { path : 'bilida', component : BilidaComponent},
  { path : 'punchiPutha', component : PunchiPuthaComponent},
  { path : 'contact', component : ContactComponent},

];

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    CategoryComponent,
    PostComponent,
    NavComponent,
    FooterComponent,
    UserComponent,
    NalaviliGeeComponent,
    PurwaGarbaniComponent,
    GarbaniComponent,
    BilidaComponent,
    PunchiPuthaComponent,
    ContactComponent
  ],
  imports: [
    AngularFontAwesomeModule,
    BrowserModule,
    AppRoutingModule,
    RouterModule.forRoot(routes)
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }

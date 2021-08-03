import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-like-dislike',
  templateUrl: './like-dislike.component.html',
  styleUrls: ['./like-dislike.component.css']
})
export class LikeDislikeComponent implements OnInit {

  numberOfLike:number = 100 ;
  numberOfDislike :number =25 ;
   
  countLike : number = 0 ;
  countDislike : number = 0
  constructor() { }

  ngOnInit() {
  }
  addLike(){
if(this.countLike ==0) {
  this.numberOfLike = this.numberOfLike +1 

  this.countLike ++ ;
}
else if(this.countLike !== 0){
  this.numberOfLike =this.numberOfLike -1
  this.countLike = 0 ;
}

if(this.countDislike > 0){
  this.numberOfDislike=this.numberOfDislike-1
}

  }

  addDislike(){
    if(this.countDislike ==0) {
      this.numberOfDislike = this.numberOfDislike +1 
    
      this.countDislike ++ ;
    }
    else if(this.countDislike !== 0){
      this.numberOfDislike =this.numberOfDislike -1
      this.countDislike = 0 ;
    }
    if(this.countLike > 0){
      this.numberOfLike=this.numberOfLike-1
    }
  }
  
}

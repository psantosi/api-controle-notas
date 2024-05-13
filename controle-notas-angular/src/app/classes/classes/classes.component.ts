import { Component, OnInit } from '@angular/core';
import { Class } from '../model/class';

@Component({
  selector: 'app-classes',
  templateUrl: './classes.component.html',
  styleUrls: ['./classes.component.scss']
})
export class ClassesComponent implements OnInit {

  classes: Class[] = [];
  displayedColumns = ['name'];

  constructor() { }

  ngOnInit(): void {
  }

}

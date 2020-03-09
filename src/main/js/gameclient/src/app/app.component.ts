import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { GameState } from './game-state';
import { GameService } from './game.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'app';

  gameState: GameState;
  isStateAvailable: boolean = false;
  gameForm: FormGroup;

  constructor(private gameService: GameService) {
      this.title = 'Board game';
      this.gameForm = new FormGroup({
          pit: new FormControl('')
      });
  }

  ngOnInit() {
      this.gameService.getGameState().subscribe(data => {
        this.gameState = data;
        console.log(this.gameState);
        this.isStateAvailable = true;
      });
  }

  onSubmit() {
      console.warn('Your order has been submitted', this.gameForm.value.pit);
      this.gameService.nextRound(this.gameForm.value.pit).subscribe(result => {
        this.gameState = result;
        console.log(result);
      });
  }
}

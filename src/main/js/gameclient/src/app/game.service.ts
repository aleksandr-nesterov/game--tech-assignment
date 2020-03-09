import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { GameState } from './game-state';
import { NextRound } from './next-round';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class GameService {

  private baseUrl: string;

  constructor(private http: HttpClient) {
    this.baseUrl = 'http://localhost:8080/game';
  }

  public getGameState(): Observable<GameState> {
    return this.http.get<GameState>(this.baseUrl);
  }

  public nextRound(pit: number) {
      var nextRound = new NextRound();
      nextRound.pit = pit;
      return this.http.put<number>(this.baseUrl, nextRound);
    }

}

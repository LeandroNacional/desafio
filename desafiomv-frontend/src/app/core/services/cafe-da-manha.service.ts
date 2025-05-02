import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CafeDaManha } from '../models/cafe-da-manha.model';

@Injectable({ providedIn: 'root' })
export class CafeDaManhaService {
  private apiUrl = 'https://desafio-production.up.railway.app/api/cafes';

  constructor(private http: HttpClient) {}

  getAll(): Observable<CafeDaManha[]> {
    return this.http.get<CafeDaManha[]>(this.apiUrl);
  }

  create(cafe: CafeDaManha): Observable<CafeDaManha> {
    return this.http.post<CafeDaManha>(this.apiUrl, cafe);
  }

  cadastrarParticipacao(cafeId: number, payload: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/${cafeId}/participantes`, payload);
  }

  getParticipantesDetalhado(cafeId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/${cafeId}/participantes`);
  }
}

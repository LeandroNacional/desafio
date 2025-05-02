import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Opcao } from '../models/opcao.model';

@Injectable({ providedIn: 'root' })
export class OpcaoService {
  private apiUrl = 'desafio-production.up.railway.app/api/opcoes';

  constructor(private http: HttpClient) {}

  getAllByCafe(cafeId: number): Observable<Opcao[]> {
    return this.http.get<Opcao[]>(`${this.apiUrl}/cafes/${cafeId}/opcoes`);
  }

  marcarStatus(id: number, trouxe: boolean): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}/status`, { trouxe });
  }

  create(opcao: Opcao): Observable<Opcao> {
    return this.http.post<Opcao>(this.apiUrl, opcao);
  }

  atualizarStatus(id: number, trouxe: boolean) {
    return this.http.put(`${this.apiUrl}/${id}/status`, { trouxe });
  }

  getOpcoesPorColaborador(colaboradorId: number) {
    return this.http.get<Opcao[]>(`${this.apiUrl}/colaborador/${colaboradorId}/opcoes`);
  }
}

import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CafeDaManhaService } from '../../core/services/cafe-da-manha.service';
import { CafeDaManha } from '../../core/models/cafe-da-manha.model';
import { RouterModule, Router } from '@angular/router';
import { HeaderComponent } from '../../shared/header/header.component';

@Component({
  selector: 'app-lista',
  standalone: true,
  imports: [CommonModule, RouterModule, HeaderComponent],
  templateUrl: './lista.component.html',
  styleUrls: ['./lista.component.scss'],
})
export class ListaComponent implements OnInit {
  cafes: CafeDaManha[] = [];
  loading = true;
  erro: string | null = null;

  constructor(
    private cafeService: CafeDaManhaService,
    private router: Router
  ) {}

  ngOnInit() {
    this.cafeService.getAll().subscribe({
      next: (cafes) => {
        this.cafes = cafes;
        this.loading = false;
      },
      error: (err) => {
        this.erro = err.error?.message || 'Erro ao buscar cafés.';
        this.loading = false;
      },
    });
  }

  irParaDetalhe(cafe: CafeDaManha) {
    this.router.navigate(['/cafe', cafe.id]);
  }
}

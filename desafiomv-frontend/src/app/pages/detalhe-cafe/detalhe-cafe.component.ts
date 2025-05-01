import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { CafeDaManhaService } from '../../core/services/cafe-da-manha.service';
import { OpcaoService } from '../../core/services/opcao.service';
import { ColaboradorService } from '../../core/services/colaborador.service';
import { CafeDaManha } from '../../core/models/cafe-da-manha.model';
import { Opcao } from '../../core/models/opcao.model';
import { HeaderComponent } from '../../shared/header/header.component';
import { FormBuilder, FormGroup, Validators, FormArray, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-detalhe-cafe',
  standalone: true,
  imports: [CommonModule, HeaderComponent, ReactiveFormsModule],
  templateUrl: './detalhe-cafe.component.html',
  styleUrls: ['./detalhe-cafe.component.scss'],
})
export class DetalheCafeComponent implements OnInit {
  cafe: CafeDaManha | null = null;
  opcoesBackend: Opcao[] = [];
  loading = true;
  erro: string | null = null;
  participantes: any[] = [];
  participacaoForm: FormGroup;
  participacaoErro: string | null = null;
  participacaoSucesso = false;
  mostrarFormulario = false;

  constructor(
    private route: ActivatedRoute,
    private cafeService: CafeDaManhaService,
    private opcaoService: OpcaoService,
    private fb: FormBuilder
  ) {
    this.participacaoForm = this.fb.group({
      nome: ['', Validators.required],
      cpf: ['', [Validators.required, Validators.pattern(/^\d{11}$/)]],
      opcoes: this.fb.array([this.fb.control('', Validators.required)]),
    });
  }

  get opcoes() {
    return this.participacaoForm.get('opcoes') as FormArray;
  }

  addOpcao() {
    this.opcoes.push(this.fb.control('', Validators.required));
  }

  removeOpcao(i: number) {
    if (this.opcoes.length > 1) {
      this.opcoes.removeAt(i);
    }
  }

  onParticipar() {
    this.participacaoErro = null;
    this.participacaoSucesso = false;
    if (this.participacaoForm.valid) {
      const id = Number(this.route.snapshot.paramMap.get('id'));
      this.cafeService.cadastrarParticipacao(id, this.participacaoForm.value).subscribe({
        next: () => {
          this.participacaoSucesso = true;
          this.participacaoForm.reset();
          this.participacaoForm.setControl(
            'opcoes',
            this.fb.array([this.fb.control('', Validators.required)])
          );
          this.cafeService.getParticipantesDetalhado(id).subscribe({
            next: (participantes) => (this.participantes = participantes),
          });
        },
        error: (err) => {
          this.participacaoErro = err.error?.message || 'Erro ao cadastrar participação.';
        },
      });
    } else {
      this.participacaoForm.markAllAsTouched();
    }
  }

  marcarComoTrouxe(participante: any, indexOpcao: number): void {
    const colaboradorId = participante.id; 
    console.log('ID do colaborador:', colaboradorId);
  
    if (!colaboradorId) {
      console.error('colaboradorId não encontrado');
      return;
    }
  
    this.opcaoService.getOpcoesPorColaborador(colaboradorId).subscribe({
      next: (opcoes) => {
        const cafeId = Number(this.route.snapshot.paramMap.get('id'));
        console.log('id do cafe', cafeId);
  
        // Filtra apenas as opções do café da manhã atual
        const opcoesDoCafeAtual = opcoes.filter(o => o.cafeDaManhaId === cafeId);
  
        const opcao = opcoesDoCafeAtual[indexOpcao];
  
        if (opcao && opcao.id !== undefined) {
          opcao.status = 'TROUXE';
  
          this.opcaoService.atualizarStatus(opcao.id, true).subscribe({
            next: () => {
              console.log('Status da opção atualizado com sucesso!');
              this.cafeService.getParticipantesDetalhado(cafeId).subscribe({
                next: (participantes) => this.participantes = participantes
              });
            },
          });
        } else {
          console.error('Opção não encontrada ou sem ID válido.');
        }
      },
      error: (err) => {
        console.error('Erro ao pegar as opções do colaborador:', err);
      },
    });
  }
  
  
  
  ngOnInit() {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.cafeService.getAll().subscribe({
      next: (cafes) => {
        this.cafe = cafes.find((c) => c.id === id) || null;
        this.loading = false;
      },
      error: (err) => {
        this.erro = err.error?.message || 'Erro ao buscar café.';
        this.loading = false;
      },
    });

    this.opcaoService.getAllByCafe(id).subscribe({
      next: (opcoes) => (this.opcoesBackend = opcoes),
      error: () => (this.opcoesBackend = []),
    });

    this.cafeService.getParticipantesDetalhado(id).subscribe({
      next: (participantes) => (this.participantes = participantes),
      error: () => (this.participantes = []),
    });
  }
}

import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  Validators,
  FormArray,
  AbstractControl,
} from '@angular/forms';
import { CafeDaManhaService } from '../../core/services/cafe-da-manha.service';
import { RouterModule } from '@angular/router';
import { HeaderComponent } from '../../shared/header/header.component';

@Component({
  selector: 'app-cadastro',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule, HeaderComponent],
  templateUrl: './cadastro.component.html',
  styleUrls: ['./cadastro.component.scss'],
})
export class CadastroComponent {
  cadastroForm: FormGroup;
  sucesso = false;
  erro: string | null = null;

  constructor(
    private fb: FormBuilder,
    private cafeService: CafeDaManhaService
  ) {
    console.log('CadastroComponent carregado!');
    this.cadastroForm = this.fb.group({
      nome: ['', Validators.required],
      data: ['', Validators.required],
    });
  }

  get opcoes() {
    return this.cadastroForm.get('opcoes') as FormArray;
  }

  addOpcao() {
    this.opcoes.push(this.fb.control('', Validators.required));
  }

  removeOpcao(index: number) {
    if (this.opcoes.length > 1) {
      this.opcoes.removeAt(index);
    }
  }

  onSubmit() {
    console.log(
      'Tentou enviar!',
      this.cadastroForm.value,
      this.cadastroForm.valid
    );
    this.sucesso = false;
    this.erro = null;
    if (this.cadastroForm.valid) {
      this.cafeService.create(this.cadastroForm.value).subscribe({
        next: () => {
          this.sucesso = true;
          this.cadastroForm.reset();
        },
        error: (err) => {
          this.erro = err.error?.message || 'Erro ao cadastrar café da manhã.';
        },
      });
    } else {
      this.cadastroForm.markAllAsTouched();
    }
  }

  dataMaiorQueHojeValidator(control: AbstractControl) {
    if (!control.value) return null;
    const data = new Date(control.value);
    const hoje = new Date();
    hoje.setHours(0, 0, 0, 0);
    return data > hoje ? null : { dataInvalida: true };
  }

  teste() {
    alert('Funcionou!');
  }
}

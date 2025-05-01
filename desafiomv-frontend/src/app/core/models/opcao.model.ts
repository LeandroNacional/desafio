export interface Opcao {
  id?: number;
  nome: string;
  status: 'TROUXE' | 'NAO_TROUXE';
  colaboradorId: number;
  cafeDaManhaId: number;
}

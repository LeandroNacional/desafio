describe('Página de Detalhe do Café da Manhã', () => {
  it('Deve exibir o nome do café da manhã e permitir participação', () => {
    cy.visit('http://localhost:4200/cafe/2');

    cy.contains('Participar').click();

    cy.get('input[formControlName="nome"]').type('Fulano de Tal');
    cy.get('input[formControlName="cpf"]').type('12345678901');

    cy.get('select[formControlName="0"]').select(0); // depende de como está implementado

    cy.contains('Enviar').click();

    cy.contains('Participação registrada com sucesso').should('exist');
  });
});

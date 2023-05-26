

describe('Test payments', () => {
  it('starts', () => {
    cy.visit('http://127.0.0.1:3000/payments')
  })
  it('pay', () => {
    cy.visit('http://127.0.0.1:3000/payments')
    cy.get('#root > :nth-child(2)').children().its('length')
        .then(length => {
          cy.get(`:nth-child(1) > button`).click()
          cy.get(`#root > :nth-child(2)`).children().should('have.length', length -1)
        })
  })
})
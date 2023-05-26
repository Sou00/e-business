

describe('Test cart', () => {
  it('starts', () => {
    cy.visit('http://127.0.0.1:3000/cart')
  })
  it('cart item deletes', () => {
    cy.visit('http://127.0.0.1:3000')
    for (let i = 1; i < 5; i++) {
      cy.get(`:nth-child(${i}) > button`).click()
      cy.get(`:nth-child(${i}) > button`).click()
    }
    cy.get('[href="/cart"]').click()
    cy.get(`:nth-child(1) > button`).click()

    for (let i = 1; i < 4; i++) {
      cy.get(`#root > :nth-child(2) > :nth-child(${i})`).invoke('text').then((text) => {
        expect(text.slice(8, 22)).equal(`"productId":${i+1},`)
      });
    }
  })
  it('cart items confirm', () => {
    cy.visit('http://127.0.0.1:3000')
    for (let i = 1; i < 5; i++) {
      cy.get(`:nth-child(${i}) > button`).click()
      cy.get(`:nth-child(${i}) > button`).click()
    }
    cy.get('[href="/cart"]').click()

    for (let i = 1; i < 5; i++) {
      cy.get(`#root > :nth-child(2) > :nth-child(${i})`).invoke('text').then((text) => {
        expect(text.slice(8, 22)).equal(`"productId":${i},`)
      });
    }
    cy.get(':nth-child(2) > :nth-child(5)').click()
    cy.get('#root > :nth-child(2)').invoke('text').should('eq','Confirm')
  })
  it('cart items stay', () => {
    cy.visit('http://127.0.0.1:3000')
    for (let i = 1; i < 5; i++) {
      cy.get(`:nth-child(${i}) > button`).click()
      cy.get(`:nth-child(${i}) > button`).click()
    }
    cy.get('[href="/cart"]').click()
    for (let i = 1; i < 5; i++) {
      cy.get(`#root > :nth-child(2) > :nth-child(${i})`).invoke('text').then((text) => {
        expect(text.slice(8, 22)).equal(`"productId":${i},`)
      });
    }
    cy.get('[href="/"]').click()
    cy.get('[href="/payments"]').click()
    cy.get('[href="/cart"]').click()

    for (let i = 1; i < 5; i++) {
      cy.get(`#root > :nth-child(2) > :nth-child(${i})`).invoke('text').then((text) => {
        expect(text.slice(8, 22)).equal(`"productId":${i},`)
      });
    }
    cy.get(':nth-child(2) > :nth-child(5)').click()
    cy.get('#root > :nth-child(2)').invoke('text').should('eq','Confirm')
  })
})


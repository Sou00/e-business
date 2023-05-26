
describe('Test products', () => {
  it('starts', () => {
    cy.visit('http://127.0.0.1:3000')
  })
  it('add product to cart', () => {
    cy.visit('http://127.0.0.1:3000')
    for (let i = 1; i < 5; i++) {
      cy.get(`:nth-child(${i}) > button`).click()
    }
    cy.get('[href="/cart"]').click()
    for (let i = 1; i < 5; i++) {
      cy.get(`#root > :nth-child(2) > :nth-child(${i})`).invoke('text').then((text) => {
        expect(text.slice(8, 22)).equal(`"productId":${i},`)
      });
    }
  })
  it('Check quantity',()=>{
    cy.visit('http://127.0.0.1:3000')
    for (let i = 1; i < 5; i++) {
      cy.get(`:nth-child(${i}) > button`).click()
    }
    cy.get('[href="/cart"]').click()
    for (let i = 1; i < 5; i++) {
      cy.get(`#root > :nth-child(2) > :nth-child(${i})`).invoke('text').then((text) => {
        expect(text.slice(8, 22)).equal(`"productId":${i},`)
        expect(text.slice(22,35)).equal('"quantity":1}')
      });
    }
    cy.get('[href="/"]').click()
    for (let i = 1; i < 5; i++) {
      cy.get(`:nth-child(${i}) > button`).click()
    }
    cy.get('[href="/cart"]').click()
    for (let i = 1; i < 5; i++) {
      cy.get(`#root > :nth-child(2) > :nth-child(${i})`).invoke('text').then((text) => {
        expect(text.slice(8, 22)).equal(`"productId":${i},`)
        expect(text.slice(22,35)).equal('"quantity":2}')
      });
    }

  })
})


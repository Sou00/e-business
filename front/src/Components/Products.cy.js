import React, {useState} from 'react'
import Products from './Products'

describe('<Products />', () => {
  it('renders', () => {
    // see: https://on.cypress.io/mounting-react
    cy.mount(<Products />)
  })

  it('buttons', () => {

    cy.mount(<Products />)

    for (let i = 1; i < 5; i++) {
      cy.get(`:nth-child(${i}) > button`).click()
    }


  })
})
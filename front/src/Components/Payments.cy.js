import React from 'react'
import Payments from './Payments'


describe('<Payments />', () => {
  it('renders', () => {
    // see: https://on.cypress.io/mounting-react
    cy.mount(<Payments />)
  })



})
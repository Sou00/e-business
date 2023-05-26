import React from 'react'
import Payments from './Payments'
import App from "../App";

describe('<Payments />', () => {
  it('renders', () => {
    // see: https://on.cypress.io/mounting-react
    cy.mount(<Payments />)
  })



})
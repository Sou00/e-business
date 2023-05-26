
const product = {
    id: 1,
    name: "testProduct",
    category: "testCategory",
    price: 1.0
}
const category = {
    id: 1,
    name: "testCategory"
}
const order = {
    id: 1,
    productId: 1,
    quantity: 99,
}

const payment = {
    id: 1,
    orderId: 1,
    total: 99.99,
    paid: true
}
let id = 1
describe('Test API', () => {
    it('Products getAll', () => {
       cy.request('http://127.0.0.1:8080/product')
    })
    it('Products getSingle', () => {
        cy.request('http://127.0.0.1:8080/product/1')
    })
    it('Products post', () => {
        cy.request('POST','http://127.0.0.1:8080/product',product).then((res)=>{
            expect(res.body).to.have.property('name',product.name)
            id = res.body.id
        })
    })
    it('Products update', () => {
        product.id = id
        product.name = "testProductUpdated"
        cy.request('PUT','http://127.0.0.1:8080/product',product).then((res)=>{
            expect(res.body).to.have.property('name',product.name)
        })
    })
    it('Products delete', () => {
        cy.request('DELETE',`http://127.0.0.1:8080/product/${id}`).then((res)=>{
            expect(res.body).equals('Deleted successfully!')
        })
    })

    it('Orders getAll', () => {
        cy.request('http://127.0.0.1:8080/order')
    })
    it('Orders getSingle', () => {
        cy.request('http://127.0.0.1:8080/order/1')
    })
    it('Orders post', () => {
        cy.request('POST','http://127.0.0.1:8080/order',order).then((res)=>{
            expect(res.body).to.have.property('quantity',order.quantity)
            id = res.body.id
        })
    })
    it('Orders update', () => {
        order.id = id
        order.quantity = 100
        cy.request('PUT','http://127.0.0.1:8080/order',order).then((res)=>{
            expect(res.body).to.have.property('quantity',order.quantity)
        })
    })
    it('Orders delete', () => {
        cy.request('DELETE',`http://127.0.0.1:8080/order/${id}`).then((res)=>{
            expect(res.body).equals('Deleted successfully!')
        })
    })

    it('Payments getAll', () => {
        cy.request('http://127.0.0.1:8080/payment')
    })
    it('Payments getSingle', () => {
        cy.request('http://127.0.0.1:8080/payment/1')
    })
    it('Payments post', () => {
        cy.request('POST','http://127.0.0.1:8080/payment',payment).then((res)=>{
            expect(res.body).to.have.property('total',payment.total)
            id = res.body.id
        })
    })
    it('Payments update', () => {
        payment.id = id
        payment.total = 99.98
        cy.request('PUT','http://127.0.0.1:8080/payment',payment).then((res)=>{
            expect(res.body).to.have.property('total',payment.total)
        })
    })
    it('Payments delete', () => {
        cy.request('DELETE',`http://127.0.0.1:8080/payment/${id}`).then((res)=>{
            expect(res.body).equals('Deleted successfully!')
        })
    })

})
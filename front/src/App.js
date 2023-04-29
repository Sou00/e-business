import './App.css';
import Products from "./Components/Products";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Cart from "./Components/Cart";
import Payments from "./Components/Payments";
import {useState} from "react";
import Navbar from "./navbar/Navbar";

function App() {

    const [cart, setCart] = useState([]);
    const [payments, setPayments] = useState([])
console.log(cart)
  return (
      <Router>
          <Navbar/>
          <Routes>
                  <Route
                      path="/"
                      element={
                          <Products cart={cart} setCart={setCart}/>
                      }
                  />
                  <Route
                      path="/payments"
                      element={
                          <Payments payments={payments} setPayments={setPayments}/>
                      }
                  />
                  <Route
                      path="/cart"
                      element={
                          <Cart cart={cart} setCart={setCart}/>
                      }
                  />
                  </Routes>

      </Router>

  );
}

export default App;

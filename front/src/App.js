import './App.css';
import Products from "./Components/Products";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Cart from "./Components/Cart";
import Payments from "./Components/Payments";
import {useState} from "react";
import Navbar from "./navbar/Navbar";
import Login from "./Components/Login";
import Register from "./Components/Register";

function App() {

    const [cart, setCart] = useState([]);
    const [payments, setPayments] = useState([])
    const [user,setUser] = useState(null)
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
              <Route
                  path="/login"
                  element={
                      <Login user={user} setUser={setUser} />
                  }
              />
                  <Route
                  path="/register"
                  element={
                      <Register setUser={setUser}/>
                  }
                  />
                  </Routes>

      </Router>

  );
}

export default App;

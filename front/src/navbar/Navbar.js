import React from "react";
import { NavLink as Link } from "react-router-dom";

function Navbar() {
  return (
      <div>
        <Link to="/" className="nav">
             Products
        </Link>
        <Link to="/cart" className="nav">
            Cart
        </Link>
        <Link to="/payments" className="nav">
            Payments
        </Link>
          <Link to="/login" className="nav">
              Login
          </Link>
          <Link to="/register" className="nav">
              Register
          </Link>
      </div>
  );
}

export default Navbar;

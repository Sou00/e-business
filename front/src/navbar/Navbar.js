import React from "react";
import { NavLink as Link } from "react-router-dom";

function Navbar() {
  return (
      <div>
        <Link to="/" >

          Products
        </Link>
        <Link to="/cart" >

        Cart
      </Link>
        <Link to="/payments">

        Payments
      </Link>
      </div>
  );
}

export default Navbar;

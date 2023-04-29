import React from 'react';
import { useState, useEffect } from 'react';
import axios from "axios";
import {Payment} from "../models/Payment";


export default function Cart({cart, setCart}){

    const submit =(cart)=>{
    axios.post('/cart',cart,{headers: {'Content-Type': 'application/json'}}).then(r => console.log(r.data));
    setCart([])
    }
    const deleteItem = (productId) =>{
        setCart(cart.filter(order => order.productId !== productId));
    }
    return ( <div>
            {cart.map((order) => (
                <div>
                    {JSON.stringify(order)}
                    <button onClick={() => deleteItem(order.productId)}>Delete</button>

                </div>
            ))}
            <button onClick={()=>submit(cart)}>Confirm</button>
        </div>
        );
}

import React from 'react';
import axios from "axios";


export default function Cart({cart, setCart}){

    const submit =(cart)=>{
    axios.post(process.env.REACT_APP_BACKEND_URL+'cart',cart,{headers: {'Content-Type': 'application/json'}})
        .then(r => console.log(r.data));
            setCart([])
    }
    const deleteItem = (productId) =>{
        setCart(cart.filter(order => order.productId !== productId));
    }
    if(cart !== undefined)
        return ( <div>
                {cart.map((order) => (
                    <div key={order.id}>
                        {JSON.stringify(order)}
                        <button onClick={() => deleteItem(order.productId)}>Delete</button>

                    </div>
                ))}
                <button onClick={()=>submit(cart)}>Confirm</button>
            </div>
            );
    else return <div>Cart is empty!</div>;
}

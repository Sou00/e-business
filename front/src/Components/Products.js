import React,{useRef, useState, useEffect } from 'react';
import axios from "axios";
import {Order} from "../models/Order";


export default function Products({cart,setCart}){
    const [products, setProducts] = useState([]);
    const [buy, setBuy]= useState(0)
    const [item,setItem]= useState(Order)
    const itemChanged = useRef(false)
    useEffect(() => {
        axios.get(process.env.REACT_APP_BACKEND_URL+'product')
            .then((res) => {
                setProducts(res.data);
            })
            .catch((err) => {
                console.log(err.message);
            });
    }, []);
    useEffect(() => {
        if(itemChanged.current) {
            let order =  {
                id: item.id,
                productId: item.productId,
                quantity: item.quantity
            }
            if (cart.isEmpty)
                setCart(order)
            else
                setCart(cart.concat(order))

            itemChanged.current = false
        }
    }, [buy])
const buyItem = (product)=>{
        if (cart !== undefined) {
            let index = cart.findIndex(p => p.productId === product.id)
            if (index > -1) {
                cart[index].quantity++
            } else {
                let order = Order;
                order.id = 1
                order.productId = product.id;
                order.quantity = 1
                setItem(order)
                itemChanged.current = true
                setBuy(buy + 1)
            }
        }
}
if(products !== undefined)
    return ( <div>{products.map((product) => (
        <div key={product.id}>
            {JSON.stringify(product)}
            <button onClick={() => buyItem(product)}>Buy</button>
        </div>
    ))}
        </div>
        );
else return <div>There are no products!</div>
}

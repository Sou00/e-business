import React from 'react';
import { useState, useEffect } from 'react';
import axios from "axios";
export default function Payments({payments,setPayments}){

    useEffect(() => {
        axios.get('/payment')
            .then((res) => {
                setPayments(res.data.filter(p => !p.paid));
            })
            .catch((err) => {
                console.log(err.message);
            });
    }, []);

    const pay =(payment)=>{
        payment.paid = true;
        axios.put('/payment',payment,{headers: {'Content-Type': 'application/json'}}).then(r => console.log(r.data));
        setPayments(payments.filter(p => p.id !== payment.id))
    }

    return ( <div>{payments.map((payment) => (
            <div>
                {JSON.stringify(payment)}
                <button onClick={()=>pay(payment)}>Pay</button>
            </div>

    ))}
        </div>
        );
}

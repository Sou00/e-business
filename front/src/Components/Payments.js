import React,{ useEffect } from 'react';
import axios from "axios";
export default function Payments({payments,setPayments}){

    useEffect(() => {
        axios.get('http://127.0.0.1:8080/payment')
            .then((res) => {
                setPayments(res.data.filter(p => !p.paid));
            })
            .catch((err) => {
                console.log(err.message);
            });
    }, []);

    const pay =(payment)=>{
        payment.paid = true;
        axios.put('http://127.0.0.1:8080/payment',payment,{headers: {'Content-Type': 'application/json'},method: "PUT"}).then(r => console.log(r.data));
        setPayments(payments.filter(p => p.id !== payment.id))
    }

    if(payments !== undefined)
    return ( <div>{payments.map((payment) => (
            <div key={payment.id}>
                {JSON.stringify(payment)}
                <button onClick={()=>pay(payment)}>Pay</button>
            </div>

    ))}
        </div>
        );
    else return <div>Payments are empty!</div>
}

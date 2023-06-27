import React,{ useEffect } from 'react';
import axios from "axios";
export default function Payments({payments,setPayments}){

    useEffect(() => {
        axios.get(process.env.REACT_APP_BACKEND_URL+'payment')
            .then((res) => {
                setPayments(res.data.filter(p => !p.paid));
            })
            .catch((err) => {
                console.log(err.message);
            });
    }, []);

    const pay =(payment)=>{
        payment.paid = true;
        axios.put(process.env.REACT_APP_BACKEND_URL+'payment',payment,{headers: {'Content-Type': 'application/json'},method: "PUT"}).then(r => console.log(r.data));
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

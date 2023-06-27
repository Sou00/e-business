import React, {useRef, useState} from 'react';
import axios from "axios";


export default function Register({ setUser }) {
    const loginRef = useRef();
    const passwordRef = useRef();
    const [error,setError]= useState(false)
    const [created,setCreated]= useState(false)


    const handleSubmit = (e) => {
        e.preventDefault();
        let user =  {
            id: 1,
            login: loginRef.current.value,
            password: passwordRef.current.value
        }
            axios.post(process.env.REACT_APP_BACKEND_URL+'user',user,{headers: {'Content-Type': 'application/json'}})
                .then((res) => {
                    setUser(res.data)
                    setError(false)
                    setCreated(true)
                })
                .catch((err) => {
                    console.log(err.message);
                    setError(true);
                });
        loginRef.current.value = null
        passwordRef.current.value = null
    }

    return (
        <div>
            <h1>Register</h1>
            <form onSubmit={handleSubmit}>
                <label>Login</label>
                <br/>
                <input type="text" ref={loginRef}></input>
                <br/>
                <label>Password</label>
                <br/>
                <input type="text" ref={passwordRef}></input>
                <br/>
                <label hidden={!error}>Wrong login</label>
                <label hidden={!created}>New account created!</label>
                <br/>
                <button type="submit">Register</button>
            </form>
        </div>
    );
}

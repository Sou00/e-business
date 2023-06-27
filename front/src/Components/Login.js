import React, {useRef, useState} from 'react';
import axios from "axios";


export default function Login({user, setUser }) {
    const loginRef = useRef();
    const passwordRef = useRef();
    const [error,setError]= useState(false)

    function checkUser() {
        if (user == null) {
            return (
                <div>
                    <h1>Log In</h1>
                    <form onSubmit={handleSubmit}>
                        <label>Login</label>
                        <br/>
                        <input type="text" ref={loginRef}></input>
                        <br/>
                        <label>Password</label>
                        <br/>
                        <input type="text" ref={passwordRef}></input>
                        <br/>
                        <label hidden={!error}>No user or wrong password!</label>
                        <br/>
                        <button type="submit">Log in</button>
                    </form>
                </div>
            );
        } else {
            return (
                <div>
                    You are logged in as {user.login}
                </div>
            );
        }
    }
    const handleSubmit = (e) => {
        e.preventDefault();
            axios.get(process.env.REACT_APP_BACKEND_URL+`user/${loginRef.current.value}`)
                .then((res) => {
                    console.log(res.data)
                    if(res.data.id != null)
                        setUser(res.data);
                    else{
                        setError(true)
                    }
                })
                .catch((err) => {
                    console.log(err.message);
                    setError(true)
                });
    }

    return (
        checkUser()
    );
}

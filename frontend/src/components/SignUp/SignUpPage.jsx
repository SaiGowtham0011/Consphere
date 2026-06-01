import {Link} from 'react-router-dom'
import { useState,useEffect } from 'react'
import countries from '../../data/CountryCodes.json'
import './SignUpPage.css'
import banner from '../../assets/loginbanner.png'

function SignUp(){
    //const [countries,setCountries] = useState([])
    //const [phone, setPhone] = useState('');


    /*useEffect(()=>{
        axios.get("https://apihut.in/api/country/phone-codes")
            .then((response)=>{
                setCountries(response.data.data)
            })
            .catch((error)=>{
                console.log(error)
            })
    },[]);*/
    return(
        <div className='container'>
            <div className='left-side'>
                <div className='SignUp-box'>
                    <h1 className='heading'>Sign Up</h1>
                    <div className='user-input'>
                        <input className='user-box' placeholder="Enter username"></input>
                    </div>
                    <div className='mobileNo'>
                        <select className='countryyOptions'>
                            {countries.map((country)=>(
                                <option key={country.name} >``
                                    {country.code} ({country.dial_code})
                                </option>
                            ))}
                        </select>
                        {/*<PhoneInput
                            country={'in'}
                            value={phone}
                            onChange={setPhone}
                            inputStyle={{
                                width: '370px',
                                height: '55px'
                            }}
                        />*/}
                        <input className='mobileNo-box' type='tel' placeholder="10-digit Mobile Number"></input>
                    </div>
                    <div className='email-input'>
                        <input className='user-box' placeholder="Enter email"></input>
                    </div>
                    <div className='password-input'>
                        <input className='pass-box' type='password' placeholder="Create password"></input>
                    </div>
                    <div className='SignUp-btn'>
                        <button>Sign Up</button>
                    </div>
                    <div className='oldAcc'>
                        <p>Didn't have an account ?</p>
                        <Link to="/login">Login</Link>
                    </div>
                </div>
            </div>
            <div className='right-side'>
                <img src={banner} alt='Login Banner'></img>
            </div>
        </div>
    )
}
export default SignUp
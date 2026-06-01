import {Link} from 'react-router-dom'
import './LoginPage.css'

function Login(){
    return(
        <div className='container'>
            <div className='login-box'>
                <h3>Login</h3>
                <div className='login-input'>
                    <input placeholder="enter username"></input>
                    <input placeholder="enter password"></input>
                </div>
                <div className='login-btn'>
                    <button>Login</button>
                </div>
                <div className='newAcc'>
                    <p>Didn't have an account ?</p>
                    <Link to="/signin">Sign In</Link>
                </div>
            </div>
        </div>
    )
}
export default Login
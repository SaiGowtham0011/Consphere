import {Link} from 'react-router-dom'
import './LoginPage.css'
import banner from '../../assets/loginbanner.png'

function Login(){
    return(
        <div className='container'>
            <div className='left-side'>
                <div className='login-box'>
                    <h1 className='heading'>Login</h1>
                    <div className='user-input'>
                        <input id='user-box' placeholder="enter username"></input>
                    </div>
                    <div className='password-input'>
                        <input id='pass-box' placeholder="enter password"></input>
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
            <div className='right-side'>
                <img src={banner} alt='Login Banner'></img>
            </div>
        </div>
    )
}
export default Login
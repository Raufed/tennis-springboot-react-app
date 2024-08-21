import React, { Component } from 'react';
import axios from 'axios';
import './RegisterComponent.css'; 
class RegisterComponent extends Component {
    constructor(props) {
      super(props);
      this.state = {
        email: '',
        password: '',
        errorMessage: '',
        successMessage: ''
      };
    }

    handleSubmit = async (e) => {
      e.preventDefault();
      const { email, password } = this.state;
            
      try {
        //const csrfToken = document.cookie.replace(/(?:(?:^|.*;\s*)XSRF-TOKEN\s*\=\s*([^;]*).*$)|^.*$/, '$1');
        const response = await axios.post('http://localhost:8080/api/v1/auth/singUp', {
          email: email,
          password: password
        }
        //,{headers:{'X-XSRF-TOKEN': csrfToken}}
      );
        this.setState({ successMessage: 'Registration successful!', errorMessage: '' });
        console.log('Registration response:', response.data);
        localStorage.setItem('email', email);
      } catch (error) {
        this.setState({ errorMessage: 'Registration failed. Please try again.', successMessage: '' });
        console.error('Registration error:', error);
      }
    };
  
    handleChange = (e) => {
      this.setState({ [e.target.name]: e.target.value });
    };
  
    render() {
      const {  email, password, errorMessage, successMessage } = this.state;
  
      return (
        <div className="RegisterContainer">
          <h1>Зарегистрироваться</h1>
          <form onSubmit={this.handleSubmit}>
            <label>
              Адрес электронной почты:
              <input 
                type="email" 
                name="email" 
                value={email} 
                onChange={this.handleChange} 
                required
              />
            </label>
            <label>
              Пароль:
              <input 
                type="password" 
                name="password" 
                value={password} 
                onChange={this.handleChange} 
                required
              />
            </label>
            <a href="/login" className="nav-link">Войти</a>
            <button type="submit">Зарегистрироваться</button>
          </form>
          {errorMessage && <p className="error-text">{errorMessage}</p>}
          {successMessage && <p className="success-text">{successMessage}</p>}
        </div>
      );
    }
}

export default (RegisterComponent);
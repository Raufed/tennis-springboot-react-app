import React, { Component } from "react";
import { Navbar, NavbarBrand } from "reactstrap";
import './AppNavbar.css';
import { Link } from "react-router-dom";

export default class AppNavbar extends Component {
    constructor(props) {
        super(props);
        this.state = { isOpen: false };
        this.toggle = this.toggle.bind(this);
    }

    toggle() {
        this.setState({
            isOpen: !this.state.isOpen
        })
    }

    render() {
        return (

            <Navbar color="dark" dark expand="md">
                {/*
<NavbarBrand ><a href="/" className="nav-link">Главная</a></NavbarBrand>
                */}
                <NavbarBrand>
                    <a href="/dishes" className="nav-link">Меню</a>
                </NavbarBrand>
                <NavbarBrand className="in_right">
                    <a href="/login" className="nav-link">Войти</a>
                </NavbarBrand>
            </Navbar>
        )
    }
}
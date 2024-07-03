import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import DishList from "./DishList"
import AppNavbar from './AppNavbar';

class App extends Component {
  render() {
    return (
      <Router>
        <div>
          <AppNavbar/>
          <Switch>
            <Route path='/' exact={true} component={Home} />
            <Route path="/dishes" exact={true} component={DishList} />
          </Switch>
        </div>
      </Router>
    );
  }
}

export default App;

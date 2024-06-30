import './App.css';
import React, { Component, useEffect, useState } from 'react';

class App extends Component {
  constructor(props) {
    super(props);
  }

  state = {    dishes: []};
    async componentDidMount() {
      try {
        const response = await fetch("http://localhost:8080/api/v1/menu");
        console.log(response);
        console.log("Данные должны быть тут: ", this.state);
        const jsonData = await response.json();
        this.setState({ dishes: jsonData });
      } catch (error) {
        console.error("Ошибка при получении данных: ", error);
      }
    }
  render() {
    const {dishes} = this.state;
    console.log(this.state);
    console.log(dishes);
    return (
      <div className="App">
        <header className="App-header">
          <div className="App-intro">
            <h2>menu</h2>
            {dishes.map(dish =>
              <div key={dish.id}>
                {dish.name} ({dish.price})
              </div>
            )}
          </div>
        </header>
      </div>
    );
  }
}

export default App;

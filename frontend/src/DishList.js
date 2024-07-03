import { Button } from 'reactstrap';
import './DishList.css';
import React, { Component } from 'react';
import AppNavbar from './AppNavbar';
import CreateDishModal from './CreateDishModal';

class DishList extends Component {
  constructor(props) {
    super(props);
  }

  state = { dishes: [] };
  async componentDidMount() {
    try {
      const response = await fetch("http://localhost:8080/api/v1/menu");
      const jsonData = await response.json();
      this.setState({ dishes: jsonData });
    } catch (error) {
      console.error("Ошибка при получении данных: ", error);
    }
  }

  async remove(id) {
    await fetch(`http://localhost:8080/api/v1/menu/${id}`,
      {
        method: 'DELETE',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        }
      }
    ).then(() => {
      let updatedDish = [...this.state.dishes].filter(i => i.id !== id);
      this.setState({ dishes: updatedDish })
    });
  }

  render() {
    const { dishes } = this.state;
    const dishList = dishes.map(dish => {
    let imageUrl = dish.imageId;
      return (
        <div key={dish.id} className="popup-menu">
          <img src={imageUrl} alt="image" border="0" />
          <div className="info">
            <div className="dish-name">{dish.name}</div>
            <div className="dish-price">{dish.price} рублей</div>
            <div className="actions">
              <Button size="sm" color="danger" onClick={() => this.remove(dish.id)}>Удалить</Button>
            </div>
          </div>
        </div>
      );
    });

    return (
      <div className='DishList'>
        <CreateDishModal/> 
        <div className="DishList-intro">
          {
            dishList
          }
        </div>
      </div>
    );
  }
}

export default DishList;

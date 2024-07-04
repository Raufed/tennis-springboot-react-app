import React, { Component } from 'react';
import { withRouter } from 'react-router-dom/cjs/react-router-dom.min';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Input, Label } from 'reactstrap';

class CreateDishModal extends Component {
  emptyItem = {
    name: '',
    price: 0,
    imageId: ''
  };

  constructor(props) {
    super(props);
    this.state = {
      isOpen: false,
      item: this.emptyItem
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleCreateDish = this.handleCreateDish.bind(this);

  }

  toggleModal = () => {
    this.setState(prevState => ({
      isOpen: !prevState.isOpen
    }));
  };

  async handleCreateDish(event) {
    event.preventDefault();
    const { item } = this.state;
    try {
      const response = await fetch("http://localhost:8080/api/v1/menu", {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(item)

      }
      )
      const data = await response.json();
      console.log(data);

      this.props.history.push(item)
      this.toggleModal();
      window.location.reload(); // Перезагрузить страницу
    } catch (error) {
      console.error("Ошибка при получении данных: ", error);
    }

  };
  handleChange(event) {
    const { name, price, imageId, value } = event.target;

    let item = { ...this.state.item };
    item[name] = value;
    item[price] = value;
    item[imageId] = value;

    this.setState(prevState => ({
      item: {
        ...prevState.item,
        [name]: value,
        [price]: value,
        [imageId]: value
      }
    }));
  };

  render() {
    const { item } = this.state;
    return (
      <div>
        <Button color="primary" onClick={this.toggleModal}>Добавить блюдо</Button>
        <Modal isOpen={this.state.isOpen} toggle={this.toggleModal}>
          <ModalHeader toggle={this.toggleModal}>Создание блюда</ModalHeader>
          <ModalBody>
            <Label>URL изображения</Label>
            <Input type="text" name="imageId" value={item.imageId} onChange={this.handleChange} ></Input>
            <Label>Название</Label>
            <Input type="text" name="name" value={item.name} onChange={this.handleChange} />
            <Label>Цена</Label>
            <Input type="number" name="price" value={item.price} onChange={this.handleChange} />
            {/* Форма создания блюда */}
            {/* Добавьте необходимые поля для создания блюда */}
          </ModalBody>
          <ModalFooter>
            <Button color="primary" onClick={this.handleCreateDish}>Создать</Button>{' '}
            <Button color="secondary" onClick={this.toggleModal}>Отмена</Button>
          </ModalFooter>
        </Modal>
      </div>
    );
  }
}

export default withRouter(CreateDishModal);
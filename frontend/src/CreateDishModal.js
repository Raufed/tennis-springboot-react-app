import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Input, Label, Form } from 'reactstrap';

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
      item: this.emptyItem,
      selectedFile: null
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleCreateDish = this.handleCreateDish.bind(this);
  }

  toggleModal = () => {
    this.setState(prevState => ({
      isOpen: !prevState.isOpen
    }));
  };


  handleFileChange = event => {
    const file = event.target.files[0];
    this.setState({ selectedFile: file });
  };

  async handleCreateDish(event) {
    event.preventDefault();
    const { item, selectedFile } = this.state;
    try {
      const formData = new FormData();
      formData.append('image', selectedFile);

      const response = await fetch("http://localhost:8080/api/v1/menu", {
        method: 'POST',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(item)
      });

      const data = await response.json();
      console.log(data);

      const responseFile = await fetch("http://localhost:8080/api/v1/menu/uploadDishImage", {
        method: 'POST',
        body: formData
      }).then(res => {
        if (res.ok) {
          console.log(res.data);
          alert("File uploaded successfully.")
        }
      });

      const dataFile = await responseFile.json();
      console.log(dataFile);

      this.props.history.push(item);
      this.toggleModal();
      window.location.reload(); // Перезагрузить страницу
    } catch (error) {
      console.error("Ошибка при получении данных: ", error);
    }
  }

  handleChange(event) {
    const { name, value } = event.target;
    this.setState(prevState => ({
      item: {
        ...prevState.item,
        [name]: value
      }
    }));
  }

  render() {
    const { item } = this.state;
    return (
      <div>
        <Button color="primary" onClick={this.toggleModal}>Добавить блюдо</Button>
        <Modal isOpen={this.state.isOpen} toggle={this.toggleModal}>
          <ModalHeader toggle={this.toggleModal}>Создание блюда</ModalHeader>
          <ModalBody>
            <Form>
              <Label>Выберите изображение</Label>
              <Input type="file" name="image" accept="image/*" onChange={this.handleFileChange} />
              <br />
              <Label>URL изображения</Label>
              <Input type="text" name="imageId" value={item.imageId} onChange={this.handleChange} />
              <Label>Название</Label>
              <Input type="text" name="name" value={item.name} onChange={this.handleChange} />
              <Label>Цена</Label>
              <Input type="number" name="price" value={item.price} onChange={this.handleChange} />
            </Form>
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
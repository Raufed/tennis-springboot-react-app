import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Input, Label, Form } from 'reactstrap';
import { v4 as uuidv4 } from 'uuid';

class CreateDishModal extends Component {
  emptyItem = {
    name: '',
    price: 0,
    imageId: '',
    imageUrl: ''
  };

  constructor(props) {
    super(props);
    this.state = {
      isOpen: false,
      item: this.emptyItem,
      selectedFile: null,
      selectedFileName: ''
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
    const imageId = uuidv4();
    const file = event.target.files[0];
    const selectedFileName = file ? file.name : '';
    const selectedFileId = file ? file.imageId : '';
    this.setState({ selectedFile: file, selectedFileName, selectedFileId });
    this.handleChange(event); // Вызов функции handleChange
  };

  async handleCreateDish(event) {
    event.preventDefault();
    const { item, selectedFile } = this.state;

    const imageId = uuidv4();
    this.setState(prevState => ({
      item: {
        ...prevState.item,
        imageId: imageId
      }
    }));

    try {
      const formData = new FormData();
      formData.append('image', selectedFile);
      formData.append('dish', JSON.stringify(item));

      const response = await fetch("http://localhost:8080/api/v1/menu", {
        method: 'POST',
        //headers: {'Accept': 'application/json','Content-Type': 'application/json'},
        //body: JSON.stringify(item)
        body: formData 
      });

      
      //const responseFile = await fetch("http://localhost:8080/api/v1/menu/uploadDishImage", {method: 'POST', body: formData});
      
      const data = await response.json();
      console.log(data);

      this.props.history.push(item);
      this.toggleModal();
      window.location.reload();
    } catch (error) {
      console.error("Ошибка при получении данных: ", error);
    }
  }

  handleChange(event) {
    const imageId = uuidv4();
    const { name, value } = event.target;
    this.setState(prevState => ({
      item: {
        ...prevState.item,
        [name]: value,
        imageId: imageId
      }
    }));
  }

  render() {
    const { item, selectedFileName } = this.state;
    return (
      <div>
        <Button color="primary" onClick={this.toggleModal}>Добавить блюдо</Button>
        <Modal isOpen={this.state.isOpen} toggle={this.toggleModal}>
          <ModalHeader toggle={this.toggleModal}>Создание блюда</ModalHeader>
          <ModalBody>
            <Form>
              <Label>Выберите изображение</Label>
              <Input type="file"  accept="image/*" onChange={this.handleFileChange} />

              <br />
              <Label>{item.imageId}</Label>
              <br />
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
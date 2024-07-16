import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import { Button, Modal, ModalBody, ModalFooter, Input, Label, Form } from 'reactstrap';
import { v4 as uuidv4 } from 'uuid';
import './CreateDishModal.css'; // Import the CSS file

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
      item: this.emptyItem,
      selectedFile: null,
      selectedFileName: '',
      selectedFileUrl: '' // Add state to store the selected file URL
    };

    this.handleChange = this.handleChange.bind(this);
    this.handleCreateDish = this.handleCreateDish.bind(this);
  }

  handleFileChange = event => {
    const file = event.target.files[0];
    const selectedFileName = file ? file.name : '';
    const selectedFileUrl = file ? URL.createObjectURL(file) : '';
    this.setState({ selectedFile: file, selectedFileName, selectedFileUrl });
    this.handleChange(event); // Call handleChange
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
        body: formData 
      });

      const data = await response.json();
      console.log(data);

      this.props.history.push(item);
      this.props.onClose();
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
    const { item, selectedFileName, selectedFileUrl } = this.state;
    const { onClose } = this.props;
    return (
      <Modal isOpen={true} toggle={onClose} className="Modal">
        <ModalBody className="ModalBody">
          <Form>
            <Label>Выберите изображение</Label>
            <label htmlFor="file-upload" className="button-like" > Выберите файл
              <Input id="file-upload" type="file" accept="image/*" onChange={this.handleFileChange} /> 
            </label>
            {selectedFileName && <p className="selected-file">Выбранный файл: {selectedFileName}</p>}
            {selectedFileUrl && <img src={selectedFileUrl} alt="Preview" className="image-preview" />}
            <br />
            <Label>Название</Label>
            <Input type="text" name="name" value={item.name} onChange={this.handleChange} />
            <Label>Цена</Label>
            <Input type="number" name="price" value={item.price} onChange={this.handleChange} />
          </Form>
        </ModalBody>
        <ModalFooter className="ModalFooter">
          <Button color="primary" className="primary" onClick={this.handleCreateDish}>Создать</Button>{' '}
          <Button color="secondary" className="secondary" onClick={onClose}>Отмена</Button>
        </ModalFooter>
      </Modal>
    );
  }
}

export default withRouter(CreateDishModal);

import React, { Component } from 'react';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';

class CreateDishModal extends Component {
  constructor(props) {
    super(props);
    this.state = {
      isOpen: false,
      // Добавьте другие необходимые поля для создания блюда
    };
  }

  toggleModal = () => {
    this.setState(prevState => ({
      isOpen: !prevState.isOpen
    }));
  };

  handleCreateDish = () => {
    // Обработка логики создания блюда
    // Можно использовать значения из this.state для создания блюда
    // Например, отправить данные на сервер или обновить состояние родительского компонента
    // После успешного создания блюда закройте модальное окно
    this.toggleModal();
  };

  render() {
    return (
      <div>
        <Button color="primary" onClick={this.toggleModal}>Добавить блюдо</Button>
        <Modal isOpen={this.state.isOpen} toggle={this.toggleModal}>
          <ModalHeader toggle={this.toggleModal}>Создание блюда</ModalHeader>
          <ModalBody>
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

export default CreateDishModal;
import React, { Component } from 'react';

class CreateEmployeeForm extends Component {

    constructor(props) {
        super(props);
        this.state = this.emptyNewEmployee;
    }

    emptyNewEmployee = {
        newEmployeeName: '',
        newEmployeeSalary: ''
    }

    render() {
        return (
            <form onSubmit={this.onCreateEmployee} className="block-margin">
                <div className="form-row justify-content-center">
                    <div className="col-auto">
                        <input type="text" className="form-control" value={this.state.newEmployeeName} onChange={this.onNameChange} placeholder="Имя" />
                    </div>
                    <div className="col-auto">
                        <input type="text" className="form-control" value={this.state.newEmployeeSalary} onChange={this.onSalaryChange} placeholder="Оклад" />
                    </div>
                    <div className="col-auto">
                        <button type="submit" className="btn btn-primary">Создать</button>
                    </div>
                </div>
            </form>
        );
    }

    onCreateEmployee = (e) => {
        e.preventDefault();
        this.props.onCreateEmployee({
            name: this.state.newEmployeeName,
            salary: this.state.newEmployeeSalary
        });
        this.resetForm();
    }

    resetForm() {
        this.setState(this.emptyNewEmployee);
    }

    onNameChange = (e) => {
        this.setState({
            newEmployeeName: e.target.value
        });
    }

    onSalaryChange = (e) => {
        this.setState({
            newEmployeeSalary: e.target.value
        });
    }

}

export default CreateEmployeeForm;

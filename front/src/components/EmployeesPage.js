import React, { Component } from 'react';
import CreateEmployeeForm from './CreateEmployeeForm';
import EmployeeList from './EmployeeList';

class EmployeesPage extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div class="container">
                <CreateEmployeeForm onCreateEmployee={this.props.onCreateEmployee} />
                <EmployeeList employees={this.props.employees} onEditSalary={this.props.onEditSalary} />
                <form onSubmit={this.onDeleteAll}>
                    <button type="submit" className="btn btn-primary">Удалить все</button>
                </form>
            </div>
        );
    }

    onDeleteAll = (e) => {
        e.preventDefault();
        this.props.onDeleteAll();
    }

}

export default EmployeesPage;

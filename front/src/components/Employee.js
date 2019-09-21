import React, { Component } from 'react';

class Employee extends Component {

    constructor(props) {
        console.log('employee constructor, props: ', props);
        super(props);
        this.state = {
            salary: this.props.employee.salary.value
        };
    }

    onSalaryChanged = (e) => {
        this.setState({
            salary: e.target.value
        });
    }

    onEditSalaryClicked = (e) => {
        e.preventDefault();
        this.props.onEditSalary({
            id: this.props.employee.id,
            salary: this.state.salary
        })
    }

    render() {
        console.log('render employee, props: ', this.props);
        return (
            <tr className="employee-item">
                <td style={{paddingTop: '1em'}}>{this.props.employee.name}</td>
                <td>
                <div class="input-group mb-3">
                    <input type="text" class="form-control" placeholder="Salary-placeholder" aria-label="Salary-label" aria-describedby="btnEditSalary"
                    value={this.state.salary} onChange={this.onSalaryChanged} />
                    <div class="input-group-append">
                        <button class="btn btn-outline-secondary" type="button" id="btnEditSalary" onClick={this.onEditSalaryClicked} >Сохранить</button>
                    </div>
                </div>
                </td>
            </tr>
        );
    }

}

export default Employee;
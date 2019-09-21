import React, { Component } from 'react';
import { connect } from 'react-redux';
import './App.css';
import EmployeesPage from './components/EmployeesPage';

class App extends Component {

    render() {
        return (
            <div className="App">
                <EmployeesPage employees={this.props.employees} 
                               onCreateEmployee={this.onCreateEmployee}
                               onEditSalary={this.onEditSalary}
                               onDeleteAll={this.onDeleteAll} />
            </div>
        );
    }

    componentDidMount() {
        fetch('/api/all_employees')
        .then((response) => response.json())
        .then((employees) => {
            console.log('GET /api/all_employees result: ', employees);
            this.props.dispatch({
                type: 'FETCH_EMPLOYEES_SUCCEEDED',
                payload: {
                    employees
                }
            });
        });
    }

    onCreateEmployee = ({name, salary}) => {
        var requestData = {
            name: name,
            salary: {
                value: salary
            }
        };
        fetch(
            '/api/create_employee',
            {
                headers: {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json'
                },
                method: 'POST',
                body: JSON.stringify(requestData)
            }
        )
        .then((response) => response.json())
        .then((employee) => {
            console.log('created employee: ', employee);
            this.props.dispatch({
                type: 'CREATE_EMPLOYEE',
                payload: {
                    employee
                }
            });
        });
    }

    onEditSalary = ({id, salary}) => {
        console.log('App.onEditSalary: ', id, salary);
        var requestData = {
            id: id,
            salary: {
                value: salary
            }
        };
        fetch(
            '/api/edit_employee',
            {
                headers: {
                  'Accept': 'application/json',
                  'Content-Type': 'application/json'
                },
                method: 'POST',
                body: JSON.stringify(requestData)
            }
        )
        .then((response) => {
            console.log('edit_employee response: ', response);
            this.props.dispatch({
                type: 'EDIT_SALARY',
                payload: {
                    id: id,
                    salary: salary
                }
            });
        });
    }

    onDeleteAll = () => {
        fetch(
            '/api/remove_all_employees',
            {
                method: 'POST',
                body: ''
            }
        )
        .then((response) => this.props.dispatch({
            type: 'DELETE_ALL'
        }));
    }

}

function mapStateToProps(state) {
    console.log('mapStateToProps');
    return {
        employees: state.employees
    }
}

export default connect(mapStateToProps)(App);

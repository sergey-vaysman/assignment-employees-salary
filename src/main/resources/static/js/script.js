
class CreateEmployee extends React.Component {

    constructor(props) {
        super(props);
        this.state = {};
        this.onEmployeeNameInputChange = this.onEmployeeNameInputChange.bind(this);
        this.onEmployeeSalaryInputChange = this.onEmployeeSalaryInputChange.bind(this);
        this.onSaveClicked = this.onSaveClicked.bind(this);
    }

    onEmployeeNameInputChange(event) {
        this.setState({
            name: event.target.value
        });
    }

    onEmployeeSalaryInputChange(event) {
        this.setState({
            salary: event.target.value
        });
    }

    onSaveClicked(event) {
        console.log('state: ', this.state);
        var requestData = {
            name: this.state.name,
            salary: {
                value: this.state.salary
            }
        };
        console.log('stringify: ', JSON.stringify(requestData));
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
        .then((response) => { return response.json()})
        .then((data) => console.log('Submitted: ', data))
    }

    render() {

        var inputName = React.createElement(
            'input',
            {
                type: 'text',
                className: 'form-control',
                placeholder: 'Имя сотрудника',
                onChange: this.onEmployeeNameInputChange
            }
        )

        var inputSalary = React.createElement(
            'input',
            {
                type: 'text',
                className: 'form-control',
                placeholder: 'Оклад',
                onChange: this.onEmployeeSalaryInputChange
            }
        );

        var btnSaveEmployee = React.createElement(
            'input',
            {
                type: 'submit',
                className: 'btn btn-primary',
                defaultValue: 'Создать',
                onClick: this.onSaveClicked
            }
        );

        var form = React.createElement(
            'form',
            null,
            React.createElement(
                'div',
                {className: 'form-group'},
                React.createElement(
                    'div',
                    {className: 'row'},
                    React.createElement('div', {className: 'col'}, inputName),
                    React.createElement('div', {className: 'col'}, inputSalary)
                )
            ),
            btnSaveEmployee
        );

        return form;
    }

}


class EmployeeList extends React.Component {
    
    constructor(props) {
        super(props);
        this.state = {
            employees: []
        }
    }

    componentDidMount() {
        fetch('/api/all_employees')
        .then((response) => response.json())
        .then((employees) => this.setState({
            employees: employees
        }));
    }

    render() {
        console.log('Rendering Employees', this.state.employees);
        var rows = [];
        var i = 0;
        this.state.employees.forEach((employee) => {
            console.log('employee name: ', employee.name);
            console.log('employee salary: ', employee.salary.value);
            var nameColumn = React.createElement('td', null, employee.name);
            var salaryColumn = React.createElement('td', null, employee.salary.value);
            rows[i++] = React.createElement('tr', {key: employee.id}, nameColumn, salaryColumn);
        });
        var tBody = React.createElement('tbody', null, rows.length == 0 ? '' : rows);
        var h1 = React.createElement('h1', null, 'Сотрудники');
        var table = React.createElement('table', {className: 'table-striped table-condensed table table-bordered table-hover'}, tBody);
        return React.createElement('div', {className: 'container'}, h1, table);
    }
}

class DeleteAllEmployees extends React.Component {

    onDeleteClicked() {
        fetch(
            '/api/remove_all_employees',
            {
                method: 'POST',
                body: ''
            }
        )
    }

    render() {
        return React.createElement(
            'form',
            null,
            React.createElement(
                'input',
                {
                    type: 'submit',
                    className: 'btn btn-primary',
                    defaultValue: 'Удалить всех',
                    onClick: this.onDeleteClicked
                }
            )
        );
    }


}

ReactDOM.render(
    React.createElement(
        'div',
        null,
        React.createElement(CreateEmployee, null),
        React.createElement('hr', null),
        React.createElement(EmployeeList, null),
        React.createElement('hr', null),
        React.createElement(DeleteAllEmployees, null)
    ),
    document.getElementById('content')
);

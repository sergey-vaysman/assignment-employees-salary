import React from 'react';
import Employee from './Employee';

const EmployeeList = props => {
    console.log('render employee list, props: ', props);
    return (
        <table className="table employee-list">
            <thead>
                <tr>
                <th scope="col">Имя</th>
                <th scope="col">Оклад</th>
                </tr>
            </thead>
            <tbody>
                {props.employees.map(employee => (
                        <Employee key={employee.id} employee={employee} onEditSalary={props.onEditSalary} />
                    )
                )}
            </tbody>
        </table>
    );
};

export default EmployeeList;


export default function employees(state = {employees: []}, action) {
    console.log('reducer, action: ', action);
    if (action.type === 'CREATE_EMPLOYEE') {
        return {
            employees: state.employees.concat(action.payload.employee)
        };
    }
    if (action.type === 'FETCH_EMPLOYEES_SUCCEEDED') {
        return {
            employees: action.payload.employees
        }
    }
    if (action.type === 'EDIT_SALARY') {
        var editEmployeeId = action.payload.id;
        var employee = state.employees.find(employee => employee.id === editEmployeeId);
        employee.salary.value = action.payload.salary;
        return { employees: state.employees };
    }
    if (action.type === 'DELETE_ALL') {
        return {
            employees: []
        }
    }
    return state;
}
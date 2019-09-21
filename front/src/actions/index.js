
export function createEmployee({ name, salary }) {
    return {
        type: 'CREATE_EMPLOYEE',
        payload: {
            name: name,
            salary: {
                value: salary
            }
        }
    };
}

export function fetchEmployees() {
        
}
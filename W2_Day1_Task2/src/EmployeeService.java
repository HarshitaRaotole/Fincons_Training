import java.util.*;


public class EmployeeService {
    static List<Employee> filterEmployees(
            List<Employee> employees,
            EmployeeFilter filter) {
        List<Employee> result = new ArrayList<>();

        for (Employee e : employees) {
            if (filter.filter(e)) {
                result.add(e);
            }
        }
        return result;
    }
}

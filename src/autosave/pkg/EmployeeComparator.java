/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autosave.pkg;

import java.util.Comparator;

/**
 *
 * @author tnnh
 */
public class EmployeeComparator implements Comparator<Employee>{
     public int compare (Employee emp1, Employee emp2){
        Employee e1 = emp1;
        Employee e2 = emp2;
        return e1.getName().compareTo(e2.getName());
    }
}

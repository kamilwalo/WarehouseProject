package warehouse.Entity;

import javax.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_employee")
    private Integer employeeId;

    @Column(name = "first_name")
    private String nameOfEmploee;

    @Column(name = "last_name")
    private String lastNameOfEmployee;

    @Column(name = "position")
    private String position;

    public Employee() {
    }

    public Employee(String nameOfEmploee, String lastNameOfEmployee, String position) {
        this.nameOfEmploee = nameOfEmploee;
        this.lastNameOfEmployee = lastNameOfEmployee;
        this.position = position;
    }

    public String getNameOfEmploee() {
        return nameOfEmploee;
    }

    public void setNameOfEmploee(String nameOfEmploee) {
        this.nameOfEmploee = nameOfEmploee;
    }

    public String getLastNameOfEmployee() {
        return lastNameOfEmployee;
    }

    public void setLastNameOfEmployee(String lastNameOfEmployee) {
        this.lastNameOfEmployee = lastNameOfEmployee;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }



    @Override
    public String toString() {
        return  "Id employee=" + employeeId +
                ",\n name of emploee='" + nameOfEmploee + '\'' +
                ",\n surname of employee='" + lastNameOfEmployee + '\'' +
                ",\n position='" + position + '\'';
    }
}

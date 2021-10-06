package warehouse.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Table(name = "employee_account")
@Entity
public class EmployeeAccount implements Serializable {
    @Id
    @OneToOne
    @JoinColumn(name = "id_employee")
    private Employee idEmployee;

    @Column(name = "login", unique = true, length = 100)
    private String login;

    @Column(name = "password", length = 20)
    private String password;

    public EmployeeAccount() {
    }



    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Employee getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Employee idEmployee) {
        this.idEmployee = idEmployee;
    }

    @Override
    public String toString() {
        return "EmployeeAccount{" +
                "idEmployee=" + idEmployee +
                ", login='" + login + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeAccount that = (EmployeeAccount) o;
        return Objects.equals(login, that.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }
}
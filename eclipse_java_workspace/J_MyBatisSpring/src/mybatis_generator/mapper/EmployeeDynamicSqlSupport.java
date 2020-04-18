package mybatis_generator.mapper;

import java.sql.JDBCType;
import java.util.Date;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class EmployeeDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final Employee employee = new Employee();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> id = employee.id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> userName = employee.userName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> brithDay = employee.brithDay;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> age = employee.age;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> employee_type = employee.employee_type;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> raise_salary = employee.raise_salary;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> deduct_salary = employee.deduct_salary;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> department_id = employee.department_id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class Employee extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<String> userName = column("username", JDBCType.VARCHAR);

        public final SqlColumn<Date> brithDay = column("birthday", JDBCType.DATE);

        public final SqlColumn<Integer> age = column("age", JDBCType.INTEGER);

        public final SqlColumn<Integer> employee_type = column("employee_type", JDBCType.INTEGER);

        public final SqlColumn<Integer> raise_salary = column("raise_salary", JDBCType.INTEGER);

        public final SqlColumn<Integer> deduct_salary = column("deduct_salary", JDBCType.INTEGER);

        public final SqlColumn<Integer> department_id = column("department_id", JDBCType.INTEGER);

        public Employee() {
            super("employee");
        }
    }
}
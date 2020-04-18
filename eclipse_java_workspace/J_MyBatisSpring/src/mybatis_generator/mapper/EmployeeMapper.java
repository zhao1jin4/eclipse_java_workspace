package mybatis_generator.mapper;

import static org.mybatis.dynamic.sql.SqlBuilder.*;
import static mybatis_generator.mapper.EmployeeDynamicSqlSupport.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;
import mybatis_generator.model.Employee;

@Mapper
public interface EmployeeMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] selectList = BasicColumn.columnList(id, userName, brithDay, age, employee_type, raise_salary, deduct_salary, department_id);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type=SqlProviderAdapter.class, method="delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insert")
    int insert(InsertStatementProvider<Employee> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type=SqlProviderAdapter.class, method="insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<Employee> multipleInsertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @ResultMap("EmployeeResult")
    Optional<Employee> selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type=SqlProviderAdapter.class, method="select")
    @Results(id="EmployeeResult", value = {
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="username", property="userName", jdbcType=JdbcType.VARCHAR),
        @Result(column="birthday", property="brithDay", jdbcType=JdbcType.DATE),
        @Result(column="age", property="age", jdbcType=JdbcType.INTEGER),
        @Result(column="employee_type", property="employee_type", jdbcType=JdbcType.INTEGER),
        @Result(column="raise_salary", property="raise_salary", jdbcType=JdbcType.INTEGER),
        @Result(column="deduct_salary", property="deduct_salary", jdbcType=JdbcType.INTEGER),
        @Result(column="department_id", property="department_id", jdbcType=JdbcType.INTEGER)
    })
    List<Employee> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, employee, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, employee, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(Integer id_) {
        return delete(c -> 
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(Employee record) {
        return MyBatis3Utils.insert(this::insert, record, employee, c ->
            c.map(id).toProperty("id")
            .map(userName).toProperty("userName")
            .map(brithDay).toProperty("brithDay")
            .map(age).toProperty("age")
            .map(employee_type).toProperty("employee_type")
            .map(raise_salary).toProperty("raise_salary")
            .map(deduct_salary).toProperty("deduct_salary")
            .map(department_id).toProperty("department_id")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertMultiple(Collection<Employee> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, employee, c ->
            c.map(id).toProperty("id")
            .map(userName).toProperty("userName")
            .map(brithDay).toProperty("brithDay")
            .map(age).toProperty("age")
            .map(employee_type).toProperty("employee_type")
            .map(raise_salary).toProperty("raise_salary")
            .map(deduct_salary).toProperty("deduct_salary")
            .map(department_id).toProperty("department_id")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(Employee record) {
        return MyBatis3Utils.insert(this::insert, record, employee, c ->
            c.map(id).toPropertyWhenPresent("id", record::getId)
            .map(userName).toPropertyWhenPresent("userName", record::getUserName)
            .map(brithDay).toPropertyWhenPresent("brithDay", record::getBrithDay)
            .map(age).toPropertyWhenPresent("age", record::getAge)
            .map(employee_type).toPropertyWhenPresent("employee_type", record::getEmployee_type)
            .map(raise_salary).toPropertyWhenPresent("raise_salary", record::getRaise_salary)
            .map(deduct_salary).toPropertyWhenPresent("deduct_salary", record::getDeduct_salary)
            .map(department_id).toPropertyWhenPresent("department_id", record::getDepartment_id)
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<Employee> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, employee, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<Employee> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, employee, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<Employee> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, employee, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<Employee> selectByPrimaryKey(Integer id_) {
        return selectOne(c ->
            c.where(id, isEqualTo(id_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, employee, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateAllColumns(Employee record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalTo(record::getId)
                .set(userName).equalTo(record::getUserName)
                .set(brithDay).equalTo(record::getBrithDay)
                .set(age).equalTo(record::getAge)
                .set(employee_type).equalTo(record::getEmployee_type)
                .set(raise_salary).equalTo(record::getRaise_salary)
                .set(deduct_salary).equalTo(record::getDeduct_salary)
                .set(department_id).equalTo(record::getDepartment_id);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(Employee record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(id).equalToWhenPresent(record::getId)
                .set(userName).equalToWhenPresent(record::getUserName)
                .set(brithDay).equalToWhenPresent(record::getBrithDay)
                .set(age).equalToWhenPresent(record::getAge)
                .set(employee_type).equalToWhenPresent(record::getEmployee_type)
                .set(raise_salary).equalToWhenPresent(record::getRaise_salary)
                .set(deduct_salary).equalToWhenPresent(record::getDeduct_salary)
                .set(department_id).equalToWhenPresent(record::getDepartment_id);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(Employee record) {
        return update(c ->
            c.set(userName).equalTo(record::getUserName)
            .set(brithDay).equalTo(record::getBrithDay)
            .set(age).equalTo(record::getAge)
            .set(employee_type).equalTo(record::getEmployee_type)
            .set(raise_salary).equalTo(record::getRaise_salary)
            .set(deduct_salary).equalTo(record::getDeduct_salary)
            .set(department_id).equalTo(record::getDepartment_id)
            .where(id, isEqualTo(record::getId))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(Employee record) {
        return update(c ->
            c.set(userName).equalToWhenPresent(record::getUserName)
            .set(brithDay).equalToWhenPresent(record::getBrithDay)
            .set(age).equalToWhenPresent(record::getAge)
            .set(employee_type).equalToWhenPresent(record::getEmployee_type)
            .set(raise_salary).equalToWhenPresent(record::getRaise_salary)
            .set(deduct_salary).equalToWhenPresent(record::getDeduct_salary)
            .set(department_id).equalToWhenPresent(record::getDepartment_id)
            .where(id, isEqualTo(record::getId))
        );
    }
}
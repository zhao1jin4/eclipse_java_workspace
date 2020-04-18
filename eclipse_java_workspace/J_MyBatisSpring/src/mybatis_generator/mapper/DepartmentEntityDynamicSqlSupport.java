package mybatis_generator.mapper;

import java.sql.JDBCType;
import javax.annotation.Generated;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

public final class DepartmentEntityDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final DepartmentEntity departmentEntity = new DepartmentEntity();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Integer> id = departmentEntity.id;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> depName = departmentEntity.depName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class DepartmentEntity extends SqlTable {
        public final SqlColumn<Integer> id = column("id", JDBCType.INTEGER);

        public final SqlColumn<String> depName = column("dep_name", JDBCType.VARCHAR);

        public DepartmentEntity() {
            super("department");
        }
    }
}
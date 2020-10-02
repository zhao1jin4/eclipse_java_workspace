package querydsl_jpa.gen;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import jpa.single.UserBean;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserBean is a Querydsl query type for UserBean
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserBean extends EntityPathBase<UserBean> {

    private static final long serialVersionUID = -1594745586L;

    public static final QUserBean userBean = new QUserBean("userBean");

    public final StringPath id = createString("id");

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public QUserBean(String variable) {
        super(UserBean.class, forVariable(variable));
    }

    public QUserBean(Path<? extends UserBean> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserBean(PathMetadata metadata) {
        super(UserBean.class, metadata);
    }

}


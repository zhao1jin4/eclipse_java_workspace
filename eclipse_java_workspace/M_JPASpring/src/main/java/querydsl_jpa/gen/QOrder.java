package querydsl_jpa.gen;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;

import jpa.one2many.Order;
import jpa.one2many.OrderItem;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = -42512162L;

    public static final QOrder order = new QOrder("order1");

    public final NumberPath<Float> amount = createNumber("amount", Float.class);

    public final SetPath<OrderItem, QOrderItem> items = this.<OrderItem, QOrderItem>createSet("items", OrderItem.class, QOrderItem.class, PathInits.DIRECT2);

    public final StringPath orderid = createString("orderid");

    public QOrder(String variable) {
        super(Order.class, forVariable(variable));
    }

    public QOrder(Path<? extends Order> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrder(PathMetadata metadata) {
        super(Order.class, metadata);
    }

}


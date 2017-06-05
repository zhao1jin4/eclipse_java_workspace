
package jta_transactional;

import javax.transaction.Transactional;

@Transactional(value = Transactional.TxType.NOT_SUPPORTED)
public class BeanNotSupported extends BeanBase {
}

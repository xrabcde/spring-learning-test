package nextstep.helloworld.jdbc.simpleinsert;

import nextstep.helloworld.jdbc.Customer;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class SimpleInsertDao {
    private SimpleJdbcInsert insertActor;

    public SimpleInsertDao(DataSource dataSource) {
        this.insertActor = new SimpleJdbcInsert(dataSource)
                .withTableName("customers")
                .usingGeneratedKeyColumns("id");
    }

    /**
     * Map +
     * insertActor.executeAndReturnKey
     */
    public Customer insertWithMap(Customer customer) {
        Map<String, Object> parameters = new HashMap<String, Object>(3);
        parameters.put("id", customer.getId());
        parameters.put("first_name", customer.getFirstName());
        parameters.put("last_name", customer.getLastName());
        Number number = insertActor.executeAndReturnKey(parameters);
        return new Customer(number.longValue(), customer.getFirstName(), customer.getLastName());
    }

    /**
     * SqlParameterSource +
     * insertActor.executeAndReturnKey
     */
    public Customer insertWithBeanPropertySqlParameterSource(Customer customer) {
        SqlParameterSource parameters = new BeanPropertySqlParameterSource(customer);
        Number number = insertActor.executeAndReturnKey(parameters);
        return new Customer(number.longValue(), customer.getFirstName(), customer.getLastName());
    }
}

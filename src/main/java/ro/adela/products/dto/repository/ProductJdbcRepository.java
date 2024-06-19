package ro.adela.products.dto.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import ro.adela.products.dto.product.Product;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductJdbcRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    static class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setId(rs.getLong("id"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getString("price"));
            return product;
        }
    }


    public List<Product> findAll(int paged, int total)  {
        return jdbcTemplate.query("select * from product order by id desc limit "+(paged-1)+","+total, new ProductRowMapper());
    }

    public Product findById(long id)  {
        return jdbcTemplate.queryForObject("select * from product where id=?",  new BeanPropertyRowMapper<>(Product.class), id);
    }

    public void deleteById(long id) {
        jdbcTemplate.update("delete from product where id=?", id);
    }

    public int insert(Product product) {
        return jdbcTemplate.update("insert into product (id, name, price) " + "values(?,  ?, ?)",
                product.getId(), product.getName(), product.getPrice());
    }

    public int update(Product product) {
        return jdbcTemplate.update("update product " + " set price = ? " + " where id = ?",
                product.getPrice(), product.getId());
    }

}

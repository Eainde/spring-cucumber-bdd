package com.eainde.cucumber.stepdefinitions;

import com.eainde.cucumber.entity.Order;
import com.eainde.cucumber.repository.OrderRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.messages.internal.com.google.common.io.Resources;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.testcontainers.shaded.com.google.common.collect.ImmutableMap;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class MockDatabaseSteps {
    private final OrderRepository repository;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    MockDatabaseSteps(final OrderRepository repository,
                      final NamedParameterJdbcTemplate jdbcTemplate){
        this.repository=repository;
        this.jdbcTemplate=jdbcTemplate;
    }

    @Given("Setup database")
    public void setupDatabase() throws IOException {
        final var initSql= Resources.toString(Resources.getResource("database/INIT.sql"), StandardCharsets.UTF_8);
        jdbcTemplate.update(initSql, ImmutableMap.of());
    }

    @Given("Add following orders in database")
    public void initializeData(final DataTable dataTable){
        final List<Order> records= dataTable.asList(Order.class);
        records.forEach(repository::add);
    }

    /** This method is required to map data table in feature file to map Order object  */
    @DataTableType
    public Order orderEntry(final Map<String, String> table){
        Order order=new Order();
        order.setOrderId(Integer.parseInt(table.get("orderId")));
        order.setCountry(table.get("country"));
        order.setDescription(table.get("description"));
        order.setQuantity(Integer.parseInt(table.get("quantity")));
        return order;
    }
}

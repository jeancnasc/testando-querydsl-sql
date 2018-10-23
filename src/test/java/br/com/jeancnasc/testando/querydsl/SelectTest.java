package br.com.jeancnasc.testando.querydsl;

import com.querydsl.sql.Configuration;
import com.querydsl.sql.HSQLDBTemplates;
import com.querydsl.sql.SQLQueryFactory;
import org.hsqldb.jdbc.JDBCDataSource;
import org.junit.Assert;
import org.junit.Test;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static br.com.jeancnasc.testando.querydsl.QCliente.cliente;

public class SelectTest {

    @Test
    public void selectTest() throws SQLException {
        HSQLDBTemplates templates = new HSQLDBTemplates();
        Configuration configuration = new Configuration(templates);

        JDBCDataSource datasource = new JDBCDataSource();
        datasource.setUrl("jdbc:hsqldb:file:target/db");
        datasource.setUser("sa");

        SQLQueryFactory factory = new SQLQueryFactory(configuration,datasource);


        Connection connection = factory.getConnection();
        connection.setAutoCommit(false);

        factory.insert(cliente).columns(cliente.id,cliente.nome)
        .values(1,"Teste1").execute();


        List<String> nomes = factory.select(cliente.nome).from(cliente).fetch();

        Assert.assertEquals(1,nomes.size());
        Assert.assertEquals("Teste1",nomes.get(0));

        connection.rollback();

    }
}

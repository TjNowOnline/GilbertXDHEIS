package org.example.gilbertxdheis.infrastructure;

import org.example.gilbertxdheis.domain.Offer;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class JdbcOfferRepository {
    private final DataSource dataSource;
    private static final String sql = "INSERT INTO offers (user_id, item_id, proposed_price, status) VALUES (?, ?, ?, ?)";


    public JdbcOfferRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Offer offer) {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement ppsm = connection.prepareStatement(sql)) {

            ppsm.setInt(1, offer.getUserId());
            ppsm.setInt(2, offer.getItemId());
            ppsm.setDouble(3, offer.getProposedPrice());
            ppsm.setString(4, offer.getStatus());

            ppsm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("could not save offer");

        }
        }
    }



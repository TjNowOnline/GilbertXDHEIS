package org.example.gilbertxdheis.infrastructure;

import org.example.gilbertxdheis.domain.Offer;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcOfferRepository {
    private final DataSource dataSource;
    private static final String sql = "INSERT INTO offers (user_id, item_id, proposed_price, status) VALUES (?, ?, ?, ?)";


    public JdbcOfferRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Offer offer) {
        try (Connection connection = dataSource.getConnection();
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
    public void updateStatus (int offerId, String newStatus) {
        String sql = "UPDATE offers SET status = ? WHERE offer_id = ?";

        try( Connection connection =dataSource.getConnection();
        PreparedStatement ppsm = connection.prepareStatement(sql)){

        ppsm.setString(1, newStatus);
        ppsm.setInt (2, offerId);
        ppsm.executeUpdate();

    }catch (SQLException e){
    throw new RuntimeException("could not update offer status", e);
        }
    }

    public List<Offer> findByItemId(int itemId) {
        String sql = "SELECT * FROM offers WHERE item_id = ?";
        List<Offer> offers = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ppsm = connection.prepareStatement(sql)) {
             ppsm.setInt(1, itemId);
             ResultSet rs = ppsm.executeQuery();

            while (rs.next()) {
                Offer offer = new Offer(
                    rs.getInt("offer_id"),
                    rs.getInt("user_id"),
                    rs.getInt("item_id"),
                    rs.getDouble("proposed_price"),
                    rs.getString("status"));
                offers.add(offer);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Could not find offers for item", e);
        }
        return offers;
    }
}




